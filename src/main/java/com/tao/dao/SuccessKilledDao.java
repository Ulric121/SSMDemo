package com.tao.dao;

import org.apache.ibatis.annotations.Param;

import com.tao.entity.SuccessKilled;

/**
 * 
 * 
 * @author Ulric
 * @version $Id: SuccessKilled.java, v 1.0 2017��4��1�� ����1:24:30 Ulric Exp $
 */
public interface SuccessKilledDao {

    /**
     * ���빺����ϸ���ɹ����ظ�
     * 
     * @param inventoryId
     * @param userPhone
     * @return ���������
     */
    int insertSuccessKilled(@Param("inventoryId") long inventoryId,
                            @Param("userPhone") long userPhone);

    /**
     * ����id��ѯSuccessKilled��Я����ɱ��Ʒʵ�����
     * 
     * @param inventoryId
     * @param userPhone
     * @return
     */
    SuccessKilled queryByIdWithInventory(@Param("inventoryId") long inventoryId,
                                         @Param("userPhone") long userPhone);
}
