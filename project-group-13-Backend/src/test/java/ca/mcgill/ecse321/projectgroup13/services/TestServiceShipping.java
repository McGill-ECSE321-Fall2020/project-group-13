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
	private ShipmentService shipmentService;

	
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
		
		lenient().when(shipmentRepo.findShipmentByShipmentID(any(Integer.class))).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(SHIPMENTID)) {
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
			return shipment;
			} else {
				return null;
			}
		});
		lenient().when(userRepository.findUserByUsername(any(String.class))).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(USERNAME)) {
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
			
				
			return user;
			} else {
				return null;
			}
		});
		lenient().when(orderRepo.findOrderByOrderID(any(Integer.class))).thenAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(ORDERID)) {
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
			order.setShipment(shipment);
			return order;
			} else {
				return null;
			}
			
		});
		
		lenient().when(shipmentRepo.findShipmentByOrder(any(Order.class))).thenAnswer((InvocationOnMock invocation) -> {
			if(((Order)invocation.getArgument(0)).getOrderID().equals(ORDERID)) {
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
			shipment.setShipmentID(SHIPMENTID);
			shipment.setAddress(address);
			shipment.setOrder(order);
			shipment.setEstimatedTimeOfArrival(Time.valueOf("14:00:00"));
			shipment.setEstimatedDateOfArrival(Date.valueOf("2020-12-20"));
			order.setShipment(shipment);
			return shipment;
			} else {
				return null;
			}
			
		});
		
		lenient().when(orderRepo.findOrdersByUser(any(User.class))).thenAnswer((InvocationOnMock invocation) -> {
			if(((User)invocation.getArgument(0)).getUsername().equals(USERNAME)) {
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
			
			HashSet<Order> orders = new HashSet<Order>();
			
			
			
			Artwork artwork = new Artwork();
			artwork.setArtworkID(ARTWORK_ID);
			
			
			HashSet<Artwork> sets = new HashSet<Artwork>();
			sets.add(artwork);
			
			user.setArtwork(sets);
			
			HashSet<User> artistss = new HashSet<User>();
			artistss.add(user);
			artwork.setArtist(artistss);
			
			Shipment shipment = new Shipment();
			shipment.setShipmentID(SHIPMENTID);
			shipment.setAddress(address);
			shipment.setOrder(order);
			shipment.setEstimatedTimeOfArrival(Time.valueOf("14:00:00"));
			shipment.setEstimatedDateOfArrival(Date.valueOf("2020-12-20"));
			order.setShipment(shipment);
			orders.add(order);
			
			return orders;
			} else {
				return null;
			}
			
		});
		
		lenient().when(shipmentRepo.findShipmentByOrder(any(Order.class))).thenAnswer((InvocationOnMock invocation) -> {
			if(((Order)invocation.getArgument(0)).getOrderID().equals(ORDERID)) {
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
			
			HashSet<Order> orders = new HashSet<Order>();
			orders.add(order);
			
			
			Artwork artwork = new Artwork();
			artwork.setArtworkID(ARTWORK_ID);
			
			
			HashSet<Artwork> sets = new HashSet<Artwork>();
			sets.add(artwork);
			
			user.setArtwork(sets);
			
			HashSet<User> artistss = new HashSet<User>();
			artistss.add(user);
			artwork.setArtist(artistss);
			
			Shipment shipment = new Shipment();
			shipment.setShipmentID(SHIPMENTID);
			shipment.setAddress(address);
			shipment.setOrder(order);
			shipment.setEstimatedTimeOfArrival(Time.valueOf("14:00:00"));
			shipment.setEstimatedDateOfArrival(Date.valueOf("2020-12-20"));
			
			
			
			return shipment;
			} else {
				return null;
			}
			
		});
		
		
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
	 	Shipment shipment = shipmentRepo.findShipmentByShipmentID(SHIPMENTID);
		String error = null;
	 	try {
	 		
	 		shipment = shipmentService.editShipmentEstimatedDate(shipment, Date.valueOf("2020-12-30"));
	 	}catch (IllegalArgumentException e) {
	 		error = e.getMessage();
	 	}
		
	 	assertEquals(error, null);
	 	assertEquals(shipment.getEstimatedDateOfArrival(),Date.valueOf("2020-12-30"));
		
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
	 		assertEquals(error, "null argument");
	 	}
		
	 }
	 @Test
	 public void testEditEstimatedTimeNullInput() {
	 	//assertEquals(0, service.getAllPayments().size());
		 String error =null;
	 	Shipment shipment = null;
	 	try {
	 		shipment = shipmentService.createShipment(ORDERID,ADDRESS_ID,Date.valueOf("2020-12-31"),Time.valueOf("14:00:00"));   
	 		shipmentService.editShipmentEstimatedTime(shipment, null);
	 	}catch (IllegalArgumentException e) {
	 		error = e.getMessage();
	 		System.out.print(error);
	 	}
	
	 	assertEquals("null argument", error);
		
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
	
	
	@Test
	public void testgetShipmentsOfUser() {
		Set<Shipment> shipment = null;
		String error = null;
		User user = userRepository.findUserByUsername(USERNAME);
	 	try {
	 		shipment=shipmentService.getShipmentsOfUser(user);
	 	}catch (IllegalArgumentException e) {
	 		error = e.getMessage();
	 		System.out.print(error);
	 	}
	 	assertNotNull(shipment);
	 	assertNull(error);
	 	
	}
	
	@Test
	public void testgetInvalidShipmentsOfUser() {
		Set<Shipment> shipment = null;
	 	try {
	 		shipment=shipmentService.getShipmentsOfUser(userRepository.findUserByUsername("hellokitty"));
	 	}catch (IllegalArgumentException e) {
	 		error = e.getMessage();
	 		System.out.print(error);
	 	}
	 	assertNull(shipment);
	 	assertEquals(error,"invalid user");
	 	
	}
	
	@Test
	public void testgetShipmentOfOrder() {
		Shipment shipment = null;
		String error = null;
	 	try {
	 		Order order = orderRepo.findOrderByOrderID(ORDERID);
	 		shipment=shipmentService.getShipmentOfOrder(order);
	 	}catch (IllegalArgumentException e) {
	 		error = e.getMessage();
	 		System.out.print(error);
	 	}
	 	assertNotNull(shipment);
	 	assertEquals(shipment.getShipmentID(), SHIPMENTID);
	 	assertNull(error);
	 	
	}
	
	@Test
	public void testInvalidgetShipmentOfOrder() {
		Shipment shipment = null;
		String error = null;
	 	try {
	 		shipment=shipmentService.getShipmentOfOrder(orderRepo.findOrderByOrderID(8008135));
	 	}catch (IllegalArgumentException e) {
	 		error = e.getMessage();
	 		System.out.print(error);
	 	}
	 	assertNull(shipment);
	 	assertEquals(error,  "invalid order");
	 	
	}
	
	@Test
	public void testgetAddressOfShipment() {
		String error = null;
		Address address = null;
	 	try {
	 		address=shipmentService.getAddressOfShipment(SHIPMENTID);
	 	}catch (IllegalArgumentException e) {
	 		error = e.getMessage();
	 		System.out.print(error);
	 	}
	 	assertNotNull(address);
	 	assertEquals(address.getCity(), CITY);
	 	assertEquals(address.getAddressID(), ADDRESS_ID);
	 	assertNull(error);
	 	
	}
	
	

}
