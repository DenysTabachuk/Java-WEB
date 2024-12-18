package com.example.space_cats.featureToggle;

import org.springframework.stereotype.Service;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class FeatureToggleService {
    private final ConcurrentHashMap<String, Boolean> featuresToggles;

    public FeatureToggleService(FeatureToggleConfig featureToggleConfig) {
        featuresToggles = new ConcurrentHashMap<>(featureToggleConfig.getToggles());
    }
    public boolean isEnabled(String feature){
        return featuresToggles.get(feature);
    }
}
