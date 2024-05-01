package com.warehousesystem.app.handler;

import com.warehousesystem.app.handler.Exception.EmptyGoodsException;
import com.warehousesystem.app.handler.Exception.NotFoundByIdException;
import com.warehousesystem.app.handler.Exception.NotFoundByArticleException;
import com.warehousesystem.app.handler.Exception.SQLUniqueException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
public class WarehouseAdvice {

    @ExceptionHandler(NotFoundByIdException.class)
    public ResponseEntity<Response> handleException(NotFoundByIdException e) {
        LocalDateTime time = LocalDateTime.now();
        List<String> errorMessage = List.of(e.getMessage());
        String exceptionName = e.getClass().getSimpleName();
        String exceptionClass = e.getStackTrace()[0].getClassName();
        Response response = new Response(exceptionName, exceptionClass, errorMessage, time);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmptyGoodsException.class)
    public ResponseEntity<Response> handleEmptyGoodsException(EmptyGoodsException e) {
        LocalDateTime time = LocalDateTime.now();
        List<String> errorMessage = List.of(e.getMessage());
        String exceptionName = e.getClass().getSimpleName();
        String exceptionClass = e.getStackTrace()[0].getClassName();
        Response response = new Response(exceptionName, exceptionClass, errorMessage, time);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> handleException(MethodArgumentNotValidException e) {
        LocalDateTime time = LocalDateTime.now();
        List<String> errors = e.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> "Field '" + fieldError.getField() + "': " + fieldError.getDefaultMessage())
                .toList();
        String exceptionName = e.getClass().getSimpleName();
        String exceptionClass = e.getStackTrace()[0].getClassName();
        Response response = new Response(exceptionName, exceptionClass, errors, time);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLUniqueException.class)
    public ResponseEntity<Response> handleException(SQLUniqueException e) {
        LocalDateTime time = LocalDateTime.now();
        List<String> errorMessage = List.of(e.getMessage());
        String exceptionName = e.getClass().getSimpleName();
        String exceptionClass = e.getStackTrace()[0].getClassName();
        Response response = new Response(exceptionName, exceptionClass, errorMessage, time);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Response> handleException(MethodArgumentTypeMismatchException e) {
        LocalDateTime time = LocalDateTime.now();
        List<String> errorMessage = List.of(e.getMessage());
        String exceptionName = e.getClass().getSimpleName();
        String exceptionClass = e.getStackTrace()[0].getClassName();
        Response response = new Response(exceptionName, exceptionClass, errorMessage, time);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Response> handleException(ConstraintViolationException e) {
        LocalDateTime time = LocalDateTime.now();
        List<String> errorMessage = List.of(e.getMessage());
        String exceptionName = e.getClass().getSimpleName();
        String exceptionClass = e.getStackTrace()[0].getClassName();
        Response response = new Response(exceptionName, exceptionClass, errorMessage, time);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundByArticleException.class)
    public ResponseEntity<Response> handleException(NotFoundByArticleException e) {
        LocalDateTime time = LocalDateTime.now();
        List<String> errorMessage = List.of(e.getMessage());
        String exceptionName = e.getClass().getSimpleName();
        String exceptionClass = e.getStackTrace()[0].getClassName();
        Response response = new Response(exceptionName, exceptionClass, errorMessage, time);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
