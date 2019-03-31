package com.tao.dto;

import com.tao.entity.SuccessKilled;
import com.tao.enums.InventoryStateEnum;

/**
 * ��װ��ɱִ�к�Ľ��
 * 
 * @author Ulric
 * @version $Id: InventoryExecution.java, v 1.0 2017��4��1�� ����7:43:50 Ulric Exp $
 */
public class InventoryExecution {

    private long          inventoryId;  //��Ʒid

    private int           stats;        //��ɱִ�н��״̬

    private String        stateInfo;    //״̬��ʶ

    private SuccessKilled successKilled;//��ɱ�ɹ�����

    public InventoryExecution(long inventoryId, InventoryStateEnum inventoryStateEnum,
                              SuccessKilled successKilled) {
        super();
        this.inventoryId = inventoryId;
        this.stats = inventoryStateEnum.getState();
        this.stateInfo = inventoryStateEnum.getStateInfo();
        this.successKilled = successKilled;
    }

    public InventoryExecution(long inventoryId, InventoryStateEnum inventoryStateEnum) {
        super();
        this.inventoryId = inventoryId;
        this.stats = inventoryStateEnum.getState();
        this.stateInfo = inventoryStateEnum.getStateInfo();
    }

    public long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public int getStats() {
        return stats;
    }

    public void setStats(int stats) {
        this.stats = stats;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public SuccessKilled getSuccessKilled() {
        return successKilled;
    }

    public void setSuccessKilled(SuccessKilled successKilled) {
        this.successKilled = successKilled;
    }

    @Override
    public String toString() {
        return "InventoryExecution [inventoryId=" + inventoryId + ", stats=" + stats
               + ", stateInfo=" + stateInfo + ", successKilled=" + successKilled + "]";
    }

}
