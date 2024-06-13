package com.warehousesystem.app.handler.exception;

public class WrongCustomerIdException extends Throwable {
    public WrongCustomerIdException(String message) {
        super(message);
    }
}
