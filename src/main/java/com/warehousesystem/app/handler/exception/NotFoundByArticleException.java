package com.warehousesystem.app.handler.exception;

public class NotFoundByArticleException extends Throwable{

    public NotFoundByArticleException() {
        super("Article not found");
    }
}
