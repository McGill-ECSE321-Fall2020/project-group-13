package ca.mcgill.ecse321.projectgroup13.services;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import java.util.*;

import ca.mcgill.ecse321.projectgroup13.dao.OrderRepository;
import ca.mcgill.ecse321.projectgroup13.dao.PaymentRepository;
import ca.mcgill.ecse321.projectgroup13.dao.UserRepository;
import ca.mcgill.ecse321.projectgroup13.model.Artwork;
import ca.mcgill.ecse321.projectgroup13.model.Order;
import ca.mcgill.ecse321.projectgroup13.model.OrderStatus;
import ca.mcgill.ecse321.projectgroup13.model.Payment;
import ca.mcgill.ecse321.projectgroup13.model.Shipment;
import ca.mcgill.ecse321.projectgroup13.model.User;
import ca.mcgill.ecse321.projectgroup13.services.exception.RegistrationException;

@ExtendWith(MockitoExtension.class)
public class TestServiceOrder {
	@Mock
	private UserRepository userRepo; 
	@Mock
	private OrderRepository orderRepo;
	@Mock
	private PaymentRepository paymentRepo;
	
	@InjectMocks
	private OrderService orderService;
	@InjectMocks
	private PaymentService paymentService;
	@InjectMocks
	private UserService userService;
	
	private String error = "";
	@BeforeEach
	public void setMockOutput() {
		MockitoAnnotations.initMocks(this);
		
		lenient().when(orderRepo.findOrdersByUser(any(User.class))).thenAnswer((InvocationOnMock invocation) -> {
			Set<Order> orders = new HashSet<Order>();
			Order order = new Order();
			Payment payment1 = new Payment();
			payment1.setPaymentDate(Date.valueOf("2020-01-01"));
			order.setPayment(payment1);
			Order order2 = new Order();
			Payment payment2 = new Payment();
			payment2.setPaymentDate(Date.valueOf("2020-02-01"));
			order2.setPayment(payment2);
			order.setOrderID(111);
			order2.setOrderID(222);
			orders.add(order);
			orders.add(order2);
			return orders;
			
		});
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		
		lenient().when(paymentRepo.save(any(Payment.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(orderRepo.save(any(Order.class))).thenAnswer(returnParameterAsAnswer);
	}
	@Test
	public void testCreateOrderSuccess() {
		//assertEquals(0, service.getAllPayments().size());
		Order order = null;
		User user = new User();
		user.setUsername("pop");
		try {
			
			order = orderService.createOrder(user);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNotNull(order);
		assertEquals(error,"");
		assertEquals(order.getUser(),user);
	}
	@Test
	public void testCreateOrderWithArtworks() {
		//assertEquals(0, service.getAllPayments().size());
		Order order = null;
		User user = new User();
		user.setUsername("pop");
		HashSet<Artwork> set = new HashSet<Artwork>();
		Artwork art = new Artwork();
		art.setArtworkID(1);
		Artwork art2 = new Artwork();
		art.setWorth(100);
		art2.setWorth(150);
		set.add(art);
		set.add(art2);
		try {
			
			order = orderService.createOrder(user,set);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNotNull(order);
		assertEquals(error,"");
		assertEquals(order.getUser(),user);
		assertTrue(order.getArtwork().contains(art));
		assertEquals(order.getTotalAmount(),250);
	}
	@Test
	public void testGetOrderFromUser() {
		Order order = null;
		User user = new User();
		user.setUsername("pop");
		
		assertEquals(orderService.getOrdersFromUser(user).size(),2);
	}
	@Test
	public void testGetMostRecentOrder() {
		Order order = null;
		try {
			
			order = orderService.getMostRecentOrder(new User());
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNotNull(order);
		assertEquals(order.getOrderID(),222);
	}
	@Test
	public void testDeleteNullOrder() {
		
		try {
			
			orderService.deleteOrder(null);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals(error,"order cannot be null");
	}
	@Test
	public void testRemoveFromOrder() {
		
		Order order = new Order();
		order.setOrderStatus(OrderStatus.PaymentPending);
		Artwork art = new Artwork();
		art.setArtworkID(123);
		Set<Artwork> set = new HashSet<Artwork>();
		set.add(art);
		order.setArtwork(set);
		orderRepo.save(order);
		try {
			orderService.removeFromOrder(order, art);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertTrue(order.getArtwork().isEmpty());
	}
	
	@Test
	public void testAddToOrder() {
		
		Order order = new Order();
		order.setOrderStatus(OrderStatus.PaymentPending);
		Artwork art = new Artwork();
		art.setArtworkID(123);
		Set<Artwork> set = new HashSet<Artwork>();
		set.add(art);
		order.setArtwork(set);
		orderRepo.save(order);
		try {
			orderService.addToOrder(order, art);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals(order.getArtwork().size(),1);
	}
	@Test
	public void testAddPaymentToOrder() {
		
		Order order = new Order();
		order.setOrderStatus(OrderStatus.PaymentPending);
		Payment pay = new Payment();
		pay.setPaymentID(333);
		
		try {
			orderService.addPaymentToOrder(order, pay);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals(order.getPayment(),pay);
	}
	
	@Test
	public void testAddShipmentToOrder() {
		
		Order order = new Order();
		order.setOrderStatus(OrderStatus.Placed);
		Shipment ship = new Shipment();
		ship.setShipmentID(444);
		
		try {
			orderService.addShipmentToOrder(order, ship);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals(order.getShipment(),ship);
		assertEquals(order.getOrderStatus(),OrderStatus.Shipped);
	}

	
	
}
