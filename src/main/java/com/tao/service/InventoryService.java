/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.tao.service;

import java.util.List;

import com.tao.dto.Exposer;
import com.tao.dto.InventoryExecution;
import com.tao.entity.Inventory;
import com.tao.exception.InventoryCloseException;
import com.tao.exception.InventoryException;
import com.tao.exception.RepeatKillException;

/**
 * 业务接口：站在使用者的角度设计接口
 * 三个方面：方法定义粒度，参数，返回类型
 * @author Ulric
 * @version $Id: InventoryService.java, v 1.0 2017年4月1日 下午7:24:38 Ulric Exp $
 */
public interface InventoryService {

    /**
     * 查询所有的秒杀记录
     * 
     * @return
     */
    List<Inventory> getInventoryList();

    /**
     * 查询单个秒杀记录
     * 
     * @param inventoryId
     * @return
     */
    Inventory getById(long inventoryId);

    /**
     * 秒杀开始时输出秒杀接口地址，否则输出系统时间和秒杀时间
     * 
     * @param inventoryId
     */
    Exposer exportInventoryUrl(long inventoryId);

    /**
     * 执行秒杀操作
     * 
     * @param inventoryId
     * @param userPhone
     * @param md5
     */
    InventoryExecution executeInventory(long inventoryId, long userPhone,
                                        String md5) throws InventoryException, RepeatKillException,
                                                    InventoryCloseException;
}
