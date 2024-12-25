package com.example.space_cats.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@Value
public class OrderDTO {
    @NotNull(message = "spaceCatId can`t be null")
    UUID spaceCatId;

    @NotNull(message = "Entries cannot be null")
    List<OrderItemDTO> products;

    @Size( max = 500, message = "Description name must be in range [0 ; 500] characters")
    String description;

    @NotNull(message = "Total price cannot be null")
    @Min(value = 0)
    Double totalPrice;
}
