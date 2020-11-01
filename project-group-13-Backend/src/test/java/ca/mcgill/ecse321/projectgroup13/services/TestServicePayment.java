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
import java.util.Calendar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.projectgroup13.dao.OrderRepository;
import ca.mcgill.ecse321.projectgroup13.dao.PaymentRepository;
import ca.mcgill.ecse321.projectgroup13.dao.UserRepository;
import ca.mcgill.ecse321.projectgroup13.model.Artwork;
import ca.mcgill.ecse321.projectgroup13.model.Order;
import ca.mcgill.ecse321.projectgroup13.model.Payment;
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
	private Order order;
	@InjectMocks
	private OrderService orderService;
	@InjectMocks
	private PaymentService service;
	@InjectMocks
	private UserService userService;
	
	private String error = "";
	@BeforeEach
	public void setMockOutput() {
		MockitoAnnotations.initMocks(this);
		//lenient().when(orderService.addPaymentToOrder(any(Order.class),any(Payment.class))).thenAnswer((InvocationOnMock invocation) -> {
			
		//});
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
			payment = service.createPayment(6011871064009705L, Date.valueOf("2020-12-12"), "David", 111, order);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNotNull(payment);
		assertEquals(error,"");
		assertEquals(payment.getNameOnCard(),"David");
	}
	@Test
	public void testCreatePaymentNullOrder() {
		//assertEquals(0, service.getAllPayments().size());
		Payment payment = null;
		try {
			payment = service.createPayment(6011871064009705L, Date.valueOf("2020-12-12"), "David", 111, null);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(error,"Order cannot be null");
		assertNull(payment);
	}
	@Test
	public void testCreatePaymentInvalidCardNumber() {
		//assertEquals(0, service.getAllPayments().size());
		Payment payment = null;
		try {
			payment = service.createPayment(9705L, Date.valueOf("2020-12-12"), "David", 111, order);
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
			payment = service.createPayment(6011871064009705L, Date.valueOf("2020-01-12"), "David", 111, order);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(error,"Expired card");
		assertNull(payment);
	}
	@Test
	public void testGetPaymentByCustomer() {
		//assertEquals(0, service.getAllPayments().size());
		error = "";
		User user = null;
		Order order = null;
		try{user = userService.createUser("david", "david@gmail.com", "aAbc123");}
		catch(RegistrationException e) {
			error = e.getMessage();
		}
		Payment payment = null;
		try{order = orderService.createOrder(user);}
		catch(Exception e) {
			error += e.getMessage();
		}
		
		try {
			payment = service.createPayment(6011871064009705L, Date.valueOf("2020-01-12"), "David", 111, order);
		}catch (IllegalArgumentException e) {
			error += e.getMessage();
		}
		
	}
	@Test
	public void testCalculateGalleryCommissionAfter() {
		//assertEquals(0, service.getAllPayments().size());
		
	}
	@Test
	public void testGetPaymentsForArtist() {
		//assertEquals(0, service.getAllPayments().size());
		
	}
	
}
