package com.self.medstore.mapper;

import org.mapstruct.Mapper;

import com.self.medstore.dto.OrderResponseDTO;
import com.self.medstore.entity.Order;

@Mapper(componentModel = "spring", uses = OrderItemMapper.class)
/*“When converting nested objects—e.g. Order.items (a collection of OrderItem)—use the methods in OrderItemMapper to handle each OrderItem conversion.”*/
public interface OrderMapper {
	
	OrderResponseDTO toDTO(Order order);
}
