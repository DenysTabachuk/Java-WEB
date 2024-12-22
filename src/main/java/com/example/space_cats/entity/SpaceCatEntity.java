package com.example.space_cats.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "space_cat")
public class SpaceCatEntity {
    @Id
    UUID id;
    String name;
    String phoneNumber;
    String email;
    String address;
    LocalDate birthDate;
}
