package com.example.space_cats.repository;

import com.example.space_cats.entity.OrderItemEntity;
import org.springframework.data.repository.ListCrudRepository;


public interface OrderProductRepository extends ListCrudRepository<OrderItemEntity, Long> {
}
