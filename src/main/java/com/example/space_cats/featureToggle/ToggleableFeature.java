package com.example.space_cats.featureToggle;

import lombok.Getter;

@Getter
public enum ToggleableFeature {
    SPACE_CATS_FEATURE("spaceCats"), KITTY_PRODUCTS_FEATURE("kittyProducts");

    private final String name;
    ToggleableFeature(String featureName){
        this.name = featureName;
    }
}
