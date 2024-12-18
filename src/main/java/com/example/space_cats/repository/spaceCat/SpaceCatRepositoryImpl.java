package com.example.space_cats.repository.spaceCat;


import com.example.space_cats.domain.Order;
import com.example.space_cats.domain.Product;
import com.example.space_cats.domain.SpaceCat;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.time.LocalDate;

@Repository
public class SpaceCatRepositoryImpl implements spaceCatRepository{
    private final List<SpaceCat> spaceCats = new ArrayList<>(makeMockData());

    @Override
    public List<SpaceCat> getAll() {
        return spaceCats;
    }

    private List<SpaceCat> makeMockData(){
        SpaceCat cat1 = SpaceCat.builder()
                .id(UUID.randomUUID())
                .name("Cosmo")
                .phoneNumber("123-456-789")
                .email("cosmo@spacecats.com")
                .address("123 Galaxy Ave")
                .birthDate(LocalDate.of(2020, 5, 15))
                .orders(new ArrayList<>())
                .build();

        SpaceCat cat2 = SpaceCat.builder()
                .id(UUID.randomUUID())
                .name("Nebula")
                .phoneNumber("987-654-321")
                .email("nebula@spacecats.com")
                .address("456 Star Rd")
                .birthDate(LocalDate.of(2021, 8, 22))
                .orders(new ArrayList<>())
                .build();

        SpaceCat cat3 = SpaceCat.builder()
                .id(UUID.randomUUID())
                .name("Luna")
                .phoneNumber("555-123-456")
                .email("luna@spacecats.com")
                .address("789 Moon St")
                .birthDate(LocalDate.of(2019, 3, 30))
                .orders(new ArrayList<>())
                .build();

        List<SpaceCat> mockDataList = new ArrayList<>();
        mockDataList.add(cat1);
        mockDataList.add(cat2);
        mockDataList.add(cat3);

        return mockDataList;
    }
}
