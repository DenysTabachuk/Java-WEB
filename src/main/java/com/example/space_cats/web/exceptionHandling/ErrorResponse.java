package com.example.space_cats.web.exceptionHandling;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public  class ErrorResponse {
    private int status;
    private String error;
    private String message;
    private String path;
}


//  I tried to use problemDetails but for some reason @ExceptionHandler stops catching the error