package com.example.space_cats.dto.product;


import com.example.space_cats.domain.Category;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Builder;
import lombok.Value;

import com.example.space_cats.validation.CosmicWordCheck;

@Value
@Builder
public class ProductDTO {

    @NotNull(message = "category can`t be null")
    private Category category;

    @CosmicWordCheck
    @NotNull(message = "name can`t be null")
    @Size(min = 3, max = 100, message = "Product name must be  in range [3 ; 100] characters")
    private String name;

    @NotNull(message = "price can`t be null")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0.01")
    private double price;

    @Size(max = 500, message = "Description must be less than or equal to 500 characters")
    private String description;
}
