package com.warehousesystem.app.handler.exception;

public class NotFoundByIdException extends Throwable {

    public NotFoundByIdException() {
        super("There are no goods with this id");
    }
}
