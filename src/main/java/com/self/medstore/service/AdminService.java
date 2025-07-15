package com.self.medstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.self.medstore.entity.Medicine;
import com.self.medstore.exception.ResourceNotFoundException;
import com.self.medstore.repository.MedicineRepository;

@Service
public class AdminService {

	@Autowired
	private MedicineRepository medicineRepository;

	// Add medicine
	public Medicine addMedicine(Medicine medicine) {
		return medicineRepository.save(medicine);
	}

	// Get medicine by name
	public Medicine getMedicineByName(String name) {
		return medicineRepository.findByName(name); 
	}

	// Get all medicines
	public List<Medicine> getAllMedicines() {
		return medicineRepository.findAll();
	}

	// Update medicine
	public Medicine updateMedicine(Long id, Medicine newData) {
		Medicine med = medicineRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Medicine with ID: " + id + " not found"));

		if (med.getName() != null) {
			med.setName(newData.getName());
		}

		if (med.getPrice() != 0.0) {
			med.setPrice(newData.getPrice());
		}

		if (med.getQuantityInStock() != 0) {
			med.setQuantityInStock(newData.getQuantityInStock());
		}

		if (med.getDescription() != null) {
			med.setDescription(newData.getDescription());
		}

		if (med.getManufacturer() != null) {
			med.setManufacturer(newData.getManufacturer());
		}

		if (med.getExpiryDate() != null) {
			med.setExpiryDate(newData.getExpiryDate());
		}

		return medicineRepository.save(med);
	}

	// Delete medicine
	public boolean deleteMedicine(Long id) {
		if (!medicineRepository.existsById(id)) {
			throw new ResourceNotFoundException("Medicine not found with ID: " + id);
		}
		medicineRepository.deleteById(id);
		return true;
	}

}
