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
 * ҵ��ӿڣ�վ��ʹ���ߵĽǶ���ƽӿ�
 * �������棺�����������ȣ���������������
 * @author Ulric
 * @version $Id: InventoryService.java, v 1.0 2017��4��1�� ����7:24:38 Ulric Exp $
 */
public interface InventoryService {

    /**
     * ��ѯ���е���ɱ��¼
     * 
     * @return
     */
    List<Inventory> getInventoryList();

    /**
     * ��ѯ������ɱ��¼
     * 
     * @param inventoryId
     * @return
     */
    Inventory getById(long inventoryId);

    /**
     * ��ɱ��ʼʱ�����ɱ�ӿڵ�ַ���������ϵͳʱ�����ɱʱ��
     * 
     * @param inventoryId
     */
    Exposer exportInventoryUrl(long inventoryId);

    /**
     * ִ����ɱ����
     * 
     * @param inventoryId
     * @param userPhone
     * @param md5
     */
    InventoryExecution executeInventory(long inventoryId, long userPhone,
                                        String md5) throws InventoryException, RepeatKillException,
                                                    InventoryCloseException;
}
