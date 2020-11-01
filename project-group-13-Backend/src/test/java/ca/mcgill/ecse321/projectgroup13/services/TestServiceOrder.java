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

import ca.mcgill.ecse321.projectgroup13.dao.OrderRepository;
import ca.mcgill.ecse321.projectgroup13.dao.PaymentRepository;
import ca.mcgill.ecse321.projectgroup13.dao.UserRepository;
import ca.mcgill.ecse321.projectgroup13.model.Artwork;
import ca.mcgill.ecse321.projectgroup13.model.Order;
import ca.mcgill.ecse321.projectgroup13.model.Payment;
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
		set.add(art);
		try {
			
			order = orderService.createOrder(user,set);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNotNull(order);
		assertEquals(error,"");
		assertEquals(order.getUser(),user);
		assertEquals(art,set.iterator().next());
	}
	@Test
	public void testGetOrderFromUser() {
		Order order = null;
		User user = new User();
		user.setUsername("pop");
		try {
			
			order = orderService.createOrder(user);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals(orderService.getOrdersFromUser(user).size(),1);
	}
	
	
}
