package com.cm.clientservice.exception;

public class MovementNotFoundException extends RuntimeException {
    public MovementNotFoundException(String message){
        super(message);
    }
}
