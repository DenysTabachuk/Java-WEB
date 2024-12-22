package com.example.space_cats.repository;

import com.example.space_cats.entity.ProductEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends ListCrudRepository<ProductEntity, UUID> {
    @Query("select product from ProductEntity product where product.name=:name")
    Optional<ProductEntity> findByName(String name);
}
