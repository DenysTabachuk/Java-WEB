package com.example.space_cats.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class Order {
    private Long id;
    private List<Product> products;
    private Double price;
    private String additionalInfo;
}
