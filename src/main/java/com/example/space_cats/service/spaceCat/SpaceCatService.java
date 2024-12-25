package com.example.space_cats.service.spaceCat;

import com.example.space_cats.domain.SpaceCat;
import com.example.space_cats.dto.OrderDTO;
import com.example.space_cats.dto.SpaceCatDTO;
import com.example.space_cats.entity.SpaceCatEntity;

import java.util.List;
import java.util.UUID;


public interface SpaceCatService {
    List<SpaceCatDTO> getAll();
    SpaceCatDTO  getById(UUID id);
    SpaceCatDTO getByEmail(String email);
    SpaceCatDTO createSpaceCat(SpaceCatDTO spaceCatDTO);
    SpaceCatDTO updateSpaceCat(UUID id,SpaceCatDTO spaceCatDTO);
    String deleteById(UUID id);
}
