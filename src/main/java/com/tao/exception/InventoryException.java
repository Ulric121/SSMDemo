package com.tao.exception;

/**
 * ��ɱ����쳣���������쳣��
 * 
 * @author Ulric
 * @version $Id: InventoryException.java, v 1.0 2017��4��1�� ����7:53:27 Ulric Exp $
 */
public class InventoryException extends RuntimeException {

    public InventoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public InventoryException(String message) {
        super(message);
    }

}
