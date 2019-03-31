package com.tao.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tao.entity.Inventory;

/**
 * 
 * 
 * @author Ulric
 * @version $Id: InventoryDao.java, v 1.0 2017��4��1�� ����1:19:50 Ulric Exp $
 */
public interface InventoryDao {

    /**
     * ���ٿ��
     * 
     * @param inventoryId
     * @param killTime
     * @return ���Ӱ������>=1����ʶ���µļ�¼����
     */
    int reduceNumber(@Param("inventoryId") long inventoryId, @Param("killTime") Date killTime);

    /**
     * ����id��ѯ��ɱ����
     * 
     * @param inventoryId
     * @return
     */
    Inventory queryById(long inventoryId);

    /**
     * ����ƫ������ѯ��ɱ��Ʒ�б�
     * 
     * @param offset
     * @param limit
     * @return
     */
    List<Inventory> queryAll(@Param("offset") int offset, @Param("limit") int limit);
}
