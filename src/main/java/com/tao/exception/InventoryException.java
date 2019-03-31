package com.tao.exception;

/**
 * 秒杀相关异常（运行期异常）
 * 
 * @author Ulric
 * @version $Id: InventoryException.java, v 1.0 2017年4月1日 下午7:53:27 Ulric Exp $
 */
public class InventoryException extends RuntimeException {

    public InventoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public InventoryException(String message) {
        super(message);
    }

}
