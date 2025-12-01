package com.cm.clientservice.exception;

public class UnauthorizedScheduleAccessException extends RuntimeException{
    public UnauthorizedScheduleAccessException(String message){
        super(message);
    }
}
