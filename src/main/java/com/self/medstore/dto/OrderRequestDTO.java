package com.self.medstore.dto;

import java.util.Map;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
   
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {

	@NotEmpty(message = "Order must include at least one medicine")
	private Map<@NotNull Long, @Min(1) Integer> medicineIdAndQuantity;
	//long = medId, integer = quantity
}
