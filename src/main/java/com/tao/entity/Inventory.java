/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.tao.entity;

import java.util.Date;

/**
 * 
 * @author Ulric
 * @version $Id: Inventory.java, v 1.0 2017年4月1日 下午1:07:28 Ulric Exp $
 */
public class Inventory{

    private long   inventoryId;

    private String name;

    private int    number;

    private Date   startTime;

    private Date   endTime;

    private Date   createTime;

    public long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Inventory [inventoryId=" + inventoryId + ", name=" + name + ", number=" + number
               + ", startTime=" + startTime + ", endTime=" + endTime + ", createTime=" + createTime
               + "]";
    }

}
