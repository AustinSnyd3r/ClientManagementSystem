package com.cm.clientservice.exception.schedule;

public class MovementNotFoundException extends RuntimeException {
    public MovementNotFoundException(String message){
        super(message);
    }
}
