package com.example.space_cats.web.controllers;

import com.example.space_cats.domain.SpaceCat;
import com.example.space_cats.dto.SpaceCatDTO;
import com.example.space_cats.featureToggle.FeatureToggle;
import com.example.space_cats.featureToggle.ToggleableFeature;
import com.example.space_cats.service.spaceCat.SpaceCatService;
import com.example.space_cats.web.mappers.SpaceCatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/space-cats")
public class SpaceCatController {
    private final SpaceCatService spaceCatService;
    private final SpaceCatMapper spaceCatMapper ;

    @Autowired
    public SpaceCatController(SpaceCatService spaceCatService, SpaceCatMapper spaceCatMapper){
        this.spaceCatService = spaceCatService;
        this.spaceCatMapper = spaceCatMapper;
    }

    @GetMapping
    @FeatureToggle(ToggleableFeature.SPACE_CATS_FEATURE)
    public ResponseEntity<List<SpaceCatDTO>> getAllSpaceCats(){
        List<SpaceCat> spaceCats = spaceCatService.getAll();
        List<SpaceCatDTO> spaceCatsDTO = spaceCatMapper.toDto(spaceCats);
        return ResponseEntity.ok(spaceCatsDTO);
    }
}
