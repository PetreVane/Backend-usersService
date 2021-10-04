package com.orbsec.photobackendusersapi.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//import org.apache.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationError {
    private String errorMessage;
    private int statusCode;
    private Long timestamp;
}
