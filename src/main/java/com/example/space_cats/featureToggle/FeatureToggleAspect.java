package com.example.space_cats.featureToggle;

import com.example.space_cats.featureToggle.exceptions.FeatureNotEnableException;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class FeatureToggleAspect {
    private final FeatureToggleService featureToggleService;

    @Autowired
    public FeatureToggleAspect(FeatureToggleService featureToggleService) {
        this.featureToggleService = featureToggleService;
    }

    @Before("@annotation(featureToggle)")
    public void checkIfFeatureIsEnabledBefore(FeatureToggle featureToggle){
        ToggleableFeature feature = featureToggle.value();
        boolean isFeatureEnabled = featureToggleService.isEnabled(feature.getName());
        if (!isFeatureEnabled)
            throw new FeatureNotEnableException(feature.getName());
    }

}
