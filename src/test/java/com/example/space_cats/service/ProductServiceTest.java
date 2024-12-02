package com.example.space_cats.service;

import com.example.space_cats.domain.Category;
import com.example.space_cats.domain.Product;
import com.example.space_cats.service.exceptions.ProductNotFoundException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    private static final UUID NOT_EXISTING_PRODUCT_ID = UUID.randomUUID();
    private static List<Product>  products;
    private static Integer expectedProductsSize = 3;

    static Stream<Product> provideProductsFotTest(){
        return  products.stream();
    }

    @Test
    @Order(1)
    void shouldGetAllProductsSuccessfully(){
        this.products = productService.getAll();
        assertNotNull(products);
        assertEquals(expectedProductsSize, products.size());
    }

    @ParameterizedTest
    @MethodSource("provideProductsFotTest")
    @Order(2)
    void shouldGetProductByIdSuccessfully(Product expectedProduct){
        Product product = productService.getById(expectedProduct.getId());
        assertEquals(expectedProduct, product);
    }

    @Test
    @Order(3)
    void shouldThrowExceptionWhenGettingNonExistingProductById(){
        assertThrows(ProductNotFoundException.class,
                () -> productService.getById(NOT_EXISTING_PRODUCT_ID));
    }

    @Test
    @Order(4)
    void shouldCreateProductSuccessfully(){
        int initialCount = productService.getAll().size();

        Category newCategory  = Category.builder()
                .id(1)
                .name("new category")
                .build();


        Product newProduct = Product.builder()
                .id(NOT_EXISTING_PRODUCT_ID)
                .category(newCategory)
                .name("new space product")
                .description("dwarf star as a gift with purchase")
                .price(123.)
                .quantity(144)
                .weight(1)
                .build();

        productService.createProduct(newProduct);
        Product createdProduct = productService.getById(newProduct.getId());
        assertNotNull(createdProduct);

        assertEquals(createdProduct.getName(), newProduct.getName());
        assertEquals(createdProduct.getPrice(), newProduct.getPrice());
        assertEquals(createdProduct.getQuantity(), newProduct.getQuantity());
        assertEquals(createdProduct.getWeight(), newProduct.getWeight());
        assertEquals(createdProduct.getCategory(), newProduct.getCategory());
        assertEquals(createdProduct.getDescription(), newProduct.getDescription());

        int updatedCount = productService.getAll().size();
        assertEquals(initialCount + 1, updatedCount);
    }

    @Test
    @Order(5)
    void shouldUpdateProductSuccessfully(){
        Category newCategory  = Category.builder()
                .id(1)
                .name("new category")
                .build();

        System.out.println(products);
        Product productToUpdate = products.get(0);

        Product product = Product.builder()
                .id(productToUpdate.getId())
                .category(newCategory)
                .name("Updated space product")
                .description("extra cosmic")
                .price(123.)
                .quantity(144)
                .weight(1)
                .build();


        productService.updateProduct(productToUpdate.getId(), product);
        Product updatedProduct = productService.getById(product.getId());

        assertNotNull(updatedProduct);

        assertAll("Updated product properties",
                () -> assertEquals(product.getName(), updatedProduct.getName()),
                () -> assertEquals(product.getPrice(), updatedProduct.getPrice()),
                () -> assertEquals(product.getQuantity(), updatedProduct.getQuantity()),
                () -> assertEquals(product.getWeight(), updatedProduct.getWeight()),
                () -> assertEquals(product.getCategory(), updatedProduct.getCategory()),
                () -> assertEquals(product.getDescription(), updatedProduct.getDescription())
        );
    }

    @Test
    @Order(6)
    void shouldDeleteProductSuccessfully() {
        // NOT_EXISTING_PRODUCT_ID currently exists due to previous tests
        String result = productService.deleteById(NOT_EXISTING_PRODUCT_ID);

        assertEquals("Product( ID - " + NOT_EXISTING_PRODUCT_ID.toString() + " ) successfully deleted", result);

        assertThrows(ProductNotFoundException.class,
                () -> productService.getById(NOT_EXISTING_PRODUCT_ID));
    }







}
