package com.example.space_cats.web;

import com.example.space_cats.entity.*;
import com.example.space_cats.repository.CategoryRepository;
import com.example.space_cats.repository.OrderRepository;
import com.example.space_cats.repository.ProductRepository;
import com.example.space_cats.repository.SpaceCatRepository;
import com.example.space_cats.service.order.OrderService;
import com.example.space_cats.web.mappers.ProductEntityDtoMapper;
import com.example.space_cats.web.mappers.SpaceCatEntityDtoMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerIT {
    @SpyBean
    private OrderService orderService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private SpaceCatRepository spaceCatRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;


    @Autowired
    private SpaceCatEntityDtoMapper spaceCatEntityDtoMapper;
    @Autowired
    private ProductEntityDtoMapper productEntityDtoMapper;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        reset(orderService);
        orderRepository.deleteAll();
        orderRepository.deleteAll();
    }

    private void  createOrderForTest(){
        SpaceCatEntity spaceCatEntity = spaceCatRepository.save( SpaceCatEntity.builder()
                .id(UUID.randomUUID())
                .email("tabakdenuc@gmail.com")
                .phoneNumber("0994592831")
                .address("Star Base 42, Milky Way")
                .build());

        CategoryEntity categoryEntity = categoryRepository.save( CategoryEntity.builder()
                .name("Category 543554")
                .description("I dont know what to say")
                .build());

        ProductEntity product = productRepository.save(ProductEntity.builder()
                .id(UUID.randomUUID())
                .name("space gun")
                .weight(2.)
                .description("piy piy")
                .price(99.)
                .category(categoryEntity)
                .build());

        OrderItemEntity orderItem = OrderItemEntity.builder()
                .product(product)
                .quantity(1)
                .price(product.getPrice())
                .build();

        List<OrderItemEntity> orderItems = new ArrayList<>();
        orderItems.add(orderItem);

        orderRepository.save(OrderEntity.builder()
                .spaceCat(spaceCatEntity)
                .totalPrice(999.)
                .description("Need this really fast")
                .products(orderItems)
                .build());
    }

    @Test
    void  shouldCreateOrder() throws Exception {
    }
}
