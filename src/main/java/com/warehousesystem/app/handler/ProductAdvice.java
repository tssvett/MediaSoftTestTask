package com.warehousesystem.app.handler;

import com.warehousesystem.app.handler.Exception.EmptyProductException;
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
public class ProductAdvice {

    @ExceptionHandler(NotFoundByIdException.class)
    public ResponseEntity<ErrorDetails> handleException(NotFoundByIdException e) {
        LocalDateTime time = LocalDateTime.now();
        List<String> errorMessage = List.of(e.getMessage());
        String exceptionName = e.getClass().getSimpleName();
        String exceptionClass = e.getStackTrace()[0].getClassName();
        ErrorDetails response = new ErrorDetails(exceptionName, exceptionClass, errorMessage, time);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmptyProductException.class)
    public ResponseEntity<ErrorDetails> handleEmptyGoodsException(EmptyProductException e) {
        LocalDateTime time = LocalDateTime.now();
        List<String> errorMessage = List.of(e.getMessage());
        String exceptionName = e.getClass().getSimpleName();
        String exceptionClass = e.getStackTrace()[0].getClassName();
        ErrorDetails response = new ErrorDetails(exceptionName, exceptionClass, errorMessage, time);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handleException(MethodArgumentNotValidException e) {
        LocalDateTime time = LocalDateTime.now();
        List<String> errors = e.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> "Field '" + fieldError.getField() + "': " + fieldError.getDefaultMessage())
                .toList();
        String exceptionName = e.getClass().getSimpleName();
        String exceptionClass = e.getStackTrace()[0].getClassName();
        ErrorDetails response = new ErrorDetails(exceptionName, exceptionClass, errors, time);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLUniqueException.class)
    public ResponseEntity<ErrorDetails> handleException(SQLUniqueException e) {
        LocalDateTime time = LocalDateTime.now();
        List<String> errorMessage = List.of(e.getMessage());
        String exceptionName = e.getClass().getSimpleName();
        String exceptionClass = e.getStackTrace()[0].getClassName();
        ErrorDetails response = new ErrorDetails(exceptionName, exceptionClass, errorMessage, time);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorDetails> handleException(MethodArgumentTypeMismatchException e) {
        LocalDateTime time = LocalDateTime.now();
        List<String> errorMessage = List.of(e.getMessage());
        String exceptionName = e.getClass().getSimpleName();
        String exceptionClass = e.getStackTrace()[0].getClassName();
        ErrorDetails response = new ErrorDetails(exceptionName, exceptionClass, errorMessage, time);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDetails> handleException(ConstraintViolationException e) {
        LocalDateTime time = LocalDateTime.now();
        List<String> errorMessage = List.of(e.getMessage());
        String exceptionName = e.getClass().getSimpleName();
        String exceptionClass = e.getStackTrace()[0].getClassName();
        ErrorDetails response = new ErrorDetails(exceptionName, exceptionClass, errorMessage, time);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundByArticleException.class)
    public ResponseEntity<ErrorDetails> handleException(NotFoundByArticleException e) {
        LocalDateTime time = LocalDateTime.now();
        List<String> errorMessage = List.of(e.getMessage());
        String exceptionName = e.getClass().getSimpleName();
        String exceptionClass = e.getStackTrace()[0].getClassName();
        ErrorDetails response = new ErrorDetails(exceptionName, exceptionClass, errorMessage, time);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
