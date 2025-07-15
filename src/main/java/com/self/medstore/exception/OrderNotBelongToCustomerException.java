package com.self.medstore.exception;

public class OrderNotBelongToCustomerException extends RuntimeException {
	public OrderNotBelongToCustomerException(String message) {
		super(message);
	}
}
