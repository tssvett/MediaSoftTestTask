package com.warehousesystem.app.errorhandler.Exception;

public class WrongCustomerIdException extends Throwable {
    public WrongCustomerIdException(String message) {
        super(message);
    }
}
