package com.warehousesystem.app.handler.exception;

public class NotEnoughProductsException  extends RuntimeException{

    public NotEnoughProductsException(String message) {
        super(message);
    }
}
