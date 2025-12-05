package com.cm.clientservice.exception.schedule;

public class TrainingScheduleNotFoundException extends RuntimeException{
    public TrainingScheduleNotFoundException(String message){
        super(message);
    }
}
