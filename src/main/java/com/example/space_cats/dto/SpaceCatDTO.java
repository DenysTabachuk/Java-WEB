package com.example.space_cats.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;


@Value
@Builder
public class SpaceCatDTO {
    @NotNull(message = "name can`t be null")
    @Size(min = 3, max = 100, message = "Product name must be  in range [3 ; 100] characters")
    String name;

    @Pattern(regexp = "^\\+?[0-9\\-\\s]{9,12}$", message = "Phone number must be valid and contain between 9 and 12 digits")
    String phoneNumber;

    @Size(min=10, max = 200, message = "Address must be  in range [10 ; 200]")
    String address;

    @NotNull(message = "Email can`t be null")
    @Email(message = "Email must be a valid email address")
    @Size(max = 100, message = "Email cannot exceed 100 characters")
    String email;
}
