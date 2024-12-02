package com.example.space_cats.domain;

import lombok.Data;

import java.util.List;

@Data
public class Order {
    private Long id;
    private List<Product> products;
    private Double price;
    private String additionalInfo;
}
