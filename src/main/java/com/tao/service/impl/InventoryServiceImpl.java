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
 * @version $Id: InventoryServiceImpl.java, v 1.0 2017��4��1�� ����9:13:42 Ulric Exp $
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

    //md5��ֵ�ַ��������ڻ���md5
    private final String     salt   = "gkfhdhadlfladfhlsdjfhjshjkhkjhkskkkdfsdfs";

    public List<Inventory> getInventoryList() {
        return inventoryDao.queryAll(0, 4);
    }

    public Inventory getById(long inventoryId) {
        return inventoryDao.queryById(inventoryId);
    }

    public Exposer exportInventoryUrl(long inventoryId) {
        /**�Ż��㣺�����Ż���һ�������ڳ�ʱ�Ļ�����ά�����������ݿ�ʱ�����������棬�����Ǹ��»��棩
         * get from cache
         * if null
         *  get db
         *  put cache
         * login*/
        //1.����redis
        Inventory inventory = redisDao.getInventory(inventoryId);
        if (inventory == null) {
            //2.�������ݿ�
            inventory = inventoryDao.queryById(inventoryId);
            if (inventory == null) {
                return new Exposer(false, inventoryId);
            } else {
                //3.����redis
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

        //ת���ض��ַ����Ĺ��̣�������
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

        //ִ����ɱ�߼�������� + ��¼������Ϊ
        Date nowTime = new Date();
        try {
            //��¼������Ϊ
            int insertCount = successKilledDao.insertSuccessKilled(inventoryId, userPhone);
            if (insertCount <= 0) {
                //�ظ���ɱ
                throw new RepeatKillException("kill repeated");
            } else {
                //����档�����Ż�1�����õ�mysql�м����Ĳ���ŵ����棬��������м�����ʱ��
                int updateCount = inventoryDao.reduceNumber(inventoryId, nowTime);
                if (updateCount <= 0) {
                    //û�и��µ���¼��rollback
                    throw new InventoryCloseException("kill is close");
                } else {
                    //��ɱ�ɹ���commit
                    SuccessKilled successKilled = successKilledDao
                        .queryByIdWithInventory(inventoryId, userPhone);
                    return new InventoryExecution(inventoryId, InventoryStateEnum.SUCCESS,
                        successKilled);
                }
            }
        } catch (InventoryCloseException e1) {
            //��׽��InventoryCloseException��spring�����������лع�
            logger.error(e1.getMessage(), e1);
            throw e1;
        } catch (RepeatKillException e2) {
            //��׽��RepeatKillException��spring�����������лع�
            logger.error(e2.getMessage(), e2);
            throw e2;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            //���б������쳣��ת��Ϊ�������쳣��spring�����������лع�
            throw new InventoryException("kill inner error:" + e.getMessage());
        }
    }
}
