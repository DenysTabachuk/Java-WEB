package com.example.space_cats.web.controllers;

import com.example.space_cats.domain.Product;
import com.example.space_cats.dto.ProductDTO;
import com.example.space_cats.featureToggle.FeatureToggle;
import com.example.space_cats.featureToggle.ToggleableFeature;
import com.example.space_cats.service.product.ProductServiceImpl;
import com.example.space_cats.web.mappers.ProductMapper;
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

    @Autowired
    public ProductController(ProductServiceImpl productService, ProductMapper productMapper){
        this.productService = productService;
    }

    @GetMapping("/{id}")
    @FeatureToggle(ToggleableFeature.KITTY_PRODUCTS_FEATURE)
    public ResponseEntity<ProductDTO> getProduct(@PathVariable UUID id) {
        ProductDTO productDTO = productService.getById(id);
        return ResponseEntity.ok(productDTO);

    }

    @GetMapping
    @FeatureToggle(ToggleableFeature.KITTY_PRODUCTS_FEATURE)
    public ResponseEntity<List<ProductDTO>> getAllProducts(){
        List<ProductDTO> productsDTO = productService.getAll();
        return ResponseEntity.ok(productsDTO);
    }

    @PostMapping
    @FeatureToggle(ToggleableFeature.KITTY_PRODUCTS_FEATURE)
    public ResponseEntity<ProductDTO> addProduct(@RequestBody @Valid  ProductDTO productDTO){
        ProductDTO newProductDTO = productService.createProduct(productDTO);
        return new ResponseEntity<>(newProductDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @FeatureToggle(ToggleableFeature.KITTY_PRODUCTS_FEATURE)
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody @Valid ProductDTO productDTO, @PathVariable UUID id) {
        ProductDTO newProductDTO = productService.updateProduct(id, productDTO);
        return ResponseEntity.ok(newProductDTO);
    }

    @DeleteMapping("{id}")
    @FeatureToggle(ToggleableFeature.KITTY_PRODUCTS_FEATURE)
    public ResponseEntity<String> deleteProduct(@PathVariable UUID id){
        return ResponseEntity.ok(productService.deleteById(id));
    }
}
