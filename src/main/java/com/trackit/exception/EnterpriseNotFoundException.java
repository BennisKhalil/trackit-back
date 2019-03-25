package com.trackit.exception;

public class EnterpriseNotFoundException extends Exception{
    public EnterpriseNotFoundException(String message, Integer id) {
        super(message + id);
    }
}
