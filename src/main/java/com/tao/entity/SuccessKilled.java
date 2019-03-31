/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.tao.entity;

import java.sql.Date;

/**
 * 
 * @author Ulric
 * @version $Id: SuccessKilled.java, v 1.0 2017年4月1日 下午1:13:05 Ulric Exp $
 */
public class SuccessKilled {

    private long      inventoryId;

    private long      userPhone;

    private short     state;

    private Date      createTime;

    private Inventory inventory;

    public long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public long getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(long userPhone) {
        this.userPhone = userPhone;
    }

    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public String toString() {
        return "SuccessKilled [inventoryId=" + inventoryId + ", userPhone=" + userPhone + ", state="
               + state + ", createTime=" + createTime + "]";
    }

}
