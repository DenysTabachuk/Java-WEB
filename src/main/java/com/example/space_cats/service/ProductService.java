package com.example.space_cats.service;

import com.example.space_cats.domain.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAll();
    Product getById(long id);
    Product createProduct(Product product);
    Product updateProduct(long id, Product product);
    void deleteById(long id);
}
