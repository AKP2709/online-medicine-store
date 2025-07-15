package com.self.medstore.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.self.medstore.dto.OrderItemDTO;
import com.self.medstore.entity.OrderItem;
 
@Mapper(componentModel = "spring")
public interface OrderItemMapper {
 
	@Mapping(source = "medicine.name", target = "medicineName")
	@Mapping(source = "medicine.price", target = "pricePerUnit")
	OrderItemDTO toDTO(OrderItem orderItem);
}
