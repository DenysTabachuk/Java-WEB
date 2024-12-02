package com.example.space_cats.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.UUID;


@Data
@Builder
@AllArgsConstructor
public class Product {
    private UUID id;
    private Category  category;
    private String name;
    private Double price;
    private String description;
    private int quantity;
    private double weight;

    // Copy constructor
    public Product(Product other) {
        this.id = other.id;
        this.name = other.name;
        this.description = other.description;
        this.price = other.price;
        this.category = other.category;
    }
}
