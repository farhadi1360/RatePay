package com.ratepay.bugtracker.exceptions.custom;
/**
 * Copyright (c) 2019 , Fanap  Project . All Rights Reserved
 * Created by Mostafa.Farhadi on 3/3/2019.
 */
import org.springframework.security.core.AuthenticationException;


public class UserNotActivatedException extends AuthenticationException {

    private static final long serialVersionUID = 1L;

    public UserNotActivatedException(String message) {
        super(message);
    }

    public UserNotActivatedException(String message, Throwable t) {
        super(message, t);
    }
}
