package com.self.medstore.cotroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.self.medstore.dto.CustomerRequestDTO;
import com.self.medstore.dto.CustomerResponseDTO;
import com.self.medstore.dto.LoginDTO;
import com.self.medstore.dto.OrderRequestDTO;
import com.self.medstore.dto.OrderResponseDTO;
import com.self.medstore.entity.Customer;
import com.self.medstore.entity.Order;
import com.self.medstore.mapper.CustomerMapper;
import com.self.medstore.mapper.OrderMapper;
import com.self.medstore.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/customer")
@CrossOrigin
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CustomerMapper customerMapper;

	@Autowired
	private OrderMapper orderMapper;

	//1. Register
	@PostMapping("/register")
	public ResponseEntity<CustomerResponseDTO> register(@RequestBody @Valid CustomerRequestDTO dto) {
		Customer saved = customerService.register(customerMapper.toEntity(dto));
		return new ResponseEntity<>(customerMapper.toDTO(saved), HttpStatus.CREATED);
	}

	//2. Login
	@PostMapping("/login")
	public ResponseEntity<CustomerResponseDTO> login(@RequestBody @Valid LoginDTO dto) {
		Customer customer = customerService.login(dto);
		return new ResponseEntity<>(customerMapper.toDTO(customer), HttpStatus.OK);

	}

	// Update customer
	@PutMapping("/{id}/update")
	@Operation(summary = "update your details")
	public ResponseEntity<CustomerResponseDTO> updateCustomer(@PathVariable Long id,
			@RequestBody CustomerRequestDTO dto) {
		Customer updatedCustomer = customerService.updateCustomer(id, customerMapper.toEntity(dto));
		return new ResponseEntity<>(customerMapper.toDTO(updatedCustomer), HttpStatus.OK);
	}

	//3. Place an order
	@PostMapping("/{customerId}/order")
	@Operation(summary = "place your order")
	public ResponseEntity<Order> placeOrder(@PathVariable Long customerId, @RequestBody @Valid OrderRequestDTO dto) {
		//		return customerService.placeOrder(customerId, dto.getMedicineIdAndQuantity());
		return new ResponseEntity<>(customerService.placeOrder(customerId, dto), HttpStatus.OK);
	}

	//4. View order history
	@GetMapping("/{customerId}/orders")
	@Operation(summary = "get your orders")
	public List<OrderResponseDTO> getOrderHistory(@PathVariable Long customerId) {
		return customerService.getCustomerOrders(customerId).stream().map(orderMapper::toDTO).toList();
	}

	//5. cancel an order item
	@DeleteMapping("/{customerId}/cancel/{orderId}/item/{orderItemId}")
	@Operation(summary = "cancel your order")
	public ResponseEntity<String> cancelOrderItem(@PathVariable long customerId, @PathVariable long orderId,
			@PathVariable long orderItemId) {

		String result = customerService.deleteOrderItem(customerId, orderId, orderItemId);

		return new ResponseEntity<>(result, HttpStatus.OK); 
	}
}
