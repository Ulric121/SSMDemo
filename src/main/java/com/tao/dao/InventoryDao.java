package com.tao.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tao.entity.Inventory;

/**
 * 
 * 
 * @author Ulric
 * @version $Id: InventoryDao.java, v 1.0 2017年4月1日 下午1:19:50 Ulric Exp $
 */
public interface InventoryDao {

    /**
     * 减少库存
     * 
     * @param inventoryId
     * @param killTime
     * @return 如果影响行数>=1，标识更新的记录行数
     */
    int reduceNumber(@Param("inventoryId") long inventoryId, @Param("killTime") Date killTime);

    /**
     * 根据id查询秒杀对象
     * 
     * @param inventoryId
     * @return
     */
    Inventory queryById(long inventoryId);

    /**
     * 根据偏移量查询秒杀商品列表
     * 
     * @param offset
     * @param limit
     * @return
     */
    List<Inventory> queryAll(@Param("offset") int offset, @Param("limit") int limit);
}
