package com.example.space_cats.service.exceptions;

import java.util.UUID;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(UUID id){
        super("Product with id - " + id + " not found");
    }

}
