package com.example.space_cats.web.mappers;

import com.example.space_cats.domain.SpaceCat;
import com.example.space_cats.dto.SpaceCatDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SpaceCatMapper {
    SpaceCatMapper INSTANCE = Mappers.getMapper(SpaceCatMapper.class);
    SpaceCat toEntity(SpaceCatDTO dto);
    SpaceCatDTO toDto(SpaceCat entity);
    List<SpaceCatDTO> toDto(List<SpaceCat> entities);
}
