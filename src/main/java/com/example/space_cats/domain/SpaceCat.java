package com.example.space_cats.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class SpaceCat {
    private UUID id;
    private String name;
    private String phoneNumber;
    private String email;
    private String address;
    private LocalDate birthDate;
    private List<Order> orders;
}
