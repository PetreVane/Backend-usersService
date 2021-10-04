package com.orbsec.photobackendusersapi.exceptions;


import com.orbsec.photobackendusersapi.domain.models.User;

import java.util.function.Supplier;

public class UserNotRegistered extends RuntimeException {

    public UserNotRegistered(String message) {
        super(message);
    }


}
