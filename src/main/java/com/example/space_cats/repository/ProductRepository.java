package com.example.space_cats.repository;

import com.example.space_cats.domain.Product;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Optional<Product> getById(long id);
    List<Product> getAll();
    void delete(long id);
    Product addProduct(Product product);

    Optional<Product> updateProduct(long id, Product product);

}
