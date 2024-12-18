package com.example.space_cats.featureToggle;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import java.util.Map;


@NoArgsConstructor
@Data
@Configuration
public class FeatureToggleConfig {

    @Value("${application.feature.spaceCats.enabled}")
    private boolean spaceCats;

    @Value("${application.feature.kittyProducts.enabled}")
    private boolean kittyProducts;

    public Map<String, Boolean> getToggles() {
        return Map.of(
                "spaceCats", spaceCats,
                "kittyProducts", kittyProducts
        );
    }
}