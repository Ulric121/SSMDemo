package com.tao.exception;

/**
 * �ظ���ɱ�쳣���������쳣��
 * 
 * @author Ulric
 * @version $Id: RepeatKillException.java, v 1.0 2017��4��1�� ����7:47:12 Ulric Exp $
 */
public class RepeatKillException extends InventoryException {

    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }

}
