package com.self.medstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.self.medstore.entity.Medicine;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {
	public Medicine findByName(String name);
}
