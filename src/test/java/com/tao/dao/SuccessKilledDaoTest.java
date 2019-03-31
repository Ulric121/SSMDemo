package com.tao.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tao.entity.SuccessKilled;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-dao.xml" })
public class SuccessKilledDaoTest {

    @Autowired
    private SuccessKilledDao successKilledDao;

    @Test
    public void testInsertSuccessKilled() throws Exception {
        long inventoryId = 1000L;
        long phone = 15840668150L;
        int insertCount = successKilledDao.insertSuccessKilled(inventoryId, phone);
        System.out.println("insertCount=" + insertCount);
    }

    @Test
    public void testQueryByIdWithInventory() throws Exception {
        long inventoryId = 1000L;
        long phone = 15840668150L;
        SuccessKilled successKilled = successKilledDao.queryByIdWithInventory(inventoryId, phone);
        System.out.println(successKilled);
        System.out.println(successKilled.getInventory());
    }

}
