package com.example.space_cats.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class Order {
    private UUID id;
    private UUID spaceCatId;
    private List<OrderItem> products;
    private Double price;
    private String additionalInfo;
}
