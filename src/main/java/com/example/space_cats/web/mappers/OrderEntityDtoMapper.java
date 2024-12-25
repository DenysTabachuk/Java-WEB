package com.example.space_cats.web.mappers;

import com.example.space_cats.dto.OrderDTO;
import com.example.space_cats.dto.OrderItemDTO;
import com.example.space_cats.entity.OrderEntity;
import com.example.space_cats.entity.OrderItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface OrderEntityDtoMapper {

    @Mapping(target = "spaceCat", ignore = true) // Ігноруємо, бо це поле буде встановлено вручну
    OrderEntity toEntity(OrderDTO dto);

    OrderDTO toDTO(OrderEntity entity);

    List<OrderDTO> toDTO(List<OrderEntity> entities);
    List<OrderEntity> toEntity(List<OrderDTO> dto);



    OrderItemEntity toItemEntity(OrderItemDTO dto);
    OrderItemDTO toItemDTO(OrderItemEntity entity);

    List<OrderItemDTO> toItemDto(List<OrderItemEntity> entities);
    List<OrderItemEntity> toItemEntity(List<OrderItemDTO> dos);
}
