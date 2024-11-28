package com.example.space_cats.service.exceptions;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(long id){
        super("There is no prdouct with id - " + id);
    }

}
