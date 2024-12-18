package com.example.space_cats.web;

import com.example.space_cats.featureToggle.FeatureToggleService;
import com.example.space_cats.featureToggle.ToggleableFeature;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class SpaceCatControllerIT {
    @MockBean
    private FeatureToggleService featureToggleService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnSpaceCatsIfFeatureIsEnabled() throws Exception{
        Mockito.when(featureToggleService.isEnabled(ToggleableFeature.SPACE_CATS_FEATURE.getName())).thenReturn(true);

        mockMvc.perform(get("/api/v1/space-cats")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnBadRequestIfFeatureIsNotEnabled() throws Exception{
        Mockito.when(featureToggleService.isEnabled(ToggleableFeature.SPACE_CATS_FEATURE.getName())).thenReturn(false);

        mockMvc.perform(get("/api/v1/space-cats")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Feature spaceCats is NOT enabled"))
                .andExpect(jsonPath("$.error").value("The requested feature is currently disabled"))
                .andExpect(jsonPath("$.path").value("/api/v1/space-cats"));
    }
}
