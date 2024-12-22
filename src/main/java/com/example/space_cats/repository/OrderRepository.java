package com.example.space_cats.repository;

import com.example.space_cats.entity.OrderEntity;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface OrderRepository extends ListCrudRepository<OrderEntity, UUID> {
}
