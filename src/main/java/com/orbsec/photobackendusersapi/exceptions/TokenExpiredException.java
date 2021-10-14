package com.orbsec.photobackendusersapi.exceptions;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;

public class TokenExpiredException extends ExpiredJwtException {


    public TokenExpiredException(Header header, Claims claims, String message) {
        super(header, claims, message);
    }

    public TokenExpiredException(Header header, Claims claims, String message, Throwable cause) {
        super(header, claims, message, cause);
    }
}
