package com.warehousesystem.app.handler.Exception;

public class NotEnoughProductsException  extends Throwable{

    public NotEnoughProductsException(String message) {
        super(message);
    }
}
