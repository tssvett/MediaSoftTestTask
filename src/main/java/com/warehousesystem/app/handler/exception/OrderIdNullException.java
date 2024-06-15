package com.warehousesystem.app.handler.exception;

public class OrderIdNullException extends RuntimeException {
    public OrderIdNullException(String message) {
        super(message);
    }
}
