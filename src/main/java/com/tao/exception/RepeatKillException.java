package com.tao.exception;

/**
 * 重复秒杀异常（运行期异常）
 * 
 * @author Ulric
 * @version $Id: RepeatKillException.java, v 1.0 2017年4月1日 下午7:47:12 Ulric Exp $
 */
public class RepeatKillException extends InventoryException {

    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }

}
