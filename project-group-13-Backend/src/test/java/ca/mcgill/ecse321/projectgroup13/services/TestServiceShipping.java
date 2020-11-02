package ca.mcgill.ecse321.projectgroup13.services;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.projectgroup13.dao.AddressRepository;
import ca.mcgill.ecse321.projectgroup13.dao.ArtworkRepository;
import ca.mcgill.ecse321.projectgroup13.dao.OrderRepository;
import ca.mcgill.ecse321.projectgroup13.dao.ShipmentRepository;
import ca.mcgill.ecse321.projectgroup13.dao.UserRepository;
import ca.mcgill.ecse321.projectgroup13.model.Address;
import ca.mcgill.ecse321.projectgroup13.model.Artwork;
import ca.mcgill.ecse321.projectgroup13.model.Order;
import ca.mcgill.ecse321.projectgroup13.model.OrderStatus;
import ca.mcgill.ecse321.projectgroup13.model.Payment;
import ca.mcgill.ecse321.projectgroup13.model.Shipment;
import ca.mcgill.ecse321.projectgroup13.model.ShipmentStatus;
import ca.mcgill.ecse321.projectgroup13.model.User;
import ca.mcgill.ecse321.projectgroup13.services.exception.RegistrationException;
public class TestServiceShipping {
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private ArtworkRepository artworkRepository;
	@Mock
	private Order order;
	@Mock
	private User user;
	@Mock
	private Address address;
	@Mock 
	private OrderRepository orderRepo;
	@Mock
	private ShipmentRepository shipmentRepo;
	@Mock
	private AddressRepository addressRepo;
	@InjectMocks
	private UserService userService;
	@InjectMocks
	private ShipmentService shipmentService;
	@InjectMocks
	private AddressService addressService;
	@InjectMocks
	private OrderService orderService;

	
	private static final int invalidID = 404;

	private static final String USERNAME = "person1";
	private static final String USER_PASSWORD= "Thatguy123#";
	private static final String USER_EMAIL= "person1@gmail.com";
	
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
	
	private static String error = null;
	@BeforeEach
	public void setMockOutput() {
		MockitoAnnotations.initMocks(this);
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient().when(shipmentRepo.save(any(Shipment.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(addressRepo.findAddressByAddressID(ADDRESS_ID)).thenAnswer((InvocationOnMock invocation) -> {
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
			return address;
		});
		lenient().when(orderRepo.findOrderByOrderID(ORDERID)).thenAnswer((InvocationOnMock invocation) -> {
			User user = new User();
			user.setUsername(USERNAME);
			user.setEmail(USER_EMAIL);
			user.setPassword(USER_PASSWORD);
			
			Order order = new Order();
			order.setOrderID(ORDERID);
			order.setUser(user);
			
			Artwork artwork = new Artwork();
			artwork.setArtworkID(ARTWORK_ID);
			
			HashSet<Artwork> set = new HashSet<Artwork>();
			set.add(artwork);
			
			return order;
		});
		
		


	// 	lenient().when(orderRepo.findOrdersByUser(any(User.class))).thenAnswer((InvocationOnMock invocation) -> {
	// 		Set<Order> orders = new HashSet<Order>();
	// 		Order order = new Order();
	// 		Payment payment1 = new Payment();
	// 		payment1.setPaymentDate(Date.valueOf("2020-01-01"));
	// 		order.setPayment(payment1);
	// 		Order order2 = new Order();
	// 		Payment payment2 = new Payment();
	// 		payment2.setPaymentDate(Date.valueOf("2020-02-01"));
	// 		order2.setPayment(payment2);
	// 		order.setOrderID(111);
	// 		order2.setOrderID(222);
	// 		orders.add(order);
	// 		orders.add(order2);
	// 		return orders;
	// 	});
	// 	lenient().when(orderRepo.findOrderByOrderID(any(Integer.class))).thenAnswer((InvocationOnMock invocation) -> {
	// 		if (invocation.getArgument(0).equals(invalidID)) {
	// 			return null;
	// 		} else {
	// 			return order;
	// 		}
			
	// 	});
	// 	lenient().when(addressRepo.findAddressByAddressID(any(Integer.class))).thenAnswer((InvocationOnMock invocation) -> {
	// 		if (invocation.getArgument(0).equals(invalidID)) {
	// 			return null;
	// 		} else {
	// 			return address;
	// 		}
	// 	});
	// 	lenient().when(shipmentRepo.findShipmentByOrder(any(Order.class))).thenAnswer((InvocationOnMock invocation) -> {
	// 		Shipment res = new Shipment();
	// 		return res;
	// 	});
	// 	Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
	// 		return invocation.getArgument(0);
	// 	};
		
	// 	lenient().when(userRepository.save(any(User.class))).thenAnswer(returnParameterAsAnswer);
    //     lenient().when(artworkRepository.save(any(Artwork.class))).thenAnswer(returnParameterAsAnswer);
    //     lenient().when(orderRepo.save(any(Order.class))).thenAnswer(returnParameterAsAnswer);
    //     lenient().when(addressRepo.save(any(Address.class))).thenAnswer(returnParameterAsAnswer);
    //     lenient().when(shipmentRepo.save(any(Shipment.class))).thenAnswer(returnParameterAsAnswer);
	// }


	}
	@Test
	public void testCreateShipmentSuccess() {
		//assertEquals(0, service.getAllPayments().size());
		Shipment shipment = null;

		String error = null;
		//address.setAddressID(111);

		try {
			shipment = shipmentService.createShipment(ORDERID,ADDRESS_ID,Date.valueOf("1997-03-10"),Time.valueOf("10:59:59"));   
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNotNull(shipment);
		assertEquals(error,null);
		assertEquals(shipment.getAddress().getAddressID(),111);
	}
	 @Test
	 public void testCreateShipmentOrderInvalid() {
	 	//assertEquals(0, service.getAllPayments().size());
	 	Shipment shipment = null;
	 	address.setAddressID(111);
	 	try {
	 		shipment = shipmentService.createShipment(invalidID,ADDRESS_ID,Date.valueOf("2020-12-31"),Time.valueOf("14:00:00"));   
	 	}catch (IllegalArgumentException e) {
	 		error = e.getMessage();
	 	}
	 	assertNull(shipment);
	 	assertEquals(error,"order cannot be null");
		
	 }
	 @Test
	 public void testCreateShipmentAddressInvalid() {
	 	Shipment shipment = null;
	 	address.setAddressID(111);
	 	try {
	 		shipment = shipmentService.createShipment(ORDERID,100000099,Date.valueOf("2020-12-31"),Time.valueOf("14:00:00"));   
	 	}catch (IllegalArgumentException e) {
	 		error = e.getMessage();
	 	}
	 	assertNull(shipment);
	 	assertEquals(error,"address cannot be null");
		
	 }
	
	 @Test
	 public void testCreateShipmentDateNull() {
	 	Shipment shipment = null;
	 	address.setAddressID(111);
	 	try {
	 		shipment = shipmentService.createShipment(ORDERID,ADDRESS_ID,null,Time.valueOf("14:00:00"));   
	 	}catch (IllegalArgumentException e) {
	 		error = e.getMessage();
	 	}
	 	assertNull(shipment);
	 	assertEquals(error,"invalid estimatedDate");
		
	 }
	 @Test
	 public void testCreateShipmentTimeNull() {
	 	//assertEquals(0, service.getAllPayments().size());
	 	Shipment shipment = null;
	 	address.setAddressID(ADDRESS_ID);
	 	try {
	 		shipment = shipmentService.createShipment(ORDERID,ADDRESS_ID,Date.valueOf("2020-12-31"),null);   
	 	}catch (IllegalArgumentException e) {
	 		error = e.getMessage();
	 	}
	 	assertNull(shipment);
	 	assertEquals(error,"invalid TimeOfArrival");
		
	 }
	
	 @Test
	 public void testEditShipmentDate() {
	 	//assertEquals(0, service.getAllPayments().size());
	 	Shipment shipment = null;
		
	 	try {
	 		shipment = shipmentService.createShipment(ORDERID,ADDRESS_ID,Date.valueOf("2020-12-31"),Time.valueOf("14:00:00"));   
	 		shipmentService.editShipmentEstimatedDate(shipment, Date.valueOf("2020-12-25"));
	 	}catch (IllegalArgumentException e) {
	 		error = e.getMessage();
	 	}
		
	 	assertEquals(error,null);
	 	assertEquals(shipment.getEstimatedDateOfArrival(),Date.valueOf("2020-12-25"));
		
	 }
	 @Test
	 public void testEditShipmentTime() {
	 	//assertEquals(0, service.getAllPayments().size());
	 	Shipment shipment = null;
	 	String error = null;
	 	try {
	 		shipment = shipmentService.createShipment(ORDERID,ADDRESS_ID,Date.valueOf("2020-12-31"),Time.valueOf("14:00:00"));   
	 		shipmentService.editShipmentEstimatedTime(shipment, Time.valueOf("03:00:00"));
	 	}catch (IllegalArgumentException e) {
	 		error = e.getMessage();
	 	}
		
	 	assertEquals(error,null);
	 	assertEquals(shipment.getEstimatedTimeOfArrival(),Time.valueOf("03:00:00"));
		
	 }
	 @Test
	 public void testEditShipmentStatus() {
	// 	//assertEquals(0, service.getAllPayments().size());
	 	Shipment shipment = null;
		String error = null;
	 	try {
	 		shipment = shipmentService.createShipment(ORDERID,ADDRESS_ID,Date.valueOf("2020-12-31"),Time.valueOf("14:00:00"));   
	 		shipmentService.editShipmentStatus(shipment, ShipmentStatus.Delivered);
	 	}catch (IllegalArgumentException e) {
	 		System.out.print(e.getMessage());
	 	}
		
	 	System.out.print(shipment);
	 	assertEquals(error,null);
	 	assertEquals(shipment.getShipmentInfo(),ShipmentStatus.Delivered);
		
	 }
	 @Test
	 public void testEditShipmentStatusNullInput() {
	 	//assertEquals(0, service.getAllPayments().size());
	 	Shipment shipment = null;
		
	 	try {
	 		shipment = shipmentService.createShipment(ORDERID,ADDRESS_ID,Date.valueOf("2020-12-31"),Time.valueOf("14:00:00"));   
	 		shipmentService.editShipmentStatus(null, ShipmentStatus.Delivered);
	 	}catch (IllegalArgumentException e) {
	 		error = e.getMessage();
	 		assertEquals(error, "shipment cannot be null");
	 	}
		
	 }
	 @Test
	 public void testEditEstimatedTimeNullInput() {
	 	//assertEquals(0, service.getAllPayments().size());
	 	Shipment shipment = null;
	 	try {
	 		shipment = shipmentService.createShipment(ORDERID,ADDRESS_ID,Date.valueOf("2020-12-31"),Time.valueOf("14:00"));   
	 		shipmentService.editShipmentEstimatedTime(null, Time.valueOf("01:00"));
	 	}catch (IllegalArgumentException e) {
	 		error = e.getMessage();
	 		assertEquals(error, "shipment cannot be null");
	 	}
	
		
	 }
	
	@Test 

	 public void testGetUserOfShipment() {
	 	try {
	 		
	 		
			
	 		//Create a shipment with proper orderID and addressID
	 		Shipment shipment = shipmentService.createShipment(ORDERID,ADDRESS_ID,Date.valueOf("2020-12-31"),Time.valueOf("14:00:00"));   
			shipment.setShipmentID(SHIPMENTID);
	 		//Assert that shipment recieved by calling method is equal to initial user
	 		assertEquals(shipmentService.getUserOfShipment(shipment.getShipmentID()), shipment.getOrder().getUser());
			
	 	} catch (IllegalArgumentException e) {
			
	 	}
	 }

	

}
