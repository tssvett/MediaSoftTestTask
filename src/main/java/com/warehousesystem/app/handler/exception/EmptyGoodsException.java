package com.warehousesystem.app.handler.exception;

public class EmptyGoodsException extends Throwable {
    public EmptyGoodsException() {
        super("There are no goods in warehouse");
    }
}
