package com.self.medstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
	private String medicineName;
	private int quantity;
	private double pricePerUnit;
	private double subTotal;
}
  