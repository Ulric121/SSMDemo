package com.tao.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.tao.dao.InventoryDao;
import com.tao.dao.SuccessKilledDao;
import com.tao.dao.cache.RedisDao;
import com.tao.dto.Exposer;
import com.tao.dto.InventoryExecution;
import com.tao.entity.Inventory;
import com.tao.entity.SuccessKilled;
import com.tao.enums.InventoryStateEnum;
import com.tao.exception.InventoryCloseException;
import com.tao.exception.InventoryException;
import com.tao.exception.RepeatKillException;
import com.tao.service.InventoryService;

/**
 * 
 * @author Ulric
 * @version $Id: InventoryServiceImpl.java, v 1.0 2017年4月1日 下午9:13:42 Ulric Exp $
 */
@Service
public class InventoryServiceImpl implements InventoryService {

    private Logger           logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private InventoryDao     inventoryDao;

    @Autowired
    private SuccessKilledDao successKilledDao;

    @Autowired
    private RedisDao         redisDao;

    //md5盐值字符串，用于混淆md5
    private final String     salt   = "gkfhdhadlfladfhlsdjfhjshjkhkjhkskkkdfsdfs";

    public List<Inventory> getInventoryList() {
        return inventoryDao.queryAll(0, 4);
    }

    public Inventory getById(long inventoryId) {
        return inventoryDao.queryById(inventoryId);
    }

    public Exposer exportInventoryUrl(long inventoryId) {
        /**优化点：缓存优化，一致性是在超时的基础上维护（更新数据库时，废弃掉缓存，而不是更新缓存）
         * get from cache
         * if null
         *  get db
         *  put cache
         * login*/
        //1.访问redis
        Inventory inventory = redisDao.getInventory(inventoryId);
        if (inventory == null) {
            //2.访问数据库
            inventory = inventoryDao.queryById(inventoryId);
            if (inventory == null) {
                return new Exposer(false, inventoryId);
            } else {
                //3.放入redis
                redisDao.putInventory(inventory);
            }
        }
        Date startTime = inventory.getStartTime();
        Date endTime = inventory.getEndTime();
        Date nowTime = new Date();
        if (nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()) {
            return new Exposer(false, inventoryId, nowTime.getTime(), startTime.getTime(),
                endTime.getTime());
        }

        //转化特定字符串的过程，不可逆
        String md5 = getMD5(inventoryId);
        return new Exposer(true, md5, inventoryId);
    }

    private String getMD5(long inventoryId) {
        String base = inventoryId + "/" + salt;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    @Transactional
    public InventoryExecution executeInventory(long inventoryId, long userPhone,
                                               String md5) throws InventoryException,
                                                           RepeatKillException,
                                                           InventoryCloseException {
        if (md5 == null || !md5.equals(getMD5(inventoryId))) {
            throw new InventoryException("kill data rewrite");
        }

        //执行秒杀逻辑：减库存 + 记录购买行为
        Date nowTime = new Date();
        try {
            //记录购买行为
            int insertCount = successKilledDao.insertSuccessKilled(inventoryId, userPhone);
            if (insertCount <= 0) {
                //重复秒杀
                throw new RepeatKillException("kill repeated");
            } else {
                //减库存。并发优化1：将拿到mysql行级锁的步骤放到后面，避免持有行级锁的时间
                int updateCount = inventoryDao.reduceNumber(inventoryId, nowTime);
                if (updateCount <= 0) {
                    //没有更新到记录，rollback
                    throw new InventoryCloseException("kill is close");
                } else {
                    //秒杀成功，commit
                    SuccessKilled successKilled = successKilledDao
                        .queryByIdWithInventory(inventoryId, userPhone);
                    return new InventoryExecution(inventoryId, InventoryStateEnum.SUCCESS,
                        successKilled);
                }
            }
        } catch (InventoryCloseException e1) {
            //捕捉到InventoryCloseException，spring的事务处理会进行回滚
            logger.error(e1.getMessage(), e1);
            throw e1;
        } catch (RepeatKillException e2) {
            //捕捉到RepeatKillException，spring的事务处理会进行回滚
            logger.error(e2.getMessage(), e2);
            throw e2;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            //所有编译期异常，转化为运行期异常，spring的事务处理会进行回滚
            throw new InventoryException("kill inner error:" + e.getMessage());
        }
    }
}
