package com.example.space_cats.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Data
@Table(name = "space_order")
public class OrderEntity {
    @Id
    UUID id;

    @ManyToOne
    @JoinColumn(name = "space_cat_id")
    private SpaceCatEntity spaceCat;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItemEntity> products;

    String description;

    @Column(name = "total_price")
    Double totalPrice;
}
