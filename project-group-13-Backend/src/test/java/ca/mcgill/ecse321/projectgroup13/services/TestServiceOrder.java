package ca.mcgill.ecse321.projectgroup13.services;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
	private static final int ARTWORK_ID = 142857;
	private static final int ARTWORK_ID2 = 857142;
	private static final int ARTWORK_ID3 = 571428;
	private static final int ORDER_ID = 852963;
	private static final int ORDER_ID2 = 6484526;
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
		lenient().when(orderRepo.findOrderByOrderID(any(Integer.class))).thenAnswer((InvocationOnMock invocation) -> {
			
			Order order = new Order();
			order.setOrderID(invocation.getArgument(0));
			Payment payment1 = new Payment();
			payment1.setPaymentDate(Date.valueOf("2020-01-01"));
			order.setPayment(payment1);
			
			return order;
			
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
	public void testDeleteOrder() {
		boolean isDeleted = false;
		User user = new User(); 
		Set<Order> orders = new HashSet<Order>();
		Set<Artwork> artworks = new HashSet<Artwork>();
		Artwork art = new Artwork();
		artworks.add(art);
		Order order = new Order();
		
		order.setArtwork(artworks);
		order.setOrderStatus(OrderStatus.PaymentPending);
		art.setOrder(order);
		Order order2 = new Order();
		Payment payment2 = new Payment();
		payment2.setPaymentDate(Date.valueOf("2020-02-01"));
		order2.setPayment(payment2);
		order.setOrderID(ORDER_ID);
		order2.setOrderID(ORDER_ID2);
		order.setUser(user);
		order2.setUser(user);
		orders.add(order);
		orders.add(order2);
		user.setOrder(orders);
		try {
			isDeleted = false;
			isDeleted = orderService.deleteOrder(order);
		} catch(IllegalArgumentException e) {
			//Throws an invalid email error for some reason, but address gets added and deleted
			fail();
		}
		assertEquals(user.getOrder().size(),1);
		assertTrue(isDeleted);
		assertNull(art.getOrder());
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
	public void testRemoveSetFromOrder() {
		
		Order order = new Order();
		order.setOrderStatus(OrderStatus.PaymentPending);
		Artwork art = new Artwork();
		art.setArtworkID(ARTWORK_ID);
		Artwork art2 = new Artwork();
		art2.setArtworkID(ARTWORK_ID2);
		Artwork art3 = new Artwork();
		art2.setArtworkID(ARTWORK_ID3);
		Set<Artwork> set = new HashSet<Artwork>();
		Set<Artwork> setToRemove = new HashSet<Artwork>();
		set.add(art);
		set.add(art2);
		set.add(art3);
		setToRemove.add(art);
		setToRemove.add(art2);
		order.setArtwork(set);
		orderRepo.save(order);
		try {
			orderService.removeFromOrder(order, setToRemove);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(error,"");
		assertEquals(order.getArtwork().iterator().next().getArtworkID(),ARTWORK_ID3);
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
	public void testAddSetToOrder() {
		
		Order order = new Order();
		order.setOrderStatus(OrderStatus.PaymentPending);
		Artwork art = new Artwork();
		art.setArtworkID(ARTWORK_ID);
		Artwork art2 = new Artwork();
		art.setArtworkID(ARTWORK_ID2);
		Set<Artwork> set = new HashSet<Artwork>();
		set.add(art);
		set.add(art2);
		order.setArtwork(set);
		orderRepo.save(order);
		try {
			orderService.addToOrder(order, set);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals(order.getArtwork().size(),2);
		assertEquals(art.getOrder(),order);
		assertEquals(art2.getOrder(),order);
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
	@Test
	public void testEditIsDelivery() {
		orderService.editIsDelivery(ORDER_ID, true);
		
	}
	@Test
	public void testNullUserCreateOrder() {
		try {
			orderService.createOrder(null,new HashSet<Artwork>());
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNotEquals(error,"");
	}
	@Test
	public void testNullArtworkCreateOrder() {
		try {
			orderService.createOrder(new User(),null);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNotEquals(error,"");
	}
	@Test
	public void testNullUserCreateOrder2() {
		try {
			orderService.createOrder(null);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNotEquals(error,"");
	}
	@Test
	public void testNullUserGetOrderFromUser() {
		try {
			orderService.getOrdersFromUser(null);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNotEquals(error,"");
	}
	@Test
	public void testNullUserGetMostRecentOrder() {
		try {
			orderService.getMostRecentOrder(null);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNotEquals(error,"");
	}
	@Test
	public void testUserWithNoOrderGetMostRecentOrder() {
		try {
			orderService.getMostRecentOrder(new User());
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNotEquals(error,"");
	}
	@Test
	public void testNullOrderRemoveFromOrder() {
		try {
			orderService.removeFromOrder(null, new Artwork());
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNotEquals(error,"");
	}
	@Test
	public void testNullOrderRemoveArtworksFromOrder() {
		try {
			orderService.removeFromOrder(null, new HashSet<Artwork>());
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNotEquals(error,"");
	}
	
	
	

	
	
}
