package com.tao.exception;

/**
 * 秒杀关闭异常（运行期异常）
 * 
 * @author Ulric
 * @version $Id: InventoryCloseException.java, v 1.0 2017年4月1日 下午7:52:16 Ulric Exp $
 */
public class InventoryCloseException extends InventoryException {

    public InventoryCloseException(String message, Throwable cause) {
        super(message, cause);
    }

    public InventoryCloseException(String message) {
        super(message);
    }

}
