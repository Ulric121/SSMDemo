package com.tao.controller;

import com.tao.dto.Exposer;
import com.tao.dto.InventoryExecution;
import com.tao.dto.InventoryResult;
import com.tao.entity.Inventory;
import com.tao.enums.InventoryStateEnum;
import com.tao.exception.InventoryCloseException;
import com.tao.exception.RepeatKillException;
import com.tao.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 
 * 
 * @author Ulric
 * @version $Id: InventoryController.java, v 1.0 2017��4��2�� ����11:07:02 Ulric Exp $
 */
@Controller
@RequestMapping("/inventory") //URL��/ģ��/��Դ/{id}/ϸ��
public class InventoryController {
    private final Logger     logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private InventoryService inventoryService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        //��ȡ�б�ҳ
        List<Inventory> list = inventoryService.getInventoryList();
        model.addAttribute("list", list);
        return "list";
    }

    @RequestMapping(value = "/{inventoryId}/detail", method = RequestMethod.GET)
    public String detail(@PathVariable("inventoryId") Long inventoryId, Model model) {
        if (inventoryId == null) {
            //inventoryId�����ڣ��ض���list����
            return "redirect:/inventory/list";//�ض���
        }
        Inventory inventory = inventoryService.getById(inventoryId);
        if (inventory == null) {
            return "forward:/inventory/list";//ת��
        }
        model.addAttribute("inventory", inventory);
        return "detail";
    }

    /**ajax���ʣ�����json������ҪModel��Ϊ��������*/
    @RequestMapping(value = "/{inventoryId}/exposer", 
            method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    @ResponseBody //SpringMVC������ע�⣬��ѷ���������Ϊajax�����Ĭ�ϵ�ajax�����ʽ��json��ʽ�����ὫInventoryResult<Exposer>��װ��json
    public InventoryResult<Exposer> exposer(@PathVariable("inventoryId") Long inventoryId) {
        InventoryResult<Exposer> result;
        try {
            Exposer exposer = inventoryService.exportInventoryUrl(inventoryId);
            result = new InventoryResult<Exposer>(true, exposer);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result = new InventoryResult<Exposer>(false, e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/{inventoryId}/{md5}/execution", 
            method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public InventoryResult<InventoryExecution> execute(@PathVariable("inventoryId") Long inventoryId,
                                                       @PathVariable("md5") String md5,
                                                       @CookieValue(value = "killPhone", required = false) Long userPhone) {
        if (userPhone == null) {
            return new InventoryResult<InventoryExecution>(false, "δע��");
        }
        try {
            InventoryExecution execution = inventoryService.executeInventory(inventoryId, userPhone,
                md5);
            return new InventoryResult<InventoryExecution>(true, execution);
        } catch (RepeatKillException e) {
            InventoryExecution execution = new InventoryExecution(inventoryId,
                InventoryStateEnum.REPEAT_KILL);
            return new InventoryResult<InventoryExecution>(true, execution);
        } catch (InventoryCloseException e) {
            InventoryExecution execution = new InventoryExecution(inventoryId,
                InventoryStateEnum.END);
            return new InventoryResult<InventoryExecution>(true, execution);
        } catch (Exception e) {
            InventoryExecution execution = new InventoryExecution(inventoryId,
                InventoryStateEnum.INNER_ERROR);
            return new InventoryResult<InventoryExecution>(true, execution);
        }
    }

    @RequestMapping(value = "/time/now", method = RequestMethod.GET)
    @ResponseBody
    public InventoryResult<Long> time() {
        Date now = new Date();
        return new InventoryResult<Long>(true, now.getTime());
    }
}
