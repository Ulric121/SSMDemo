package com.tao.exception;

/**
 * ��ɱ�ر��쳣���������쳣��
 * 
 * @author Ulric
 * @version $Id: InventoryCloseException.java, v 1.0 2017��4��1�� ����7:52:16 Ulric Exp $
 */
public class InventoryCloseException extends InventoryException {

    public InventoryCloseException(String message, Throwable cause) {
        super(message, cause);
    }

    public InventoryCloseException(String message) {
        super(message);
    }

}
