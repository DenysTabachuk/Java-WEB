package com.example.space_cats.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Value
@Data
@Builder
public class CategoryDTO {
    @NotNull(message = "category can`t be null")
    @Size(min = 3, max = 100, message = "Category name must be in range [3 ; 100] characters")
    private String name;

    @Size( max = 500, message = "Category name must be in range [0 ; 500] characters")
    private String description;
}
