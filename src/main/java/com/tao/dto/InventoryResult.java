package com.tao.dto;

/**
 * 所有的ajax请求返回类型，封装json结果
 * 
 * @author Ulric
 * @version $Id: InventoryResult.java, v 1.0 2017年4月2日 上午11:53:36 Ulric Exp $
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
