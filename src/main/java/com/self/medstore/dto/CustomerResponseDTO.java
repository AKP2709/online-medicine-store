package com.self.medstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponseDTO {
	private Long id;
	private String name;
	private String email;
	private String phone;
	private String address;

}
