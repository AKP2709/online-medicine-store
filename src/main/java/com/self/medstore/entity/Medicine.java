package com.self.medstore.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
 
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
 
@Entity
@Table(name = "medicines")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Medicine {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private double price;
	private int quantityInStock;
	private String description;
	private String manufacturer;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate expiryDate;
}
