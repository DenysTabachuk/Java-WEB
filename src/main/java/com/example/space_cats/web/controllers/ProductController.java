package com.example.space_cats.web.controllers;

import com.example.space_cats.domain.Product;
import com.example.space_cats.dto.product.ProductDTO;
import com.example.space_cats.service.ProductServiceImpl;
import com.example.space_cats.web.mapper.ProductMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductServiceImpl productService;
    private final ProductMapper productMapper ;

    @Autowired
    public ProductController(ProductServiceImpl productService, ProductMapper productMapper){
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable UUID id) {
        Product product =  productService.getById(id);
        ProductDTO productDTO = productMapper.toDto(product);
        return ResponseEntity.ok(productDTO);

    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts(){
        List<Product> products = productService.getAll();
        List<ProductDTO> productsDTO = productMapper.toDto(products);
        return ResponseEntity.ok(productsDTO);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> addProduct(@RequestBody @Valid  ProductDTO productDTO){
        Product product = productMapper.toEntity(productDTO);
        Product createdProduct = productService.createProduct(product);
        ProductDTO newProductDTO = productMapper.toDto(createdProduct);
        return new ResponseEntity<>(newProductDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody @Valid ProductDTO productDTO, @PathVariable UUID id) {
        Product product = productService.updateProduct(id, productMapper.toEntity(productDTO) );
        ProductDTO newProductDTO = productMapper.toDto(product);
        return ResponseEntity.ok(newProductDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable UUID id){
        return ResponseEntity.ok(productService.deleteById(id));
    }
}
