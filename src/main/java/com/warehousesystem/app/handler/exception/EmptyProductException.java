package com.warehousesystem.app.handler.exception;

public class EmptyProductException extends Throwable {

    public EmptyProductException() {
        super("Goods list is empty");
    }
}