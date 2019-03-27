package com.trackit.exception;

public class DriverNotFoundException extends Exception {
    public DriverNotFoundException(String message, Integer id ){
        super(message + id);
    }
}
