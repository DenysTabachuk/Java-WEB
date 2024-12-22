package com.example.space_cats.service.order;

import com.example.space_cats.dto.OrderDTO;
import com.example.space_cats.dto.OrderItemDTO;
import com.example.space_cats.entity.OrderEntity;
import com.example.space_cats.entity.OrderItemEntity;
import com.example.space_cats.entity.ProductEntity;
import com.example.space_cats.entity.SpaceCatEntity;
import com.example.space_cats.repository.OrderRepository;
import com.example.space_cats.repository.ProductRepository;
import com.example.space_cats.repository.SpaceCatRepository;
import com.example.space_cats.service.exceptions.OrderNotFoundException;
import com.example.space_cats.service.exceptions.ProductNotFoundException;
import com.example.space_cats.service.exceptions.SpaceCatNotFoundException;
import com.example.space_cats.web.mappers.OrderEntityDtoMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements  OrderService{
    private final OrderRepository orderRepository;
    private final SpaceCatRepository spaceCatRepository;
    private final ProductRepository productRepository;
    private final OrderEntityDtoMapper orderEntityDtoMapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderEntityDtoMapper orderEntityDtoMapper,
                            SpaceCatRepository spaceCatRepository, ProductRepository productRepository){
        this.orderRepository = orderRepository;
        this.orderEntityDtoMapper = orderEntityDtoMapper;
        this.spaceCatRepository = spaceCatRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<OrderDTO> getAll() {
        List<OrderEntity> orderDTOList = orderRepository.findAll();
        return  orderEntityDtoMapper.toDTO(orderDTOList);
    }

    @Override
    public OrderDTO getById(UUID id) {
        Optional<OrderEntity> orderEntity = orderRepository.findById(id);
        if (orderEntity.isEmpty()){
            throw new OrderNotFoundException(id);
        }
        return orderEntityDtoMapper.toDTO(orderEntity.get());
    }

    @Transactional
    @Override
    public OrderDTO createOrder(OrderDTO orderDTO){
        // check if spaceCat exists
        Optional<SpaceCatEntity> spaceCatEntityOptional = spaceCatRepository.findById(orderDTO.getSpaceCatId());
        if (spaceCatEntityOptional.isEmpty()){
            throw new SpaceCatNotFoundException(orderDTO.getSpaceCatId());
        }
        // check if products exists
        for (OrderItemDTO product : orderDTO.getProducts()) {
            Optional<ProductEntity> productEntityOptional = productRepository.findById(product.getProductId());
            if (productEntityOptional.isEmpty()) {
                throw new ProductNotFoundException(product.getProductId());
            }
        }

        OrderEntity orderToCreate = orderEntityDtoMapper.toEntity(orderDTO);
        orderToCreate.setSpaceCat(spaceCatEntityOptional.get());
        orderToCreate.setProducts(orderEntityDtoMapper.toItemEntity(orderDTO.getProducts()));
        orderToCreate.setId(UUID.randomUUID());

        //  connect the products with the order
        List<OrderItemEntity> orderItems = orderDTO.getProducts().stream()
                .map(orderItemDTO -> {
                    OrderItemEntity orderItemEntity = orderEntityDtoMapper.toItemEntity(orderItemDTO);
                    orderItemEntity.setOrder(orderToCreate);
                    return orderItemEntity;
                })
                .collect(Collectors.toList());

        orderToCreate.setProducts(orderItems);

        OrderEntity createdOrder =  orderRepository.save(orderToCreate);
        return orderEntityDtoMapper.toDTO(createdOrder);
    }
}
