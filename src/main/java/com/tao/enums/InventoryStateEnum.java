package com.tao.enums;

/**
 * ʹ��ö�ٱ������������ֵ�
 * 
 * @author Ulric
 * @version $Id: InventoryEnum.java, v 1.0 2017��4��1�� ����8:41:33 Ulric Exp $
 */
public enum InventoryStateEnum {
    SUCCESS(1,"��ɱ�ɹ�"),
    END(0,"��ɱ����"),
    REPEAT_KILL(-1,"�ظ���ɱ"),
    INNER_ERROR(-2,"ϵͳ�쳣"),
    DATA_REWRITE(-3,"���ݴ۸�");

    private int    state;

    private String stateInfo;

    private InventoryStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    /**
     * ����state����ö������
     * @param index
     * @return
     */
    public static InventoryStateEnum stateOf(int index) {
        for (InventoryStateEnum state : values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }

}
