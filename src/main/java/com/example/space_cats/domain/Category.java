package com.example.space_cats.domain;


import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class Category {
    private long id;
    private String name;
    private String description;
}
