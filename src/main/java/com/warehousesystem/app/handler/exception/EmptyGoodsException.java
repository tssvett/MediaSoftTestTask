package com.warehousesystem.app.handler.exception;

public class EmptyGoodsException extends Throwable {

    public EmptyGoodsException() {
        super("Goods list is empty");
    }
}