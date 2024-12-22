package com.example.space_cats.service.spaceCat;

import com.example.space_cats.dto.SpaceCatDTO;
import com.example.space_cats.entity.SpaceCatEntity;
import com.example.space_cats.repository.SpaceCatRepository;
import com.example.space_cats.service.exceptions.SpaceCatNotFoundException;
import com.example.space_cats.web.mappers.SpaceCatEntityDtoMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class SpaceCatServiceImpl implements SpaceCatService{
    private final SpaceCatRepository spaceCatRepository;
    private final SpaceCatEntityDtoMapper spaceCatEntityDtoMapper;

    @Autowired
    public SpaceCatServiceImpl(SpaceCatRepository spaceCatRepository, SpaceCatEntityDtoMapper spaceCatEntityDtoMapper){
        this.spaceCatRepository = spaceCatRepository;
        this.spaceCatEntityDtoMapper = spaceCatEntityDtoMapper;
    }

    @Override
    public List<SpaceCatDTO> getAll() {
        List<SpaceCatEntity> spaceCatEntities = spaceCatRepository.findAll();
        return spaceCatEntityDtoMapper.toDto(spaceCatEntities);
    }

    @Override
    public SpaceCatDTO getById(UUID id) {
        SpaceCatEntity spaceCat =  spaceCatRepository.findById(id)
                .orElseThrow(() -> new SpaceCatNotFoundException(id));

        return spaceCatEntityDtoMapper.toDto(spaceCat);
    }

    @Override
    @Transactional
    public SpaceCatDTO createSpaceCat(SpaceCatDTO spaceCatDTO) {
        SpaceCatEntity spaceCatEntity = spaceCatEntityDtoMapper.toEntity(spaceCatDTO);
        spaceCatEntity.setId(UUID.randomUUID());
        SpaceCatEntity createdSpaceCat = spaceCatRepository.save(spaceCatEntity);
        return spaceCatEntityDtoMapper.toDto(createdSpaceCat);
    }

    @Override
    public SpaceCatDTO updateSpaceCat(UUID id, SpaceCatDTO spaceCatDTO) {
        SpaceCatEntity spaceCatToUpdate = spaceCatEntityDtoMapper.toEntity(spaceCatDTO);
        spaceCatToUpdate.setId(id);
        SpaceCatEntity updatedSpaceCat = spaceCatRepository.save(spaceCatToUpdate);
        return spaceCatEntityDtoMapper.toDto(updatedSpaceCat);
    }

    @Override
    public String deleteById(UUID id) {
        Optional<SpaceCatEntity> product = spaceCatRepository.findById(id);
        product.orElseThrow( ()-> new SpaceCatNotFoundException(id) );
        spaceCatRepository.deleteById(id);
        return( String.format("SpaceCat with ID %s deleted successfully.", id)) ;
    }

    @Override
    public SpaceCatDTO getByEmail(String email){
        Optional<SpaceCatEntity> product = spaceCatRepository.findByEmail(email);
        product.orElseThrow( ()-> new SpaceCatNotFoundException(email) );
        return spaceCatEntityDtoMapper.toDto(product.get());
    }
}
