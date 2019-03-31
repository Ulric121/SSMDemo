package com.tao.dto;

/**
 * ���е�ajax���󷵻����ͣ���װjson���
 * 
 * @author Ulric
 * @version $Id: InventoryResult.java, v 1.0 2017��4��2�� ����11:53:36 Ulric Exp $
 */
public class InventoryResult<T> {

    private boolean success;

    private T       data;

    private String  error;

    public InventoryResult(boolean success, String error) {
        super();
        this.success = success;
        this.error = error;
    }

    public InventoryResult(boolean success, T data) {
        super();
        this.success = success;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
