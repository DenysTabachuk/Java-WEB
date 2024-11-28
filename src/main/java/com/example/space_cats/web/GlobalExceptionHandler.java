package com.example.space_cats.web;

import com.example.space_cats.service.exceptions.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProductController.ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {

        String responseErrorText = "";

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            responseErrorText += "Field: " + error.getField() + ". ";
            responseErrorText += "Error: " + error.getDefaultMessage() +". ";
        }

        ProductController.ErrorResponse errorResponse = new ProductController.ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Bad request. Object field validation Error",
                responseErrorText,
                request.getDescription(false).replace("uri=", "")
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ProductController.ErrorResponse> handleProductNotFoundException(ProductNotFoundException ex, WebRequest request) {


        ProductController.ErrorResponse errorResponse = new ProductController.ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Product not found",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }


}
