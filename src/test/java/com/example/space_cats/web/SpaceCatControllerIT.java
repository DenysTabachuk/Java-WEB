package com.example.space_cats.web;

import com.example.space_cats.dto.SpaceCatDTO;
import com.example.space_cats.entity.SpaceCatEntity;
import com.example.space_cats.featureToggle.FeatureToggleService;
import com.example.space_cats.featureToggle.ToggleableFeature;
import com.example.space_cats.repository.SpaceCatRepository;
import com.example.space_cats.service.spaceCat.SpaceCatService;
import com.example.space_cats.web.mappers.SpaceCatEntityDtoMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SpaceCatControllerIT {
    @MockBean
    private FeatureToggleService featureToggleService;
    @SpyBean
    private SpaceCatService spaceCatService;
    @Autowired
    private SpaceCatRepository spaceCatRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private SpaceCatEntityDtoMapper spaceCatEntityDtoMapper;

    @BeforeEach
    void setUp() {
        reset(spaceCatService);
        spaceCatRepository.deleteAll();
    }

    private SpaceCatDTO getValidSpaceCatDTO(){
        SpaceCatDTO spaceCatDTO = SpaceCatDTO.builder()
                .name("Ryzhyk")
                .email("fluffy@gmail.com")
                .address("Star Cluster NGC 6205, Sector 8, Orbital Zone 3, Milky Way Galaxy, Universe Quadrant 42")
                .phoneNumber("0994592831")
                .build();

        return spaceCatDTO;
    }

    private SpaceCatDTO getUnvalidSpaceCatDTO(){
        SpaceCatDTO spaceCatDTO = SpaceCatDTO.builder()
                .name("Ryzhyk")
                .email("fluffygmail.com")
                .address("Star Cluster NGC 6205, Sector 8, Orbital Zone 3, Milky Way Galaxy, Universe Quadrant 42")
                .phoneNumber("911")
                .build();

        return spaceCatDTO;
    }

    private void addSpaceCatEntityForTest(){
        SpaceCatEntity spaceCatEntity = spaceCatEntityDtoMapper.toEntity(getValidSpaceCatDTO());
        spaceCatEntity.setId(UUID.randomUUID());
        spaceCatRepository.save(spaceCatEntity);
    }

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

    @Test
    void shouldCreateCat() throws Exception {
        Mockito.when(featureToggleService.isEnabled(ToggleableFeature.SPACE_CATS_FEATURE.getName())).thenReturn(true);

        String spaceCatJson = objectMapper.writeValueAsString(getValidSpaceCatDTO());

        mockMvc.perform(post("/api/v1/space-cats")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(spaceCatJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Ryzhyk"))
                .andExpect(jsonPath("$.email").value("fluffy@gmail.com"))
                .andExpect(jsonPath("$.address").value("Star Cluster NGC 6205, Sector 8, Orbital Zone 3, Milky Way Galaxy, Universe Quadrant 42"))
                .andExpect(jsonPath("$.phoneNumber").value("0994592831"));

        assertThat(spaceCatRepository.findByEmail("fluffy@gmail.com") ).isPresent();
    }

    @Test
    void shouldNotCreateCatWhenFieldIsNotValid() throws Exception {
        Mockito.when(featureToggleService.isEnabled(ToggleableFeature.SPACE_CATS_FEATURE.getName())).thenReturn(true);

        String spaceCatJson = objectMapper.writeValueAsString(getUnvalidSpaceCatDTO());

        mockMvc.perform(post("/api/v1/space-cats")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(spaceCatJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Bad request. Object field validation Error"));

        assertThat(spaceCatRepository.findByEmail("fluffy@gmail.com") ).isEmpty();
    }

    @Test
    void shouldGetNotFoundExceptionIfCatDoesntExist() throws Exception{
        Mockito.when(featureToggleService.isEnabled(ToggleableFeature.SPACE_CATS_FEATURE.getName())).thenReturn(true);

        UUID randomUUID = UUID.randomUUID();

        mockMvc.perform(get("/api/v1/space-cats/{id}", randomUUID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("SpaceCat not found"))
                .andExpect(jsonPath("$.message").value(String.format("Space cat with id - %s not found", randomUUID) ));;
    }

    @Test
    void shouldGetCatByIdAndEmail() throws Exception{
        addSpaceCatEntityForTest();

        Mockito.when(featureToggleService.isEnabled(ToggleableFeature.SPACE_CATS_FEATURE.getName())).thenReturn(true);

        SpaceCatEntity spaceCatEntity = spaceCatRepository.findByEmail("fluffy@gmail.com").get();

        mockMvc.perform(get("/api/v1/space-cats/{id}", spaceCatEntity.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ryzhyk"))
                .andExpect(jsonPath("$.email").value("fluffy@gmail.com"))
                .andExpect(jsonPath("$.phoneNumber").value("0994592831"));

        mockMvc.perform(get("/api/v1/space-cats/email/{email}", spaceCatEntity.getEmail())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ryzhyk"))
                .andExpect(jsonPath("$.email").value("fluffy@gmail.com"))
                .andExpect(jsonPath("$.phoneNumber").value("0994592831"));
    }

    @Test
    void shouldUpdateSpaceCat() throws Exception{
        addSpaceCatEntityForTest();

        Mockito.when(featureToggleService.isEnabled(ToggleableFeature.SPACE_CATS_FEATURE.getName())).thenReturn(true);

        SpaceCatEntity spaceCatEntity = spaceCatRepository.findByEmail("fluffy@gmail.com").get();

        SpaceCatDTO spaceCatDTO = SpaceCatDTO.builder()
                .name("New Name")
                .email("fluffy@gmail.com")
                .address("Star Cluster NGC 6205, Sector 8, Orbital Zone 3, Milky Way Galaxy, Universe Quadrant 42")
                .phoneNumber("0994592831")
                .build();

        String spaceCatJson = objectMapper.writeValueAsString(spaceCatDTO);

        mockMvc.perform(put("/api/v1/space-cats/{id}", spaceCatEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(spaceCatJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Name"));
    }

    @Test
    void shouldDeleteSpaceCat() throws Exception{
        addSpaceCatEntityForTest();

        Mockito.when(featureToggleService.isEnabled(ToggleableFeature.SPACE_CATS_FEATURE.getName())).thenReturn(true);

        SpaceCatEntity spaceCatEntity = spaceCatRepository.findByEmail("fluffy@gmail.com").get();

        mockMvc.perform(delete("/api/v1/space-cats/{id}", spaceCatEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(String.format("SpaceCat with ID %s deleted successfully.", spaceCatEntity.getId())));
    }
}


