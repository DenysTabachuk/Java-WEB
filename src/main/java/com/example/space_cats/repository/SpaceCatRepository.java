package com.example.space_cats.repository;

import com.example.space_cats.entity.SpaceCatEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpaceCatRepository extends ListCrudRepository<SpaceCatEntity, UUID> {
    @Query("select cat from SpaceCatEntity as cat where cat.email=:email")
    Optional<SpaceCatEntity> findByEmail(String email);
}
