package com.ratepay.bugtracker.exceptions.custom;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String message){
        super(message);
    }
}
