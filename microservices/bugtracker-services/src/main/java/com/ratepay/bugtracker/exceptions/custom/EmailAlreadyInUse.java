package com.ratepay.bugtracker.exceptions.custom;

public class EmailAlreadyInUse extends RuntimeException {
    public EmailAlreadyInUse(String s) {
        super(s);
    }
}
