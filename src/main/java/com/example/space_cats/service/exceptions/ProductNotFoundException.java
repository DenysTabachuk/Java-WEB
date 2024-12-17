package com.example.space_cats.service.exceptions;

import java.util.UUID;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(UUID id){
        super(String.format("Product with id - %s not found", id));
    }
}
