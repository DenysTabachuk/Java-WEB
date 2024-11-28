package com.example.space_cats.web.mapper;

import com.example.space_cats.domain.Product;
import com.example.space_cats.dto.product.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
    Product toEntity(ProductDTO dto);
    ProductDTO toDto(Product entity);
    List<ProductDTO> toDto(List<Product> entities);
}