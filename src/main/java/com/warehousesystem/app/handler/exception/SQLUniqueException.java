package com.warehousesystem.app.handler.exception;

public class SQLUniqueException extends RuntimeException{

    public SQLUniqueException(String message) {
        super(message);
    }
}
