package com.ratepay.bugtracker.exceptions.custom;

public class UsernameNotAvailableException extends RuntimeException {
    public UsernameNotAvailableException(String s) {
        super(s);
    }
}
