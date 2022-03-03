package com.xyz.bank.exception;

import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(CustomerNotFoundException ex, WebRequest request) {
        log.error("error occurred",ex);
         return new ResponseEntity<>(new ErrorDetails(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> globleExcpetionHandler(MethodArgumentNotValidException ex, WebRequest request) {
        return new ResponseEntity<>(new ErrorDetails(ex.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(","))), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountAlreadyPresent.class)
    public ResponseEntity<?> resourceNotFoundException(AccountAlreadyPresent ex, WebRequest request) {
        log.error("error occurred",ex);
        return new ResponseEntity<>(new ErrorDetails(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globleExcpetionHandler(Exception ex, WebRequest request) {
        log.error("error occurred",ex);
        return new ResponseEntity<>(new ErrorDetails("Technical error occurred"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
