package com.example.space_cats.web.mappers;

import com.example.space_cats.dto.SpaceCatDTO;
import com.example.space_cats.entity.SpaceCatEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SpaceCatEntityDtoMapper {
    SpaceCatEntityDtoMapper INSTANCE = Mappers.getMapper(SpaceCatEntityDtoMapper.class);
    SpaceCatEntity toEntity(SpaceCatDTO dto);
    SpaceCatDTO toDto(SpaceCatEntity entity);
    List<SpaceCatDTO> toDto(List<SpaceCatEntity> entities);
}
