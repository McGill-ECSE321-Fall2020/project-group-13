package ca.mcgill.ecse321.projectgroup13.dao;

import ca.mcgill.ecse321.projectgroup13.model.Order;
import ca.mcgill.ecse321.projectgroup13.model.Payment;
import ca.mcgill.ecse321.projectgroup13.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest

/**
 *  The TestPayment class implements JUnit for reading and writing payments to the database
 *  
 */


class TestPayment {
	@Autowired
	private PaymentRepository paymentRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private UserRepository userRepository;
	
	
	
	 /**
     *  Deletes all information from paymentRepository, orderRepository and
     *  userRepository
     */
	
	@BeforeEach
	@AfterEach
	public void clearDatabase() {
		// First, we clear registrations to avoid exceptions due to inconsistencies
		paymentRepository.deleteAll();
		orderRepository.deleteAll();
		userRepository.deleteAll();
	}
	
	/**
      * Creates instances of order, user and payment
      * populates them with test information, saves them to the database
      * and then tests whether payment was successfully saved
      */
	
	@Test
	public void persistAndLoadPayment() {
		Integer orderID = "456".hashCode();
		double totalAmount = 11.5;
		Integer paymentID = "123".hashCode();
		User user = new User();
		user.setUsername("David");
		userRepository.save(user);

		Order order = new Order();
		order.setOrderID(orderID);
		Set<Order> orders= new HashSet<>();
		orders.add(order);

		user.setOrder(orders);
		order.setUser(user);
		orderRepository.save(order);

		Payment payment = new Payment();
		payment.setPaymentID(paymentID);
		payment.setOrder(order);
		paymentRepository.save(payment);

		Payment paymentTest = payment;
		payment = null;

		payment = paymentRepository.findPaymentByOrder(order);
		assertNotNull(payment);
		assertEquals(true, payment.equals(paymentTest));
	}

}