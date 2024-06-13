package com.warehousesystem.app.handler;

import com.warehousesystem.app.handler.exception.CustomerIdNullException;
import com.warehousesystem.app.handler.exception.EmptyProductException;
import com.warehousesystem.app.handler.exception.NotEnoughProductsException;
import com.warehousesystem.app.handler.exception.NotFoundByArticleException;
import com.warehousesystem.app.handler.exception.NotFoundByIdException;
import com.warehousesystem.app.handler.exception.SQLUniqueException;
import com.warehousesystem.app.handler.exception.UnavailableProductException;
import com.warehousesystem.app.handler.exception.UpdateOrderException;
import com.warehousesystem.app.handler.exception.WrongCustomerIdException;
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

    @ExceptionHandler(CustomerIdNullException.class)
    public ResponseEntity<ErrorDetails> handleException(CustomerIdNullException e) {
        LocalDateTime time = LocalDateTime.now();
        List<String> errorMessage = List.of(e.getMessage());
        String exceptionName = e.getClass().getSimpleName();
        String exceptionClass = e.getStackTrace()[0].getClassName();
        ErrorDetails response = new ErrorDetails(exceptionName, exceptionClass, errorMessage, time);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotEnoughProductsException.class)
    public ResponseEntity<ErrorDetails> handleException(NotEnoughProductsException e) {
        LocalDateTime time = LocalDateTime.now();
        List<String> errorMessage = List.of(e.getMessage());
        String exceptionName = e.getClass().getSimpleName();
        String exceptionClass = e.getStackTrace()[0].getClassName();
        ErrorDetails response = new ErrorDetails(exceptionName, exceptionClass, errorMessage, time);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorDetails> handleException(NullPointerException e) {
        LocalDateTime time = LocalDateTime.now();
        List<String> errorMessage = List.of(e.getMessage());
        String exceptionName = e.getClass().getSimpleName();
        String exceptionClass = e.getStackTrace()[0].getClassName();
        ErrorDetails response = new ErrorDetails(exceptionName, exceptionClass, errorMessage, time);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnavailableProductException.class)
    public ResponseEntity<ErrorDetails> handleException(UnavailableProductException e) {
        LocalDateTime time = LocalDateTime.now();
        List<String> errorMessage = List.of(e.getMessage());
        String exceptionName = e.getClass().getSimpleName();
        String exceptionClass = e.getStackTrace()[0].getClassName();
        ErrorDetails response = new ErrorDetails(exceptionName, exceptionClass, errorMessage, time);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UpdateOrderException.class)
    public ResponseEntity<ErrorDetails> handleException(UpdateOrderException e) {
        LocalDateTime time = LocalDateTime.now();
        List<String> errorMessage = List.of(e.getMessage());
        String exceptionName = e.getClass().getSimpleName();
        String exceptionClass = e.getStackTrace()[0].getClassName();
        ErrorDetails response = new ErrorDetails(exceptionName, exceptionClass, errorMessage, time);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WrongCustomerIdException.class)
    public ResponseEntity<ErrorDetails> handleException(WrongCustomerIdException e) {
        LocalDateTime time = LocalDateTime.now();
        List<String> errorMessage = List.of(e.getMessage());
        String exceptionName = e.getClass().getSimpleName();
        String exceptionClass = e.getStackTrace()[0].getClassName();
        ErrorDetails response = new ErrorDetails(exceptionName, exceptionClass, errorMessage, time);
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }
}
