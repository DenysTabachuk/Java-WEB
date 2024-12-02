package com.example.space_cats.web;

import com.example.space_cats.domain.Category;
import com.example.space_cats.domain.Product;
import com.example.space_cats.service.ProductServiceImpl;
import com.example.space_cats.service.exceptions.ProductNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;


import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import java.util.UUID;


@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerIt {

    @MockBean
    private ProductServiceImpl productService; // Мокаємо сервіс

    @Autowired
    private MockMvc mockMvc;

    private Category category;
    private Product product;
    private Product notValidProduct;

    @BeforeEach
    void initVariables(){
        category  = Category.builder()
                .id(1)
                .name("new category")
                .build();

        product = Product.builder()
                .id(UUID.randomUUID())
                .category(category)
                .name("Test space product")
                .description("extra cosmic")
                .price(123.0)
                .quantity(144)
                .weight(1)
                .build();

        notValidProduct = Product.builder()
                .id(UUID.randomUUID())
                .category(category)
                .name("Not valid product")
                .description("default product")
                .price(123.0)
                .quantity(144)
                .weight(1)
                .build();

    }



    @Test void shouldReturnProduct() throws Exception{
        Mockito.when(productService.getById(product.getId())).thenReturn(product);

        mockMvc.perform(get("/api/v1/products/{id}", product.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(product.getName()))
                .andExpect(jsonPath("$.description").value(product.getDescription()))
                .andExpect(jsonPath("$.price").value(product.getPrice()))
                .andExpect(jsonPath("$.category.name").value(product.getCategory().getName()));
    }


    @Test void shouldThrowProductNotFoundException() throws Exception{
        UUID randomId = UUID.randomUUID();

        Mockito.when(productService.getById(randomId))
                .thenThrow(new ProductNotFoundException(randomId));


        mockMvc.perform(get("/api/v1/products/{id}", randomId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Product with id - " + randomId + " not found"));
    }




    @Test
    void Test() throws Exception{
        String jsonEntity = "{\"name\":\"Test Entity\"}";

        // Perform the POST request
        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonEntity))
                .andExpect(status().isCreated())
                .andDo(print());

    }


}
