package com.warehousesystem.app.handler.exception;

public class NotFoundByArticleException extends RuntimeException{

    public NotFoundByArticleException() {
        super("Article not found");
    }
}
