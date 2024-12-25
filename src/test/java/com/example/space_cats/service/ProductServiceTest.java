package com.example.space_cats.service;


import com.example.space_cats.dto.ProductDTO;
import com.example.space_cats.entity.CategoryEntity;
import com.example.space_cats.entity.ProductEntity;
import com.example.space_cats.repository.ProductRepository;
import com.example.space_cats.service.exceptions.ProductNotFoundException;
import com.example.space_cats.service.product.ProductService;
import com.example.space_cats.web.mappers.ProductEntityDtoMapper;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.boot.test.mock.mockito.MockBean;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductServiceTest {
    @MockBean
    private ProductRepository productRepository;
    @Autowired
    ProductEntityDtoMapper productEntityDtoMapper;
    @Autowired
    private ProductService productService;

    private CategoryEntity categoryEntityTest;
    private ProductEntity productEntityTest;

    @BeforeEach
    void setUp() {
        categoryEntityTest =  CategoryEntity.builder()
                .name("Test Category")
                .description("I dont know what to say")
                .build();

        productEntityTest = ProductEntity.builder()
                .id(UUID.randomUUID())
                .name("space gun")
                .description("piy piy")
                .price(99.)
                .category(categoryEntityTest)
                .build();
    }

    @Test
    void shouldGetAllProductsSuccessfully(){
        Mockito.when(productRepository.findAll()).thenReturn(List.of(productEntityTest));

        List<ProductDTO> productDTOList = productService.getAll();
        ProductDTO productDTO = productDTOList.get(0);

        assertEquals(productDTOList.size(), 1);

        assertEquals(productDTO.getName(), "space gun");
        assertEquals(productDTO.getPrice(), 99.);
        assertEquals(productDTO.getDescription(), "piy piy");
    }


    @Test
    void shouldGetProductByIdSuccessfully(){
        UUID randomId = UUID.randomUUID();
        Mockito.when(productRepository.findById(randomId)).thenReturn(Optional.of(productEntityTest));
        ProductDTO productDTO = productService.getById(randomId);

        assertNotNull(productDTO);
        assertEquals(productDTO.getName(), "space gun");
        assertEquals(productDTO.getPrice(), 99.);
        assertEquals(productDTO.getDescription(), "piy piy");
    }

    @Test
    void shouldThrowExceptionWhenGettingNonExistingProductById() {
        UUID nonExistingProductId = UUID.randomUUID();
        Mockito.when(productRepository.findById(nonExistingProductId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class,
                () -> productService.getById(nonExistingProductId),
                "Expected ProductNotFoundException to be thrown");
    }

    @Test
    void shouldCreateProductSuccessfully(){
        Mockito.when(productRepository.save(Mockito.any(ProductEntity.class))).thenReturn(productEntityTest);

        ProductDTO createdProduct = productService.createProduct(productEntityDtoMapper.toDto(productEntityTest));

        assertNotNull(createdProduct);
        assertEquals(createdProduct.getName(), productEntityTest.getName());
        assertEquals(createdProduct.getPrice(), productEntityTest.getPrice());
        assertEquals(createdProduct.getDescription(), productEntityTest.getDescription());
    }

    @Test
    void shouldThrowExceptionWhenTryingToUpdateNotExistingProduct(){
        UUID notExistingId = UUID.randomUUID();
        Mockito.when(productRepository.findById(notExistingId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class,
                () -> productService.updateProduct(notExistingId, productEntityDtoMapper.toDto(productEntityTest)));
    }

    @Test
    void shouldUpdateProductSuccessfully() {
        CategoryEntity updatedCategoryEntity =  CategoryEntity.builder()
                .name("updated Category")
                .description("I dont know what to say")
                .build();

        ProductEntity updatedProductEntity = ProductEntity.builder()
                .id(productEntityTest.getId())
                .name("changed name")
                .description("new description")
                .category(updatedCategoryEntity)
                .price(200.0)
                .build();

        Mockito.when(productRepository.findById(productEntityTest.getId()))
                .thenReturn(Optional.of(productEntityTest));

        Mockito.when(productRepository.save(Mockito.any(ProductEntity.class)))
                .thenReturn(updatedProductEntity);

        ProductDTO updatedProductDTO = productService.updateProduct(
                productEntityTest.getId(), productEntityDtoMapper.toDto(updatedProductEntity));

        assertNotNull(updatedProductDTO);
        assertEquals(updatedProductEntity.getName(), updatedProductDTO.getName());
        assertEquals(updatedProductEntity.getDescription(), updatedProductDTO.getDescription());
        assertEquals(updatedProductEntity.getPrice(), updatedProductDTO.getPrice());
        assertEquals(updatedProductEntity.getCategory().getName(), updatedProductDTO.getCategory().getName());
    }

    @Test
    void shouldDeleteProductSuccessfully() {
        UUID randomId = UUID.randomUUID();
        Mockito.when(productRepository.findById(randomId)).thenReturn(Optional.of(productEntityTest));
        doNothing().when(productRepository).deleteById(randomId);

        String result = productService.deleteById(randomId);

        assertEquals(String.format("Product with ID - %s deleted successfully.", randomId), result);

    }

    @Test
    void shouldThrowExceptionWhenTryingToDeleteNotExistingProduct(){
        UUID notExistingId = UUID.randomUUID();
        Mockito.when(productRepository.findById(notExistingId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class,
        () -> productService.deleteById(notExistingId));
    }
}
