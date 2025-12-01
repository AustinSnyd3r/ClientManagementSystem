package com.cm.clientservice.exception;

public class TrainingScheduleNotFoundException extends RuntimeException{
    public TrainingScheduleNotFoundException(String message){
        super(message);
    }
}
