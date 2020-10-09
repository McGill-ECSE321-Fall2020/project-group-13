package ca.mcgill.ecse321.projectgroup13.doa;

import ca.mcgill.ecse321.projectgroup13.dao.OrderRepository;
import ca.mcgill.ecse321.projectgroup13.dao.PaymentRepository;
import ca.mcgill.ecse321.projectgroup13.dao.UserRepository;
import ca.mcgill.ecse321.projectgroup13.model.Order;
import ca.mcgill.ecse321.projectgroup13.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class TestPayment {
	@Autowired
	private PaymentRepository paymentRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private UserRepository userRepository;
	
	@BeforeEach
	public void clearDatabase() {
		// First, we clear registrations to avoid exceptions due to inconsistencies
		paymentRepository.deleteAll();
		//orderRepository.deleteAll();
		
	}
	@Test
	public void testOrderPayment() {
		String orderID = "456";
		double totalAmount = 11.5;
		Date date = Date.valueOf("2020-02-02");
		String paymentID = "123";
		// First example for object save/load
		User user = new User();
		user.setUsername("David");
		userRepository.save(user);
		Order order = new Order();
		order.setOrderID(orderID);

		Set<Order> orders= new HashSet<>();
		//creating a hashset
		orders.add(order);

		user.setOrder(orders);
		order.setUser(user);
		orderRepository.save(order);

		order = null;

		order = orderRepository.findOrderByOrderID(orderID);
//		Payment payment = new Payment();
//		order.setPayment(payment);
//		payment.setOrder(order);
//		// First example for attribute save/load
//		payment.setPaymentID(paymentID);
//		payment.setTotalAmount(totalAmount);
//		payment.setPaymentDate(date);
//		payment.setOrder(order);
//		order.setPayment(payment);
//		paymentRepository.save(payment);
//
//		payment = null;
//
//
//		payment = paymentRepository.findPaymentByPaymentID("123");
//		assertNotNull(payment);
//		assertEquals(orderID, payment.getOrder().getOrderID());
//		assertEquals(totalAmount,payment.getTotalAmount());
//		assertEquals(date, payment.getPaymentDate());
		
	}

}
