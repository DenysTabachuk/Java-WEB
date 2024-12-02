package com.example.space_cats.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class CosmicWordCheckValidator implements ConstraintValidator<CosmicWordCheck, String> {
    private static final List<String> COSMIC_WORDS = Arrays.asList("star", "galaxy", "comet", "planet", "universe", "space", "blaster", "cosmic");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        for (String cosmicWord : COSMIC_WORDS) {
            if (value.toLowerCase().contains(cosmicWord)) {
                return true;
            }
        }

        return false;
    }
}
