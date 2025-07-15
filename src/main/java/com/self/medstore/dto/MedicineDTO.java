package com.self.medstore.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicineDTO {

	private Long id;

	@NotBlank(message = "Medicine name is required")
	private String name;

	@NotBlank(message = "Brand is required")
	private String manufacturer;

	@Positive(message = "Price must be positive")
	private double price;

	@Min(value = 1, message = "Stock cannot be negative or zero")
	private int quantityInStock;
	
	@NotBlank(message = "description is required")
	private String description;
	
	@NotBlank(message="expiry date is required")
	private String expiryDate;
}
