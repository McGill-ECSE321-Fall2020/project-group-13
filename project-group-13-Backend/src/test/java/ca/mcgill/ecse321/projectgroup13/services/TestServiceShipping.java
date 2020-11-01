package ca.mcgill.ecse321.projectgroup13.services;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
	
	private String error = "";
	
	private static final int invalidID = 404;
	
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
			if (invocation.getArgument(0).equals(invalidID)) {
				return null;
			} else {
				return order;
			}
			
		});
		lenient().when(addressRepo.findAddressByAddressID(any(Integer.class))).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(invalidID)) {
				return null;
			} else {
				return address;
			}
		});
		lenient().when(shipmentRepo.findShipmentByOrder(any(Order.class))).thenAnswer((InvocationOnMock invocation) -> {
			Shipment res = new Shipment();
			return res;
		});
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		
		lenient().when(userRepository.save(any(User.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(artworkRepository.save(any(Artwork.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(orderRepo.save(any(Order.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(addressRepo.save(any(Address.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(shipmentRepo.save(any(Shipment.class))).thenAnswer(returnParameterAsAnswer);
	}
	@Test
	public void testCreateShipmentSuccess() {
		//assertEquals(0, service.getAllPayments().size());
		Shipment shipment = null;
		//address.setAddressID(111);
		try {
			shipment = shipmentService.createShipment(999,111,Date.valueOf("2020-12-31"),Time.valueOf("14:00"));   
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
			System.out.println(error);
		}
		
		assertNotNull(shipment);
		assertEquals(error,"");
		assertEquals(shipment.getAddress().getAddressID(),111);
	}
	@Test
	public void testCreateShipmentOrderInvalid() {
		//assertEquals(0, service.getAllPayments().size());
		Shipment shipment = null;
		address.setAddressID(111);
		try {
			shipment = shipmentService.createShipment(invalidID,111,Date.valueOf("2020-12-31"),Time.valueOf("14:00"));   
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(shipment);
		assertEquals(error,"order cannot be null");
		
	}
	@Test
	public void testCreateShipmentAddressInvalid() {
		//assertEquals(0, service.getAllPayments().size());
		Shipment shipment = null;
		address.setAddressID(111);
		try {
			shipment = shipmentService.createShipment(100,invalidID,Date.valueOf("2020-12-31"),Time.valueOf("14:00"));   
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(shipment);
		assertEquals(error,"address cannot be null");
		
	}
	
	@Test
	public void testCreateShipmentDateNull() {
		//assertEquals(0, service.getAllPayments().size());
		Shipment shipment = null;
		address.setAddressID(111);
		try {
			shipment = shipmentService.createShipment(1,1,null,Time.valueOf("14:00"));   
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(shipment);
		assertEquals(error,"estimatedDateOfArrival");
		
	}
	@Test
	public void testCreateShipmentTimeNull() {
		//assertEquals(0, service.getAllPayments().size());
		Shipment shipment = null;
		address.setAddressID(111);
		try {
			shipment = shipmentService.createShipment(1,1,Date.valueOf("2020-12-31"),null);   
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(shipment);
		assertEquals(error,"estimatedTimeOfArrival cannot be null");
		
	}
	
	@Test
	public void testEditShipmentDate() {
		//assertEquals(0, service.getAllPayments().size());
		Shipment shipment = null;
		
		try {
			shipment = shipmentService.createShipment(1,1,Date.valueOf("2020-12-31"),Time.valueOf("14:00"));   
			shipmentService.editShipmentEstimatedDate(shipment, Date.valueOf("2020-12-25"));
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals(error,"");
		assertEquals(shipment.getEstimatedDateOfArrival(),Date.valueOf("2020-12-25"));
		
	}
	@Test
	public void testEditShipmentTime() {
		//assertEquals(0, service.getAllPayments().size());
		Shipment shipment = null;
		address.setAddressID(111);
		try {
			shipment = shipmentService.createShipment(1,1,Date.valueOf("2020-12-31"),Time.valueOf("14:00"));   
			shipmentService.editShipmentEstimatedTime(shipment, Time.valueOf("03:00"));
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals(error,"");
		assertEquals(shipment.getEstimatedTimeOfArrival(),Time.valueOf("03:00"));
		
	}
	@Test
	public void testEditShipmentStatus() {
		//assertEquals(0, service.getAllPayments().size());
		Shipment shipment = null;
		
		try {
			shipment = shipmentService.createShipment(1,1,Date.valueOf("2020-12-31"),Time.valueOf("14:00"));   
			shipmentService.editShipmentStatus(shipment, ShipmentStatus.Delivered);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals(error,"");
		assertEquals(shipment.getShipmentInfo(),ShipmentStatus.Delivered);
		
	}
	@Test
	public void testEditShipmentStatusNullInput() {
		//assertEquals(0, service.getAllPayments().size());
		Shipment shipment = null;
		
		try {
			shipment = shipmentService.createShipment(1,1,Date.valueOf("2020-12-31"),Time.valueOf("14:00"));   
			shipmentService.editShipmentStatus(null, ShipmentStatus.Delivered);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals(shipment.getShipmentInfo(),ShipmentStatus.OnRoute);
		
	}
	@Test
	public void testEditEstimatedTimeNullInput() {
		//assertEquals(0, service.getAllPayments().size());
		Shipment shipment = null;
		try {
			shipment = shipmentService.createShipment(1,1,Date.valueOf("2020-12-31"),Time.valueOf("14:00"));   
			shipmentService.editShipmentEstimatedTime(null, Time.valueOf("01:00"));
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals(shipment.getEstimatedTimeOfArrival(),Time.valueOf("14:00"));
		
	}
	

}
