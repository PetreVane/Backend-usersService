package com.orbsec.photobackendusersapi.exceptions;

public class UserAccountNotFound extends RuntimeException {
    public UserAccountNotFound(String message) {
        super(message);
    }
}
