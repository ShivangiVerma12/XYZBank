package com.xyz.bank.exception;
import java.util.Date;

public class ErrorDetails {
    private String message;

    public ErrorDetails(String message) {
         this.message = message;
    }

    public String getMessage() {
         return message;
    }

}