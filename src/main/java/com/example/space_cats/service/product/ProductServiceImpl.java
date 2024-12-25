package com.example.space_cats.service.product;


import com.example.space_cats.dto.CategoryDTO;
import com.example.space_cats.dto.ProductDTO;
import com.example.space_cats.entity.CategoryEntity;
import com.example.space_cats.entity.ProductEntity;
import com.example.space_cats.repository.CategoryRepository;
import com.example.space_cats.repository.ProductRepository;
import com.example.space_cats.service.exceptions.ProductNotFoundException;
import com.example.space_cats.web.mappers.ProductEntityDtoMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductEntityDtoMapper productEntityDtoMapper;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              ProductEntityDtoMapper productEntityDtoMapper, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.productEntityDtoMapper = productEntityDtoMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public List<ProductDTO> getAll() {
        List<ProductEntity> products= productRepository.findAll();
        return  productEntityDtoMapper.toDto(products);
    }

    @Override
    @Transactional
    public ProductDTO getById(UUID id) {
        ProductEntity product =  productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        ProductDTO productDTO =  productEntityDtoMapper.toDto(product);
        // mapping doesnt work for category :(
        return productDTO;
    }

    @Override
    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO) {
        ProductEntity productEntity = productEntityDtoMapper.toEntity(productDTO);
        CategoryEntity categoryEntity = findOrCreateCategory(productDTO.getCategory());
        productEntity.setCategory(categoryEntity);
        productEntity.setId(UUID.randomUUID());
        ProductEntity createdProduct = productRepository.save(productEntity);
        return productEntityDtoMapper.toDto(createdProduct);
    }

    @Override
    @Transactional
    public ProductDTO updateProduct(UUID id, ProductDTO productDTO) {
        Optional<ProductEntity> productEntityOptional = productRepository.findById(id);
        if (productEntityOptional.isEmpty()){
            throw new ProductNotFoundException(id);
        }
        else {
            ProductEntity existingProduct = productEntityOptional.get();
            existingProduct.setName(productDTO.getName());
            existingProduct.setDescription(productDTO.getDescription());
            existingProduct.setPrice(productDTO.getPrice());
            CategoryEntity categoryEntity = findOrCreateCategory(productDTO.getCategory());
            existingProduct.setCategory(categoryEntity);
            ProductEntity savedProductEntity = productRepository.save(existingProduct);
            return  productEntityDtoMapper.toDto(savedProductEntity);
        }
    }

    @Override
    @Transactional
    public String deleteById(UUID id) {
        Optional <ProductEntity> product = productRepository.findById(id);
        product.orElseThrow( ()-> new ProductNotFoundException(id) );
        productRepository.deleteById(id);
        return( String.format("Product with ID - %s deleted successfully.", id)) ;
    }

    private CategoryEntity findOrCreateCategory(CategoryDTO categoryDTO) {
        Optional<CategoryEntity> categoryEntityOptional = categoryRepository.findByName(categoryDTO.getName());

        if (categoryEntityOptional.isPresent()) {
            return categoryEntityOptional.get();
        } else {
            CategoryEntity newCategoryEntity = productEntityDtoMapper.toEntity(categoryDTO);
            return categoryRepository.save(newCategoryEntity);
        }
    }
}
