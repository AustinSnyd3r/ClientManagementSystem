package com.cm.clientservice.exception.schedule;

public class WorkoutNotFoundException extends RuntimeException{
    public WorkoutNotFoundException(String message){
        super(message);
    }
}
