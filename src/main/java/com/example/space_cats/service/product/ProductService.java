package com.example.space_cats.service.product;

import com.example.space_cats.domain.Product;
import com.example.space_cats.dto.ProductDTO;
import com.example.space_cats.entity.ProductEntity;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<ProductDTO> getAll();
    ProductDTO getById(UUID id);
    ProductDTO createProduct(ProductDTO productDTO);
    ProductDTO updateProduct(UUID id, ProductDTO productDTO);
    String deleteById(UUID id);
}
