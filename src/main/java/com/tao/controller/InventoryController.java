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
 * @version $Id: InventoryController.java, v 1.0 2017年4月2日 上午11:07:02 Ulric Exp $
 */
@Controller
@RequestMapping("/inventory") //URL：/模块/资源/{id}/细分
public class InventoryController {
    private final Logger     logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private InventoryService inventoryService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        //获取列表页
        List<Inventory> list = inventoryService.getInventoryList();
        model.addAttribute("list", list);
        return "list";
    }

    @RequestMapping(value = "/{inventoryId}/detail", method = RequestMethod.GET)
    public String detail(@PathVariable("inventoryId") Long inventoryId, Model model) {
        if (inventoryId == null) {
            //inventoryId不存在，重定向到list请求
            return "redirect:/inventory/list";//重定向
        }
        Inventory inventory = inventoryService.getById(inventoryId);
        if (inventory == null) {
            return "forward:/inventory/list";//转发
        }
        model.addAttribute("inventory", inventory);
        return "detail";
    }

    /**ajax访问，返回json，不需要Model作为参数传入*/
    @RequestMapping(value = "/{inventoryId}/exposer", 
            method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    @ResponseBody //SpringMVC看到此注解，会把返回类型作为ajax输出，默认的ajax输出格式是json格式，即会将InventoryResult<Exposer>封装成json
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
            return new InventoryResult<InventoryExecution>(false, "未注册");
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
