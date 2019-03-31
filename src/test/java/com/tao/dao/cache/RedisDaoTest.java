package com.tao.dao.cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tao.dao.InventoryDao;
import com.tao.entity.Inventory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-dao.xml" })
public class RedisDaoTest {
    private long         id = 1001L;

    @Autowired
    private RedisDao     redisDao;

    @Autowired
    private InventoryDao inventoryDao;

    @Test
    public void testInventory() throws Exception {
        Inventory inventory = redisDao.getInventory(id);
        if (inventory == null) {
            inventory = inventoryDao.queryById(id);
            if (inventory != null) {
                String result = redisDao.putInventory(inventory);
                System.out.println(result);
                inventory = redisDao.getInventory(id);
                System.out.println(inventory);
            }
        }
    }
}