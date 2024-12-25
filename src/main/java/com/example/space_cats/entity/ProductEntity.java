package com.example.space_cats.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "product")
public class ProductEntity {
    @Id UUID id;
    String name;
    String description;
    Double price;
    Integer quantity;
    Double weight;

    @ManyToOne
    @JoinColumn(name = "category_id")
    CategoryEntity category;
}
