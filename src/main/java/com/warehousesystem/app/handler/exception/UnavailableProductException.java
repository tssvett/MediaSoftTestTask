package com.warehousesystem.app.handler.exception;

public class UnavailableProductException extends RuntimeException {

    public UnavailableProductException(String message) {
        super(message);
    }
}
