package com.warehousesystem.app.handler.exception;

public class WrongCustomerIdException extends RuntimeException {
    public WrongCustomerIdException(String message) {
        super(message);
    }
}
