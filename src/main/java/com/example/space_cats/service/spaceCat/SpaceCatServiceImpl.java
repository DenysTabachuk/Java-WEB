package com.example.space_cats.service.spaceCat;

import com.example.space_cats.domain.SpaceCat;
import com.example.space_cats.dto.SpaceCatDTO;
import com.example.space_cats.repository.product.ProductRepository;
import com.example.space_cats.repository.spaceCat.spaceCatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpaceCatServiceImpl implements SpaceCatService {
    private final spaceCatRepository spaceCatRepository;

    @Autowired
    public SpaceCatServiceImpl(spaceCatRepository spaceCatRepository){
        this.spaceCatRepository = spaceCatRepository;
    }

    @Override
    public List<SpaceCat> getAll() {
        return spaceCatRepository.getAll();
    }
}
