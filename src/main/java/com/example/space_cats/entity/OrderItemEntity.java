package com.example.space_cats.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;
import static jakarta.persistence.CascadeType.PERSIST;


@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
@Entity
@Table(name = "order_item")
public class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @ManyToOne(cascade = PERSIST)
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    ProductEntity product;

    Integer quantity;

    Double price;

    @ManyToOne(cascade = PERSIST)
    @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
    OrderEntity order;
}
