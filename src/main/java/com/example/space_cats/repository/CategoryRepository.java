package com.example.space_cats.repository;

import com.example.space_cats.entity.CategoryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<CategoryEntity, Long> {
    @Query("select category from CategoryEntity as category where category.name=:name")
    Optional<CategoryEntity> findByName(String name);
}
