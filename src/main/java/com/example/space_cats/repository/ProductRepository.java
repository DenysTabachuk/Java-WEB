package com.example.space_cats.repository;

import com.example.space_cats.domain.Product;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface ProductRepository {
    Optional<Product> getById(UUID id);
    List<Product> getAll();
    String delete(UUID id);
    Product addProduct(Product product);
    Optional<Product> updateProduct(UUID id, Product product);
}
