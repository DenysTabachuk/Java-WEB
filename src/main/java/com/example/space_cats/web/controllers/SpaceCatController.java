package com.example.space_cats.web.controllers;

import com.example.space_cats.dto.SpaceCatDTO;
import com.example.space_cats.featureToggle.FeatureToggle;
import com.example.space_cats.featureToggle.ToggleableFeature;
import com.example.space_cats.service.spaceCat.SpaceCatService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/api/v1/space-cats")
public class SpaceCatController {
    private final SpaceCatService spaceCatService;

    @Autowired
    public SpaceCatController(SpaceCatService spaceCatService){
        this.spaceCatService = spaceCatService;
    }

    @GetMapping
    @FeatureToggle(ToggleableFeature.SPACE_CATS_FEATURE)
    public ResponseEntity<List<SpaceCatDTO>> getAllSpaceCats(){
        List<SpaceCatDTO> spaceCatsDTO = spaceCatService.getAll();
        return ResponseEntity.ok(spaceCatsDTO);
    }

    @GetMapping("/{id}")
    @FeatureToggle(ToggleableFeature.SPACE_CATS_FEATURE)
    public ResponseEntity<SpaceCatDTO> getSpaceCat(@PathVariable UUID id){
        SpaceCatDTO spaceCatDTO = spaceCatService.getById(id);
        return ResponseEntity.ok(spaceCatDTO);
    }

    @PostMapping
    @FeatureToggle(ToggleableFeature.SPACE_CATS_FEATURE)
    public ResponseEntity<SpaceCatDTO> createSpaceCat(@RequestBody @Valid SpaceCatDTO spaceCatDTO){
        SpaceCatDTO createdSpaceCat = spaceCatService.createSpaceCat(spaceCatDTO);
        return new ResponseEntity<>(createdSpaceCat, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @FeatureToggle(ToggleableFeature.SPACE_CATS_FEATURE)
    public ResponseEntity<SpaceCatDTO> updateSpaceCat(@RequestBody @Valid SpaceCatDTO spaceCatDTO, @PathVariable UUID id) {
        SpaceCatDTO updateSpaceCatDTO = spaceCatService.updateSpaceCat(id, spaceCatDTO);
        return ResponseEntity.ok(updateSpaceCatDTO);
    }

    @DeleteMapping("/{id}")
    @FeatureToggle(ToggleableFeature.SPACE_CATS_FEATURE)
    public ResponseEntity<String> deleteProduct(@PathVariable UUID id){
        return ResponseEntity.ok(spaceCatService.deleteById(id));
    }

    @GetMapping("/email/{email}")
    @FeatureToggle(ToggleableFeature.SPACE_CATS_FEATURE)
    public ResponseEntity<SpaceCatDTO> getSpaceCatByEmail(@PathVariable String email){
        return ResponseEntity.ok(spaceCatService.getByEmail(email));
    }
}
