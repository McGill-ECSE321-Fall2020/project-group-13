package ca.mcgill.ecse321.projectgroup13.services;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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

import ca.mcgill.ecse321.projectgroup13.dao.ArtworkRepository;
import ca.mcgill.ecse321.projectgroup13.dao.OrderRepository;
import ca.mcgill.ecse321.projectgroup13.dao.PaymentRepository;
import ca.mcgill.ecse321.projectgroup13.dao.UserRepository;
import ca.mcgill.ecse321.projectgroup13.model.Address;
import ca.mcgill.ecse321.projectgroup13.model.Artwork;
import ca.mcgill.ecse321.projectgroup13.model.Order;
import ca.mcgill.ecse321.projectgroup13.model.Payment;
import ca.mcgill.ecse321.projectgroup13.model.Shipment;
import ca.mcgill.ecse321.projectgroup13.model.User;
import ca.mcgill.ecse321.projectgroup13.services.exception.RegistrationException;

@ExtendWith(MockitoExtension.class)
public class TestServicePayment {
	@Mock
	private UserRepository userRepo; 
	@Mock
	private OrderRepository orderRepo;
	@Mock
	private PaymentRepository paymentRepo;
	@Mock
	private ArtworkRepository artworkRepo;
	@Mock
	private Order order;
	@Mock
	private Order order2;
	@InjectMocks
	private OrderService orderService;
	@InjectMocks
	private PaymentService service;
	@InjectMocks
	private UserService userService;
	
	private String error = "";
	private static final int invalidID = 404;
	private static final int order1ID = 1;
	private static final int order2ID = 2;
	private static final int artworkID = 11;
	private static final String USERNAME2 = "person2";
	private static final String USER_PASSWORD2= "Thatgirl123#";
	private static final String USER_EMAIL2= "person2@gmail.com";
	private static final String ARTWORK_TITLE= "BEAUTY";
	private static final String COUNTRY= "CANADA";
	private static final String CITY= "MONTREAL";
	private static final Integer ARTWORK_ID= 1234;
	private static final Integer ORDERID= 999;
	private static final Integer ADDRESS_ID= 111;

	private static final Integer SHIPMENTID = 200;

	private static final Double WORTH = 100.00;
	private static final String TITLE = "BEAUTY";
	private static final String USERNAME = "person1";
	private static final String USER_PASSWORD= "Thatguy123#";
	private static final String USER_EMAIL= "person1@gmail.com";
	private static final double PAYMENT_AMOUNT = 10.0;
	private static final String[] ARTISTS = {USERNAME};
	@BeforeEach
	public void setMockOutput() {
		MockitoAnnotations.initMocks(this);
		lenient().when(order.getTotalAmount()).thenAnswer((InvocationOnMock invocation) -> {
			return 10.0;
		});
		lenient().when(order2.getTotalAmount()).thenAnswer((InvocationOnMock invocation) -> {
			return 20.0;
		});
		
		lenient().when(paymentRepo.findByPaymentDateAfter(any(Date.class))).thenAnswer((InvocationOnMock invocation) -> {
			
			
			Payment payment = new Payment();
			payment.setTotalAmount(10);
			Payment payment2 = new Payment();
			payment2.setTotalAmount(20);
			List<Payment> list = new ArrayList<Payment>();
			list.add(payment);
			list.add(payment2);
			
			return list;
			
			
		});
		lenient().when(orderRepo.findOrderByOrderID(any(Integer.class))).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(invalidID)) {
				return null;
			} else if (invocation.getArgument(0).equals(order1ID)){
				return order;
			} else if(invocation.getArgument(0).equals(order2ID)) {
				return order2;
			}
			else {
				return order;
			}
			
		});
		
		lenient().when(userRepo.findUserByUsername(any(String.class))).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(invalidID)) {
				User user = new User();
				user.setUsername(USERNAME);
				user.setEmail(USER_EMAIL);
				user.setPassword(USER_PASSWORD);
				
				Address address = new Address();
				
				address.setAddressID(ADDRESS_ID);
				address.setCity(CITY);
				address.setCountry(COUNTRY);
				address.setPostalCode("H4C2C4");
				address.setUser(user);
				
				HashSet<Address> set = new HashSet<Address>();
				set.add(address);
				user.setAddress(set);
				
				Order order = new Order();
				order.setOrderID(ORDERID);
				order.setUser(user);
				
				Artwork artwork = new Artwork();
				artwork.setArtworkID(ARTWORK_ID);
				
				
				HashSet<Artwork> sets = new HashSet<Artwork>();
				sets.add(artwork);
				
				user.setArtwork(sets);
				
				HashSet<User> artistss = new HashSet<User>();
				artistss.add(user);
				artwork.setArtist(artistss);
				
				Shipment shipment = new Shipment();
				shipment.setAddress(address);
				shipment.setOrder(order);
				shipment.setEstimatedTimeOfArrival(Time.valueOf("14:00:00"));
				shipment.setEstimatedDateOfArrival(Date.valueOf("2020-12-20"));
				
				




				Payment payment = new Payment();
				payment.setTotalAmount(PAYMENT_AMOUNT);
				order.setPayment(payment);
	
				return user;
				
				
			} 
			else {
				return order;
			}
			
		});
		
		
		lenient().when(artworkRepo.findArtworkByArtist(any(User.class))).thenAnswer((InvocationOnMock invocation) -> {
			Set<Artwork> set = new HashSet<Artwork>();
			Artwork art = new Artwork();
			art.setArtworkID(artworkID);
			Order ord = new Order();
			Payment payment = new Payment();
			payment.setTotalAmount(PAYMENT_AMOUNT);
			order.setPayment(payment);
			art.setOrder(ord);
			set.add(art);
			return set;
			
		});
		lenient().when(orderRepo.findOrdersByUser(any(User.class))).thenAnswer((InvocationOnMock invocation) -> {
			Set<Order> set = new HashSet<Order>();
			Order order = new Order();
			order.setOrderID(order1ID);
			Payment payment = new Payment();
			payment.setTotalAmount(PAYMENT_AMOUNT);
			order.setPayment(payment);
			set.add(order);
			return set;
			
			
		});
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		
		lenient().when(paymentRepo.save(any(Payment.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(orderRepo.save(any(Order.class))).thenAnswer(returnParameterAsAnswer);
	}
	@Test
	public void testCreatePaymentSuccess() {
		//assertEquals(0, service.getAllPayments().size());
		Payment payment = null;
		try {
			payment = service.createPayment(6011871064009705L, Date.valueOf("2020-12-12"), "David", 111, 111);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNotNull(payment);
		assertEquals(error,"");
		assertEquals(payment.getNameOnCard(),"David");
	}
	@Test
	public void testCreatePaymentInvalidOrder() {
		//assertEquals(0, service.getAllPayments().size());
		Payment payment = null;
		try {
			payment = service.createPayment(6011871064009705L, Date.valueOf("2020-12-12"), "David", 111, invalidID);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(error,"order cannot be null");
		assertNull(payment);
	}
	@Test
	public void testCreatePaymentInvalidCardNumber() {
		//assertEquals(0, service.getAllPayments().size());
		Payment payment = null;
		try {
			payment = service.createPayment(9705L, Date.valueOf("2020-12-12"), "David", 111, 111);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(error,"Invalid card");
		assertNull(payment);
		
	}
	@Test
	public void testCreatePaymentExpiredCard() {
		//assertEquals(0, service.getAllPayments().size());
		Payment payment = null;
		try {
			payment = service.createPayment(6011871064009705L, Date.valueOf("2020-01-12"), "David", 111, 111);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(error,"Expired card");
		assertNull(payment);
	}
	
	@Test
	public void testGetPaymentsByCustomer() {
		//assertEquals(0, service.getAllPayments().size());
		List<Payment> payments = service.getPaymentsForCustomer(userRepo.findUserByUsername(USERNAME));
		assertEquals( payments.iterator().next().getTotalAmount(),PAYMENT_AMOUNT);
		
	}
	@Test
	public void testGetPaymentArtist() {
		List<Payment> payments = service.getPaymentsForArtist(userRepo.findUserByUsername(USERNAME));
		assertEquals(payments.iterator().next().getTotalAmount(),PAYMENT_AMOUNT);
	}
	
	@Test
	public void testCalculateGalleryCommissionAfter() {
		//assertEquals(0, service.getAllPayments().size());
		Payment payment = null;
		try {
			payment = service.createPayment(6011871064009705L, Date.valueOf("2020-12-12"), "David", 111, order1ID);
			payment = service.createPayment(6011871064009705L, Date.valueOf("2020-12-12"), "David", 111, order2ID);
		}catch (IllegalArgumentException e) {
			error += e.getMessage();
		}
		assertEquals(service.calculateGalleryCommissionAfter(Date.valueOf("2020-01-01")),1.5); //$30*5% = $1.5
	}
	
	@Test
	public void testGetPaymentForCustomer() {
		//assertEquals(0, service.getAllPayments().size());
		Payment payment = null;
		payment = service.createPayment(6011871064009705L, Date.valueOf("2020-12-12"), "David", 111, order1ID);
		payment = service.createPayment(6011871064009705L, Date.valueOf("2020-12-12"), "David", 111, order2ID);
		assertEquals(service.calculateGalleryCommissionAfter(Date.valueOf("2020-01-01")),1.5); //$30*5% = $1.5
	}
	
	
}
