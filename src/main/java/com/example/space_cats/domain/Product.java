package com.example.space_cats.domain;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class Product {
    private long id;
    private long  categoryId;
    private String name;
    private Double price;
    private String description;
    private int quantity;
    private double weight;
}
