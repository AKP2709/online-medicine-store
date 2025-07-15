package com.self.medstore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orderItems")
@Data
@NoArgsConstructor
public class OrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private int quantity;
	private double subTotal;

	@ManyToOne
	@JsonIgnore
	private Medicine medicine;

	@ManyToOne
	@JsonIgnore
	private Order order;

	public OrderItem(int quantity, double subTotal, Medicine medicine, Order order) {
		super();
		this.quantity = quantity;
		this.subTotal = subTotal;
		this.medicine = medicine;
		this.order = order;
	}

}
