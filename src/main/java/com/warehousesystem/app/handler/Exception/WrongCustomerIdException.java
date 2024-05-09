package com.warehousesystem.app.handler.Exception;

public class WrongCustomerIdException extends Throwable {
    public WrongCustomerIdException(String message) {
        super(message);
    }
}
