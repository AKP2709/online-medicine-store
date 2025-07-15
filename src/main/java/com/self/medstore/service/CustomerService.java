package com.self.medstore.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.self.medstore.dto.LoginDTO;
import com.self.medstore.dto.OrderRequestDTO;
import com.self.medstore.entity.Customer;
import com.self.medstore.entity.Medicine;
import com.self.medstore.entity.Order;
import com.self.medstore.entity.OrderItem;
import com.self.medstore.entity.OrderStatus;
import com.self.medstore.exception.DuplicateResourceException;
import com.self.medstore.exception.InvalidCredentialsException;
import com.self.medstore.exception.OrderNotBelongToCustomerException;
import com.self.medstore.exception.OrderShippedException;
import com.self.medstore.exception.OutOfStockException;
import com.self.medstore.exception.ResourceNotFoundException;
import com.self.medstore.repository.CustomerRepository;
import com.self.medstore.repository.MedicineRepository;
import com.self.medstore.repository.OrderRepository;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private MedicineRepository medicineRepository;

	@Autowired
	private OrderRepository orderRepository;

	//1. Register
	public Customer register(Customer customer) {
		if (customerRepository.findByEmail(customer.getEmail()) != null) {
			throw new DuplicateResourceException("Email is already in use");
		}
		return customerRepository.save(customer);
	}

	//2. Login 
	public Customer login(LoginDTO loginDto) {
		Customer customer = customerRepository.findByEmail(loginDto.getEmail())
				.orElseThrow(() -> new InvalidCredentialsException("Invalid email"));

		if (!loginDto.getPassword().equals(customer.getPassword())) {
			throw new InvalidCredentialsException("Invalid password");
		}
		return customer;
	}

	//3. Update customer
	public Customer updateCustomer(Long id, Customer cust) {
		Customer customer = customerRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Customer with ID: " + id + " not found"));

		if (cust.getName() != null) {
			customer.setName(cust.getName());
		}
		if (cust.getEmail() != null) {
			customer.setEmail(cust.getEmail());
		}
		if (cust.getPassword() != null) {
			customer.setPassword(cust.getPassword());
		}
		if (cust.getPhone() != null) {
			customer.setPhone(cust.getPhone());
		}
		if (cust.getAddress() != null) {
			customer.setAddress(cust.getAddress());
		}

		return customerRepository.save(customer);
	}

	//4. Place order
	public Order placeOrder(Long customerId, OrderRequestDTO dto) {
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + customerId));

		//creating list to store order item
		List<OrderItem> orderItems = new ArrayList<>();
		double totalAmount = 0;
		Map<Long, Integer> medicineIdAndQtyMap = dto.getMedicineIdAndQuantity();
		for (Map.Entry<Long, Integer> entry : medicineIdAndQtyMap.entrySet()) {
			double subTotal = 0;
			Long medId = entry.getKey();
			int qty = entry.getValue();

			Medicine medicine = medicineRepository.findById(medId)
					.orElseThrow(() -> new ResourceNotFoundException("Medicine not found with ID: " + medId));

			if (medicine.getQuantityInStock() < qty) {
				throw new OutOfStockException("Medicine '" + medicine.getName() + "' is out of stock. Available: "
						+ medicine.getQuantityInStock());
			}

			medicine.setQuantityInStock(medicine.getQuantityInStock() - qty);
			medicineRepository.save(medicine);
			subTotal += qty * medicine.getPrice();
			totalAmount += subTotal;
			OrderItem item = new OrderItem(qty, subTotal, medicine, null);
			orderItems.add(item);
		}

		// inserting in order
		Order order = new Order();
		order.setOrderDate(LocalDate.now());
		order.setStatus(OrderStatus.PLACED);
		order.setCustomer(customer);
		order.setTotalAmount(totalAmount);
		order.setOrderItems(orderItems);
		orderItems.forEach(i -> i.setOrder(order));

		return orderRepository.save(order);
	}

	//5. Get order history
	public List<Order> getCustomerOrders(Long customerId) {
		Optional<Customer> customer = customerRepository.findById(customerId);
		return customer.map(orderRepository::findByCustomer).orElse(Collections.emptyList());
	}

	//6. delete order 
	public String deleteOrderItem(long customerId, Long orderId, long orderItemId) {

		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + orderId));

		if (!(order.getCustomer().getId() == customerId)) {
			throw new OrderNotBelongToCustomerException("Unauthorized: This order doesn't belong to you!!");
		}

		if (order.getStatus() != OrderStatus.PLACED) {
			throw new OrderShippedException("Only PLACED orders can be deleted!!");
		}

		// Find the OrderItem in the current Order
		OrderItem targetItem = order.getOrderItems().stream().filter(oi -> oi.getId() == orderItemId).findFirst()
				.orElseThrow(() -> new ResourceNotFoundException(
						"No such orderItem of id " + orderItemId + " in your order!!"));
		
		Medicine med = targetItem.getMedicine();
		med.setQuantityInStock(med.getQuantityInStock() + targetItem.getQuantity());
		medicineRepository.save(med);
		
		order.setTotalAmount(order.getTotalAmount() - targetItem.getSubTotal());
		order.getOrderItems().remove(targetItem);
		orderRepository.save(order);

		return "item deleted uccessfully"; 
	}
}
