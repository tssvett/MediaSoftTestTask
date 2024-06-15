package com.warehousesystem.app.handler.exception;

public class EmptyProductException extends RuntimeException {

    public EmptyProductException() {
        super("Goods list is empty");
    }
}