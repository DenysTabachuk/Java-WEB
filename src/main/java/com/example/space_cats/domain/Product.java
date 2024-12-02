package com.example.space_cats.domain;

import lombok.Builder;
import lombok.Data;
import java.util.UUID;


@Data
@Builder
public class Product {
    private UUID id;
    private Category  category;
    private String name;
    private Double price;
    private String description;
    private int quantity;
    private double weight;
}
