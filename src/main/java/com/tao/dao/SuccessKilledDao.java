package com.tao.dao;

import org.apache.ibatis.annotations.Param;

import com.tao.entity.SuccessKilled;

/**
 * 
 * 
 * @author Ulric
 * @version $Id: SuccessKilled.java, v 1.0 2017年4月1日 下午1:24:30 Ulric Exp $
 */
public interface SuccessKilledDao {

    /**
     * 插入购买明细，可过滤重复
     * 
     * @param inventoryId
     * @param userPhone
     * @return 插入的行数
     */
    int insertSuccessKilled(@Param("inventoryId") long inventoryId,
                            @Param("userPhone") long userPhone);

    /**
     * 根据id查询SuccessKilled并携带秒杀产品实体对象
     * 
     * @param inventoryId
     * @param userPhone
     * @return
     */
    SuccessKilled queryByIdWithInventory(@Param("inventoryId") long inventoryId,
                                         @Param("userPhone") long userPhone);
}
