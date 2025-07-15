package com.self.medstore.mapper;

import org.mapstruct.Mapper;

import com.self.medstore.dto.MedicineDTO;
import com.self.medstore.entity.Medicine;

@Mapper(componentModel = "spring")
public interface MedicineMapper {
	MedicineDTO toDTO(Medicine medicine);

	Medicine toEntity(MedicineDTO dto);
}
