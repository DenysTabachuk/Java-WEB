package com.example.space_cats.service.exceptions;

import java.util.UUID;

public class SpaceCatNotFoundException extends RuntimeException {
    public SpaceCatNotFoundException(UUID id){
        super(String.format("Space cat with id - %s not found", id));
    }

    public SpaceCatNotFoundException(String email){
        super(String.format("Space cat with email - %s not found", email));
    }

}
