package com.example.space_cats.service.product;

import com.example.space_cats.domain.Product;
import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<Product> getAll();
    Product getById(UUID id);
    Product createProduct(Product product);
    Product updateProduct(UUID id, Product product);
    String deleteById(UUID id);
}
