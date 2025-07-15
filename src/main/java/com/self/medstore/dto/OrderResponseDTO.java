package com.self.medstore.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {
	private Long id;
	private LocalDate orderDate;
	private double totalAmount;
	private List<OrderItemDTO> orderItems;
}
