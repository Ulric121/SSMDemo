package com.tao.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tao.dto.Exposer;
import com.tao.dto.InventoryExecution;
import com.tao.entity.Inventory;
import com.tao.exception.InventoryCloseException;
import com.tao.exception.RepeatKillException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-dao.xml", "classpath:spring/spring-service.xml" })
public class InventoryServiceTest {

    private final Logger     logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private InventoryService inventoryService;

    @Test
    public void testGetInventoryList() {
        List<Inventory> list = inventoryService.getInventoryList();
        logger.info("list={}", list);
    }

    @Test
    public void testGetById() {
        long inventoryId = 1000L;
        Inventory inventory = inventoryService.getById(inventoryId);
        logger.info("inventory={}", inventory);
    }

    /**
     * ���Դ��������߼���ע����ظ�ִ��
     */
    @Test
    public void testKillLogic() {
        long inventoryId = 1001L;
        Exposer exposer = inventoryService.exportInventoryUrl(inventoryId);
        if (exposer.isExposed()) {
            logger.info("exposer={}", exposer);
            long phone = 15840668911L;
            String md5 = exposer.getMd5();
            try {
                InventoryExecution execution = inventoryService.executeInventory(inventoryId, phone,
                    md5);
                logger.info("execution={}", execution);
            } catch (InventoryCloseException e) {
                //������catch������Է�ֹ��������������׳���Junit�������ظ�ִ�У�ÿ�ζ�����ɫ��
                logger.error(e.getMessage());
            } catch (RepeatKillException e) {
                logger.error(e.getMessage());
            }
        } else {
            //��ɱδ����
            logger.warn("exposer={}", exposer);
        }
    }
}
