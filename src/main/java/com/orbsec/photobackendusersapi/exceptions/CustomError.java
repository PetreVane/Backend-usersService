package com.orbsec.photobackendusersapi.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomError {
    private String errorMessage;
    private int statusCode;
    private Long timestamp;
}
