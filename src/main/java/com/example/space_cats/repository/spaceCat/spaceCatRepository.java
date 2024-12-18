package com.example.space_cats.repository.spaceCat;

import com.example.space_cats.domain.SpaceCat;

import java.util.List;

public interface spaceCatRepository {
    List<SpaceCat> getAll();
}
