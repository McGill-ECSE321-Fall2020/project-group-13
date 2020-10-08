package ca.mcgill.ecse321.projectgroup13.doa;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.GregorianCalendar;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.projectgroup13.dao.PaymentRepository;
import ca.mcgill.ecse321.projectgroup13.model.Order;
import ca.mcgill.ecse321.projectgroup13.model.OrderStatus;
import ca.mcgill.ecse321.projectgroup13.model.Payment;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class TestPayment {
	@Autowired
	private PaymentRepository paymentRepository;
	
	@AfterEach
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
		Order order = new Order();
		order.setOrderID(orderID);
		Payment payment = new Payment();
		order.setPayment(payment);
		payment.setOrder(order);
		// First example for attribute save/load
		payment.setPaymentID(paymentID);
		payment.setTotalAmount(totalAmount);
		payment.setPaymentDate(date);
		paymentRepository.save(payment);
		//orderRepository.save(order);
		
		payment = null;

		payment = paymentRepository.findPaymentByPaymentID("123");
		assertNotNull(payment);
		assertEquals(orderID, payment.getOrder().getOrderID());
		assertEquals(totalAmount,payment.getTotalAmount());
		assertEquals(date, payment.getPaymentDate());
		
	}
	@Test
	void test() {
		fail("Not yet implemented");
	}

}
