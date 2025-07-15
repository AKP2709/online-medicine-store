package com.self.medstore.mapper;

import org.mapstruct.Mapper;

import com.self.medstore.dto.CustomerRequestDTO;
import com.self.medstore.dto.CustomerResponseDTO;
import com.self.medstore.entity.Customer;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

	Customer toEntity(CustomerRequestDTO dto);

	CustomerResponseDTO toDTO(Customer customer);
}
