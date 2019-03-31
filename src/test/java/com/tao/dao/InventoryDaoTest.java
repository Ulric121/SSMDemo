package com.tao.dao;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tao.entity.Inventory;

/**
 * 配置spring与junit整合
 * 
 * @author Ulric
 * @version $Id: InventoryDaoTest.java, v 1.0 2017年4月1日 下午3:39:26 Ulric Exp $
 */
/**junit启动时加载springIOC容器*/
@RunWith(SpringJUnit4ClassRunner.class)
/**spring配置文件位置*/
@ContextConfiguration({ "classpath:spring/spring-dao.xml" })
public class InventoryDaoTest {

    @Autowired
    private InventoryDao inventoryDao;

    @Test
    public void testReduceNumber() throws Exception {
        Date killTime = new Date();
        int updateCount = inventoryDao.reduceNumber(1000L, killTime);
        System.out.println("updateCount=" + updateCount);
    }

    @Test
    public void testQueryById() throws Exception {
        long inventoryId = 1000L;
        Inventory inventory = inventoryDao.queryById(inventoryId);
        System.out.println(inventory.getName());
        System.out.println(inventory);
    }

    @Test
    public void testQueryAll() throws Exception {
        List<Inventory> inventoryList = inventoryDao.queryAll(0, 100);
        for (Inventory i : inventoryList) {
            System.out.println(i);
        }
    }
}