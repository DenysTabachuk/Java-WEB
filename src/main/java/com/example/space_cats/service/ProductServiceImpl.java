package com.example.space_cats.service;

import com.example.space_cats.domain.Product;
import com.example.space_cats.repository.ProductRepository;
import com.example.space_cats.service.exceptions.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import java.util.Optional;


@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @Override
    public List<Product> getAll() {
        return productRepository.getAll();
    }

    public Product getById(long id) {
        Optional <Product> product = productRepository.getById(id);
        return product.orElseThrow(() -> new ProductNotFoundException(id));
    }


    @Override
    public Product createProduct(Product product) {
        return productRepository.addProduct(product);
    }

    @Override
    public Product updateProduct(long id, Product product) {
        Optional<Product> updatedProduct = productRepository.updateProduct(id, product);
        return updatedProduct.orElseThrow(() -> new ProductNotFoundException(id));
    }


    @Override
    public void deleteById(long id) {
        Optional <Product> product = productRepository.getById(id);
        product.orElseThrow( ()-> new ProductNotFoundException(id) );
        productRepository.delete(id);
    }
}
