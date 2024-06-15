package com.warehousesystem.app.handler.exception;

public class NotFoundByIdException extends RuntimeException{

    public NotFoundByIdException() {
        super("Good not found");
    }
}
