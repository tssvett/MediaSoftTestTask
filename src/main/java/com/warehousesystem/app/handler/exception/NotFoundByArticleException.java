package com.warehousesystem.app.handler.exception;

public class NotFoundByArticleException extends Throwable {
    public NotFoundByArticleException() {
        super("There are no goods with this article");
    }
}
