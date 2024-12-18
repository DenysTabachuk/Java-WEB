package com.example.space_cats.featureToggle.exceptions;

public class FeatureNotEnableException extends RuntimeException{
    public FeatureNotEnableException(String featureName){
        super(String.format("Feature %s is NOT enabled", featureName));
    }
}
