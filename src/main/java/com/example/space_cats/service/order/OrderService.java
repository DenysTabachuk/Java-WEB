package com.example.space_cats.service.order;

import com.example.space_cats.dto.OrderDTO;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    List<OrderDTO> getAll();
    OrderDTO getById(UUID id);
    OrderDTO createOrder(OrderDTO orderDTO);
}
