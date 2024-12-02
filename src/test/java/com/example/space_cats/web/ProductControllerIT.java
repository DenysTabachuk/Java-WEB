package com.example.space_cats.web;

import com.example.space_cats.domain.Category;
import com.example.space_cats.domain.Product;
import com.example.space_cats.dto.product.ProductDTO;
import com.example.space_cats.service.ProductServiceImpl;
import com.example.space_cats.service.exceptions.ProductNotFoundException;
import com.example.space_cats.web.mapper.ProductMapper;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerIT {

    @MockBean
    private ProductServiceImpl productService; // Мокаємо сервіс

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    ObjectMapper objectMapper;

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


    @Test void shouldReturnProductById() throws Exception{
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

    @Test void shouldReturnAllProducts() throws Exception{
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        Mockito.when(productService.getAll()).thenReturn(productList);

        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].category.name").value(product.getCategory().getName()))
                .andExpect(jsonPath("$[0].name").value(product.getName()))
                .andExpect(jsonPath("$[0].price").value(product.getPrice()))
                .andExpect(jsonPath("$[0].description").value(product.getDescription()));
    }


    @Test
    void shouldCreateProduct() throws Exception{
        ProductDTO productDTO = productMapper.toDto(product);
        Mockito.when(productService.createProduct(productMapper.toEntity(productDTO))).thenReturn(product);

        String jsonProductDTO = objectMapper.writeValueAsString(productDTO);
        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonProductDTO))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(productDTO.getName()))
                .andExpect(jsonPath("$.description").value(productDTO.getDescription()))
                .andExpect(jsonPath("$.price").value(productDTO.getPrice()))
                .andExpect(jsonPath("$.category.name").value(productDTO.getCategory().getName()));
    }

    @Test
    void shouldNotCreateProductAndReturnBadRequest() throws Exception{
        ProductDTO productDTO = productMapper.toDto(notValidProduct);
        Mockito.when(productService.createProduct(productMapper.toEntity(productDTO))).thenReturn(product);

        String jsonProductDTO = objectMapper.writeValueAsString(productDTO);
        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonProductDTO))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Bad request. Object field validation Error"))
                .andExpect(jsonPath("$.message").value("Field: name. Error: Invalid cosmic word. "));
    }

    @Test
    void shouldUpdateProduct() throws Exception{
        String updatedName = "Updated space product name";
        Product updatedProduct = new Product(product);
        updatedProduct.setName(updatedName);

        ProductDTO productDTO = productMapper.toDto(updatedProduct);
        Mockito.when(productService.updateProduct(product.getId(),productMapper.toEntity(productDTO))).thenReturn(updatedProduct);

        String jsonProductDTO = objectMapper.writeValueAsString(productDTO);
        mockMvc.perform(put("/api/v1/products/{id}", product.getId() )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonProductDTO))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(updatedProduct.getName()))
                .andExpect(jsonPath("$.description").value(updatedProduct.getDescription()))
                .andExpect(jsonPath("$.price").value(updatedProduct.getPrice()))
                .andExpect(jsonPath("$.category.name").value(updatedProduct.getCategory().getName()));
    }

    @Test
    void shouldNotUpdateProductAndReturnBadRequest() throws Exception{
        String updatedName = "Invalid product name";
        Product updatedProduct = new Product(product);
        updatedProduct.setName(updatedName);

        ProductDTO productDTO = productMapper.toDto(updatedProduct);
        Mockito.when(productService.updateProduct(product.getId(),productMapper.toEntity(productDTO))).thenReturn(updatedProduct);

        String jsonProductDTO = objectMapper.writeValueAsString(productDTO);
        mockMvc.perform(put("/api/v1/products/{id}", product.getId() )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonProductDTO))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Bad request. Object field validation Error"))
                .andExpect(jsonPath("$.message").value("Field: name. Error: Invalid cosmic word. "));
    }

    @Test
    void shouldDeleteProduct() throws Exception{
        Mockito.when(productService.deleteById(product.getId())).thenReturn("Product( ID - " + product.getId() + " ) successfully deleted");

        mockMvc
                .perform(delete("/api/v1/products/{id}", product.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string("Product( ID - " + product.getId() + " ) successfully deleted"));
    }

    @Test
    void shouldNotDeleteProductAndThrowProductNotFoundException() throws Exception{
        UUID randomId = UUID.randomUUID();
        Mockito.when(productService.deleteById(randomId)).thenThrow(new ProductNotFoundException(randomId));

        mockMvc.perform(delete("/api/v1/products/{id}", randomId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Product with id - " + randomId + " not found"));
    }

}


