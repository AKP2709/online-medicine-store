package com.self.medstore.cotroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.self.medstore.dto.MedicineDTO;
import com.self.medstore.entity.Medicine;
import com.self.medstore.mapper.MedicineMapper;
import com.self.medstore.service.AdminService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@Autowired
	private MedicineMapper medicineMapper;

	// Add new medicine
	@PostMapping("/addMedicine")
	public Medicine addMedicine(@RequestBody Medicine medicine) {
		return adminService.addMedicine(medicine);
	}

	// Get medicine by name
	@GetMapping("/getMedicine/{name}")
	public Medicine getMedicineByName(String name) {
		return adminService.getMedicineByName(name);
	}

	// Get all medicines
	@GetMapping("/getMedicines")
	public List<MedicineDTO> getAllMedicines() {
		return adminService.getAllMedicines().stream().map(medicineMapper::toDTO).toList();
	}

	// Update medicine
	@PutMapping("/updateMedicine/{id}")
	public Medicine updateMedicine(@PathVariable Long id, @RequestBody Medicine medicine) {
		return adminService.updateMedicine(id, medicine);
	}

	// Delete medicine
	@DeleteMapping("/deleteMedicine/{id}")
	public String deleteMedicine(@PathVariable Long id) {
		return adminService.deleteMedicine(id) ? "Medicine of Id: " + id + " deleted successfully"
				: "Medicine not found";
	}
}