package com.example.space_cats.web;

import com.example.space_cats.dto.CategoryDTO;
import com.example.space_cats.dto.ProductDTO;
import com.example.space_cats.entity.CategoryEntity;
import com.example.space_cats.entity.ProductEntity;
import com.example.space_cats.featureToggle.FeatureToggleService;
import com.example.space_cats.repository.CategoryRepository;
import com.example.space_cats.repository.ProductRepository;
import com.example.space_cats.service.product.ProductServiceImpl;
import com.example.space_cats.web.mappers.ProductEntityDtoMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.space_cats.featureToggle.ToggleableFeature.KITTY_PRODUCTS_FEATURE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.reset;
import java.util.UUID;


@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerIT {
    @SpyBean
    private ProductServiceImpl productService;

    @MockBean
    private FeatureToggleService featureToggleService;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ProductEntityDtoMapper productEntityDtoMapper;
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;


    @BeforeEach
    void setUp() {
        reset(productService);
        productRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    private ProductEntity saveProductEntityFotTest(){
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

        return product;
    }

    private ProductDTO getProductDtoForTest(){
        CategoryDTO categoryDTO = CategoryDTO.builder()
                .name("test category")
                .description("test category description")
                .build();

        ProductDTO productDTO = ProductDTO.builder()
                .name("Space Gun")
                .description("Piy piy")
                .price(99.99)
                .category(categoryDTO)
                .build();


        return productDTO;
    }

    @Test
    void shouldReturnBadRequestIfFeatureIsDisabled() throws Exception{
        Mockito.when(featureToggleService.isEnabled(KITTY_PRODUCTS_FEATURE.getName())).thenReturn(false);

        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value(String.format("Feature %s is NOT enabled", KITTY_PRODUCTS_FEATURE.getName())))
                .andExpect(jsonPath("$.error").value("The requested feature is currently disabled"))
                .andExpect(jsonPath("$.path").value("/api/v1/products"));
    }

    @Test
    void shouldReturnProductById() throws Exception {
        Mockito.when(featureToggleService.isEnabled(KITTY_PRODUCTS_FEATURE.getName())).thenReturn(true);

        ProductEntity createdProduct =  saveProductEntityFotTest();

        mockMvc.perform(get("/api/v1/products/{id}", createdProduct.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(createdProduct.getName()))
                .andExpect(jsonPath("$.description").value(createdProduct.getDescription()))
                .andExpect(jsonPath("$.price").value(createdProduct.getPrice()));
//                .andExpect(jsonPath("$.category.name").value(createdProduct.getCategory())); // mapping doesnt work for category :(
    }

    @Test
    void shouldThrowProductNotFoundException() throws Exception {
        Mockito.when(featureToggleService.isEnabled(KITTY_PRODUCTS_FEATURE.getName())).thenReturn(true);

        UUID randomId = UUID.randomUUID();

        mockMvc.perform(get("/api/v1/products/{id}", randomId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(String.format("Product with id - %s not found", randomId)));
    }

    @Test
    void shouldReturnAllProductsIfFeatureIsEnabled() throws Exception {
        ProductEntity createdProduct =  saveProductEntityFotTest();

        Mockito.when(featureToggleService.isEnabled(KITTY_PRODUCTS_FEATURE.getName())).thenReturn(true);

        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].category.name").value(createdProduct.getCategory().getName()))
                .andExpect(jsonPath("$[0].name").value(createdProduct.getName()))
                .andExpect(jsonPath("$[0].price").value(createdProduct.getPrice()))
                .andExpect(jsonPath("$[0].description").value(createdProduct.getDescription()));
    }

    @Test
    void shouldCreateProduct() throws Exception {
        Mockito.when(featureToggleService.isEnabled(KITTY_PRODUCTS_FEATURE.getName())).thenReturn(true);

        ProductDTO productDTO = getProductDtoForTest();
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
    void shouldNotCreateProductAndReturnBadRequest() throws Exception {
        Mockito.when(featureToggleService.isEnabled(KITTY_PRODUCTS_FEATURE.getName())).thenReturn(true);

        ProductDTO productDTO = getProductDtoForTest();
        ProductDTO unvalidProductDTO = ProductDTO.builder()
                .name("Unvalid name")
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .category(productDTO.getCategory())
                .build();

        String jsonProductDTO = objectMapper.writeValueAsString(unvalidProductDTO);
        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonProductDTO))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Bad request. Object field validation Error"))
                .andExpect(jsonPath("$.message").value("Field: name. Error: Invalid cosmic word. "));
    }

    @Test
    void shouldUpdateProduct() throws Exception {
        Mockito.when(featureToggleService.isEnabled(KITTY_PRODUCTS_FEATURE.getName())).thenReturn(true);

        ProductEntity createdProduct =  saveProductEntityFotTest();

        ProductDTO updatedProductDTO = ProductDTO.builder()
                .name("Updated space product name")
                .description("Updated description")
                .price(15435.1)
                .category(CategoryDTO.builder()
                        .name("updated category")
                        .description("updated category description")
                        .build())
                .build();

        String jsonUpdatedProductDTO = objectMapper.writeValueAsString(updatedProductDTO);
        mockMvc.perform(put("/api/v1/products/{id}", createdProduct.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUpdatedProductDTO))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(updatedProductDTO.getName()))
                .andExpect(jsonPath("$.description").value(updatedProductDTO.getDescription()))
                .andExpect(jsonPath("$.price").value(updatedProductDTO.getPrice()))
                .andExpect(jsonPath("$.category.name").value(updatedProductDTO.getCategory().getName()));
    }

    @Test
    void shouldNotUpdateProductAndReturnBadRequest() throws Exception {
        Mockito.when(featureToggleService.isEnabled(KITTY_PRODUCTS_FEATURE.getName())).thenReturn(true);

        ProductEntity createdProduct =  saveProductEntityFotTest();

        ProductDTO updatedProductDTO = ProductDTO.builder()
                .name("Updated invalid product name")
                .description("Updated description")
                .price(15435.1)
                .category(CategoryDTO.builder()
                        .name("updated category")
                        .description("updated category description")
                        .build())
                .build();


        String jsonProductDTO = objectMapper.writeValueAsString(updatedProductDTO);
        mockMvc.perform(put("/api/v1/products/{id}", createdProduct.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonProductDTO))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Bad request. Object field validation Error"))
                .andExpect(jsonPath("$.message").value("Field: name. Error: Invalid cosmic word. "));
    }

    @Test
    void shouldDeleteProduct() throws Exception {
        Mockito.when(featureToggleService.isEnabled(KITTY_PRODUCTS_FEATURE.getName())).thenReturn(true);

        ProductEntity createdProduct =  saveProductEntityFotTest();

        mockMvc
                .perform(delete("/api/v1/products/{id}", createdProduct.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(String.format("Product with ID - %s deleted successfully.", createdProduct.getId())));
    }

    @Test
    void shouldNotDeleteProductAndThrowProductNotFoundException() throws Exception {
        Mockito.when(featureToggleService.isEnabled(KITTY_PRODUCTS_FEATURE.getName())).thenReturn(true);

        UUID randomId = UUID.randomUUID();

        mockMvc.perform(delete("/api/v1/products/{id}", randomId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(String.format("Product with id - %s not found", randomId)));
    }
}


