package com.example.space_cats.repository;

import com.example.space_cats.domain.Category;
import com.example.space_cats.domain.Product;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ProductRepositoryImpl implements ProductRepository{
    private final List<Product> products = new ArrayList<>(makeMockData());

    @Override
    public Optional<Product> getById(UUID id) {
        return products.stream()
                .filter(product -> product.getId() == id)
                .findFirst();
    }

    @Override
    public List<Product> getAll() {
        return products;
    }

    @Override
    public String delete(UUID id) {
        products.removeIf(product -> product.getId() == id);
        return String.format("Product( ID - %s ) successfully deleted", id);
    }

    @Override
    public Product addProduct(Product product) {
        products.add(product);
        return product;
    }

    @Override
    public Optional<Product> updateProduct(UUID id, Product product) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == id) {
                products.set(i, product);
                return Optional.of(product);
            }
        }
        return Optional.empty();
    }

    private List<Product> makeMockData(){
        Category weapons  = Category.builder()
                .id(1)
                .name("Space Guns")
                .build();

        Category toys  = Category.builder()
                .id(2)
                .name("Space toys")
                .build();

        Category food  = Category.builder()
                .id(3)
                .name("Space food")
                .build();

        Product product1 = Product.builder()
                .id(UUID.randomUUID())
                .category(food)
                .name("Space Cat Food")
                .price(100.)
                .description("Testy")
                .quantity(100)
                .weight(0.5)
                .build();

        Product product2 = Product.builder()
                .id(UUID.randomUUID())
                .category(toys)
                .name("Space cat toy")
                .price(999.99)
                .description("Very soft. Meow")
                .quantity(40)
                .weight(0.75)
                .build();

        Product product3 = Product.builder()
                .id(UUID.randomUUID())
                .category(weapons)
                .name("Space Blaster")
                .price(8999.99)
                .description("Piy Piy. Deadly thing")
                .quantity(88)
                .weight(2.)
                .build();

        List<Product> mockDataList = new ArrayList<>();
        mockDataList.add(product1);
        mockDataList.add(product2);
        mockDataList.add(product3);

        return mockDataList;
    }
}
