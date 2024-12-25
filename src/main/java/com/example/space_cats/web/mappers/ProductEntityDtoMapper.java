package com.example.space_cats.web.mappers;

import com.example.space_cats.dto.CategoryDTO;
import com.example.space_cats.dto.ProductDTO;
import com.example.space_cats.entity.CategoryEntity;
import com.example.space_cats.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductEntityDtoMapper {
    ProductEntityDtoMapper INSTANCE = Mappers.getMapper(ProductEntityDtoMapper.class);
    ProductEntity toEntity(ProductDTO dto);
    ProductDTO toDto(ProductEntity entity);
    List<ProductDTO> toDto(List<ProductEntity> entities);

    CategoryDTO toDto(CategoryEntity categoryEntity);
    CategoryEntity toEntity(CategoryDTO categoryDTO);
}



