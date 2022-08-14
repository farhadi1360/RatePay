package com.ratepay.bugtracker.exceptions.custom;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String s) {
        super(s);
    }
}
