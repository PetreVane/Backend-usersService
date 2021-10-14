package com.orbsec.photobackendusersapi.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.var;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.UnknownHostException;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(UserNotRegistered.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "User not registered. An account with this email address already exists.")
    public CustomError registrationErrorHandler(UserNotRegistered exception) {
        var error = new CustomError();
        error.setErrorMessage(exception.getMessage());
        error.setStatusCode(HttpStatus.BAD_REQUEST.value());
        error.setTimestamp(System.currentTimeMillis());
        return error;
    }

    @ExceptionHandler(UserAccountNotFound.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "There is no user with this email address!")
    public CustomError userMissingErrorHandler (UserAccountNotFound exception) {
        var error = new CustomError();
        error.setErrorMessage(exception.getMessage());
        error.setStatusCode(HttpStatus.NOT_FOUND.value());
        error.setTimestamp(System.currentTimeMillis());
        return error;
    }

    @ExceptionHandler(UnknownHostException.class)
    @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE, reason = "Unknown Host Exception thrown!")
    public CustomError handleUnknownHost (UnknownHostException exception) {
        var error = new CustomError();
        error.setErrorMessage(exception.getMessage());
        error.setStatusCode(HttpStatus.NOT_FOUND.value());
        error.setTimestamp(System.currentTimeMillis());
        return error;
    }

    @ExceptionHandler
    public ResponseEntity<CustomError> handleExpiredToken (TokenExpiredException exception) {
        var error = new CustomError();
        error.setErrorMessage(exception.getMessage());
        error.setStatusCode(HttpStatus.EXPECTATION_FAILED.value());
        error.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.EXPECTATION_FAILED);
    }

}
