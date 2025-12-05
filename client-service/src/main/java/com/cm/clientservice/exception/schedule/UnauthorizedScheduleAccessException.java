package com.cm.clientservice.exception.schedule;

public class UnauthorizedScheduleAccessException extends RuntimeException{
    public UnauthorizedScheduleAccessException(String message){
        super(message);
    }
}
