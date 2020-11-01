package ca.mcgill.ecse321.projectgroup13.controller;

import java.sql.Date;
import java.sql.Time;
<<<<<<< HEAD
import java.util.HashSet;
import java.util.Set;

=======
import java.util.*;
import java.util.stream.Collectors;

import ca.mcgill.ecse321.projectgroup13.dto.*;
import ca.mcgill.ecse321.projectgroup13.model.*;
import ca.mcgill.ecse321.projectgroup13.services.OrderService;
import ca.mcgill.ecse321.projectgroup13.services.ShipmentService;
import ca.mcgill.ecse321.projectgroup13.services.UserService;
import ca.mcgill.ecse321.projectgroup13.services.AddressService;
import ca.mcgill.ecse321.projectgroup13.services.exception.RegistrationException;
import net.minidev.json.JSONUtil;
>>>>>>> 47e533f112dc52fe5aee376d3438a8ceb7f3eda9
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ca.mcgill.ecse321.projectgroup13.dao.UserRepository;
import ca.mcgill.ecse321.projectgroup13.dto.AddressDto;
import ca.mcgill.ecse321.projectgroup13.dto.ArtworkDto;
import ca.mcgill.ecse321.projectgroup13.dto.CartDto;
import ca.mcgill.ecse321.projectgroup13.dto.OrderDto;
import ca.mcgill.ecse321.projectgroup13.dto.PaymentDto;
import ca.mcgill.ecse321.projectgroup13.dto.ShipmentDto;
import ca.mcgill.ecse321.projectgroup13.dto.UserDto;
import ca.mcgill.ecse321.projectgroup13.model.Address;
import ca.mcgill.ecse321.projectgroup13.model.Artwork;
import ca.mcgill.ecse321.projectgroup13.model.Cart;
import ca.mcgill.ecse321.projectgroup13.model.Order;
import ca.mcgill.ecse321.projectgroup13.model.Payment;
import ca.mcgill.ecse321.projectgroup13.model.Shipment;
import ca.mcgill.ecse321.projectgroup13.model.ShipmentStatus;
import ca.mcgill.ecse321.projectgroup13.model.User;
import ca.mcgill.ecse321.projectgroup13.services.AddressService;
import ca.mcgill.ecse321.projectgroup13.services.OrderService;
import ca.mcgill.ecse321.projectgroup13.services.PaymentService;
import ca.mcgill.ecse321.projectgroup13.services.ShipmentService;
import ca.mcgill.ecse321.projectgroup13.services.UserService;

@CrossOrigin(origins = "*")
@RestController
public class ProjectGroup13Controller {

	@Autowired
	private PaymentService paymentService;
	@Autowired
	private ShipmentService shipmentService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private UserService userService;
	@Autowired
	private AddressService addressService;

//------------------ toDTO methods ---------------------------------------------------------------------------------------------------
	
	private PaymentDto convertToDto(Payment e) {
		if (e == null) {
			throw new IllegalArgumentException("There is no such payment!");
		}
		PaymentDto dto = new PaymentDto(e.getPaymentID(), e.getCardNumber(),e.getExpirationDate(),e.getNameOnCard(),e.getCvv(),convertToDto(e.getOrder()));
		return dto;
	}


	private OrderDto convertToDto(Order order) {
		if (order == null) {
			throw new IllegalArgumentException("There is no such order!");
		}

		Set<ArtworkDto> artworksDto = new HashSet<ArtworkDto>();
		for (Artwork artwork : order.getArtwork()) {
			artworksDto.add(convertToDto(artwork));
		}

		ShipmentDto shipmentsDto = convertToDto(order.getShipment());

		OrderDto dto = new OrderDto(order.getOrderID(), order.getTotalAmount(), order.getOrderStatus(), artworksDto, convertToDto(order.getUser()), convertToDto(order.getPayment()), shipmentsDto);
		return dto;
	}


	private ArtworkDto convertToDto(Artwork artwork) {
		if (artwork == null) {
			throw new IllegalArgumentException("There is no such artwork!");
		}

		Set<UserDto> artists = new HashSet<UserDto>();
		for (User artist : artwork.getArtist()) {
			artists.add(convertToDto(artist));
		}

		ArtworkDto dto = new ArtworkDto(artwork.getArtworkID(), artwork.isIsOnPremise(), artists,  convertToDto(artwork.getOrder()), artwork.getWorth(), artwork.isArtworkSold(), artwork.getDescription(), artwork.getTitle(), artwork.getCreationDate(), artwork.getDimensions(), artwork.getMedium(), artwork.getCollection(), artwork.getImageUrl());
		return dto;
	}



	private UserDto convertToDto(User user) {
		if (user == null) {
			throw new IllegalArgumentException("There is no such user!");
		}

		Set<ArtworkDto> artworksDto = new HashSet<ArtworkDto>();
		for (Artwork artwork : user.getArtwork()) {
			artworksDto.add(convertToDto(artwork));
		}

		Set<AddressDto> addressDtos = new HashSet<AddressDto>();
		for (Address address : user.getAddress()) {
			addressDtos.add(convertToDto(address));
		}

		Set<OrderDto> ordersDto = new HashSet<OrderDto>();
		for (Order order : user.getOrder()) {
			ordersDto.add(convertToDto(order));
		}

		UserDto dto = new UserDto(convertToDto(user.getCart()), artworksDto, user.getBio(), addressDtos, ordersDto ,user.getUsername(), user.getEmail(), user.getProfilePictureURL());
		return dto;
	}


	private CartDto convertToDto(Cart cart) {
		if (cart == null) {
			throw new IllegalArgumentException("There is no such cart!");
		}

		Set<ArtworkDto> artworksDto = new HashSet<ArtworkDto>();
		for (Artwork artwork : cart.getArtwork()) {
			artworksDto.add(convertToDto(artwork));
		}

		CartDto dto = new CartDto(cart.getCartID(), cart.getTotalCost(), artworksDto, convertToDto(cart.getUser()));
		return dto;
	}


	private ShipmentDto convertToDto(Shipment shipment) {
		if (shipment == null) {
			throw new IllegalArgumentException("There is no such shipment!");
		}
		ShipmentDto dto = new ShipmentDto(shipment.getShipmentID(), shipment.getShipmentInfo(), shipment.getEstimatedDateOfArrival(), shipment.getEstimatedTimeOfArrival(), convertToDto(shipment.getOrder()), convertToDto(shipment.getAddress()), shipment.isShipmentMethodIsDelivery());
		return dto;
	}


	private AddressDto convertToDto(Address address) {
		if (address == null) {
			throw new IllegalArgumentException("There is no such address!");
		}
		AddressDto dto = new AddressDto(address.getAddressID(), address.getStreetAddress1(), address.getStreetAddress2(), address.getCity(), address.getProvince(), address.getCountry(), address.getPostalCode(), convertToDto(address.getUser()));
		return dto;
	}

//-----------------------------------------------------------------------------------------------------------------------------------------------------

//----------------------------Payment RESTful service--------------------------
	
	//public Payment createPayment(long cardNumber, Date expirationDate, String nameOnCard, int cvv, Order order) 
	@PostMapping(value = { "/pay", "/pay/" })
	public PaymentDto PayForOrder(@RequestParam(name="card") long cardNumber, @RequestParam(name="expiry") Date expirationDate, @RequestParam(name="name") String nameOnCard, @RequestParam(name="cvv") int cvv, @RequestParam(name="orderId") Integer orderId) throws IllegalArgumentException {
		Order order = orderService.getOrder(orderId);
		Payment payment = paymentService.createPayment(cardNumber, expirationDate, nameOnCard, cvv, order);
		return convertToDto(payment);
	}
	
	//public int calculateGalleryCommissionAfter(Date date)
	
	
	//public List<Payment> getPaymentsForCustomer(User user)
	@GetMapping(value = { "/payments/{user}/customer", "/payments/{user}/customer/"})
	public Set<PaymentDto> getPaymentsForCustomer(@PathVariable("user") String username){
		User user = userService.getUserByUsername(username);
		Set<PaymentDto> paymentsDto = new HashSet<PaymentDto>();
		for(Payment p : paymentService.getPaymentsForCustomer(user)) {
			paymentsDto.add(convertToDto(p));
		}
		return paymentsDto;
	}
	
	//public List<Payment> getPaymentsForArtist(User user)
	@GetMapping(value = { "/payments/{artist}/artist", "/payments/{artist}/artist"})
	public Set<PaymentDto> getPaymentsForArtist(@PathVariable("artist") String username){
		User artist = userService.getUserByUsername(username);
		Set<PaymentDto> paymentsDto = new HashSet<PaymentDto>();
		for(Payment p : paymentService.getPaymentsForArtist(artist)) {
			paymentsDto.add(convertToDto(p));
		}
		return paymentsDto;
	}
	
	//public Payment getPayment(int paymentID)
	@GetMapping(value = { "/payments/{id}", "/payments/{id}/" })
	public PaymentDto createPerson(@PathVariable("id") Integer id) throws IllegalArgumentException {
		Payment payment = paymentService.getPayment(id);
		return convertToDto(payment);
	}
	
	
	

//	/**
//	 * RESTful service to create a person
//	 * @param name
//	 * @return shipment dto
//	 */
//	@PostMapping(value = { "/person/{name}", "/person/{name}/" })
//	public UserDto createPerson(@PathVariable("name") String name) throws IllegalArgumentException {
//		User user = userService.createUser(name);
//		return convertToDto(user);
//	}


<<<<<<< HEAD
	//TODO: Security flaw. All card info would be sent
//	@GetMapping(value = { "/payments", "/payments/" })
//	public List<PaymentDto> getAllPayments() {
//		return paymentService.getAllPayments().stream().map(p -> convertToDto(p)).collect(Collectors.toList());
//	}
=======
	@PostMapping(value = {"/newuser", "/newuser/"})
	public UserDto createUser(@RequestParam(name = "username") String username, @RequestParam(name = "email") String email, @RequestParam(name = "password") String password) throws RegistrationException{
		System.out.println(username+ " " + email + " " + password);
		try{
//			UserDto userDto = convertToDto(userService.createUser(username, email, password));
//			return userDto;
			UserDto userDto = convertToDto(userService.createUser(username, email, password));
			return userDto;
		}catch(Exception e){
			System.out.println("user null "+e.toString());
		}
		return null;
	}


	@GetMapping(value = { "/payments", "/payments/" })
	public List<PaymentDto> getAllPayments() {
		return paymentService.getAllPayments().stream().map(p -> convertToDto(p)).collect(Collectors.toList());
	}


	@PostMapping(value = { "/pay", "/pay/" })
	public PaymentDto PayForOrder(@RequestParam(name="card") long cardNumber, @RequestParam(name="expiry") Date expirationDate, @RequestParam(name="name") String nameOnCard, @RequestParam(name="cvv") int cvv, @RequestParam(name="order") OrderDto orderDto) throws IllegalArgumentException {
		Order order = orderService.getOrder(orderDto.getOrderID());
		
		Payment payment = paymentService.createPayment(cardNumber, expirationDate, nameOnCard, cvv, order);
		try { orderService.addPaymentToOrder(order, payment);} 
		catch (IllegalArgumentException e) {
			System.out.println("Could not create payment! Error : [" + e.toString() + "]");
			throw new IllegalArgumentException("Could not create payment! Error : [" + e.toString() + "]");
		}
		return convertToDto(payment);
	}
>>>>>>> 47e533f112dc52fe5aee376d3438a8ceb7f3eda9


	@PostMapping(value = { "/{username}/order", "/{username}/order/" })
	public OrderDto createOrder(@PathVariable String username){
		User user = userService.getUserByUsername(username);
		Order order = orderService.createOrder(user);
		OrderDto orderDto = convertToDto(order);
		return orderDto;
	}


	@GetMapping(value = {"/user/{username}/orders", "/user/{username}/orders/"})
	public List<OrderDto> getAllOrders(@PathVariable String username){
		List<OrderDto> orders = new ArrayList<OrderDto>();
		User user = userService.getUserByUsername(username);
		for(Order order: orderService.getOrdersFromUser(user)){
			orders.add(convertToDto(order));
		}
		return orders;
	}


	/**
	 * RESTful service that gets all shipments in database
	 * @return DTO shipments
	 */
	@GetMapping(value = { "/shipments", "/shipments/"})
	public Set<ShipmentDto> getAllShipments(){
		Set<ShipmentDto> shipmentsDto = new HashSet<ShipmentDto>();
		for(Shipment shipment : shipmentService.getAllShipments()) {
			shipmentsDto.add(convertToDto(shipment));
		}
		return shipmentsDto;
	}


	/**
	 * RESTful service that gets all shipments of a user
	 * @param username
	 * @return DTO shipments
	 */
	@GetMapping(value = { "/shipments/user/{username}", "/shipments/user/{username}/"})
	public Set<ShipmentDto> getAllShipmentsOfUser(@PathVariable String username){
		Set<ShipmentDto> shipmentsDto = new HashSet<ShipmentDto>();
		User user = userService.getUserByUsername(username);
		for(Shipment shipment : shipmentService.getShipmentsOfUser(user)) {
			shipmentsDto.add(convertToDto(shipment));
		}
		return shipmentsDto;
	}


	/**
	 * RESTful service to create a shipment once an order is placed
	 * @param orderId
	 * @return shipment dto
	 */
//	@PostMapping(value = { "/order/{id}/shipping", "/order/{id}/shipping/"})
//	public ShipmentDto createShipment(@PathVariable("id") int orderId, @RequestBody Address address, ShipmentStatus status, boolean isDelivery){
//		ShipmentDto shipmentDto = convertToDto(shipmentService.createShipment(orderService.getOrder(orderId), address, status, Date.valueOf("2020-8-04"), Time.valueOf("18:07"), isDelivery));
//		return shipmentDto;
//	}

	
	/**
	 * RESTful service that returns user's addresses
	 */
	
	@GetMapping(value = { "/user/{username}/addresses", "/user/{username}/addresses/"})
	public Set<AddressDto> getAllAddressOfUser(@RequestParam User user){
		Set<AddressDto> addressesDto = new HashSet<AddressDto>();
		for(Address address : addressService.getAddressesByUser(user)) {
			addressesDto.add(convertToDto(address));
		}
		return addressesDto;
	}
	
	/**
	 * RESTful service that adds address to user 
	 */
	
	@PostMapping(value = { "user/{username}/addresses", "user/{username}/addresses" }) 
	public AddressDto createAddress(@PathVariable("username") String username, @RequestParam User user, String streetAddress1, String streetAddress2, String city, String province, String country, String postalCode){
		AddressDto addressDto = convertToDto(addressService.createAddress(user, streetAddress1, streetAddress2, city, province, country, postalCode));
		return addressDto;
	}
	
	/**
	 * RESTful service that deletes address by id
	 */
	
	@DeleteMapping(value = {"/addresses}", "/addresses/addressId}"})
	public boolean deleteAddress(@PathVariable(name = "addressId") Integer addressId) {
		if (addressId == null) {
			throw new IllegalArgumentException("There is no such Donation Id!");
		} else {
			return addressService.deleteAddress(addressId);
		}
	}
}