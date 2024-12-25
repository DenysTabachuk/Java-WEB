package com.example.space_cats.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
@Data
public class OrderItemDTO {
    @NotNull(message = "Product id cannot be null")
    UUID productId;

    @NotNull(message = "Quantity cannot be null")
    int quantity;
}
