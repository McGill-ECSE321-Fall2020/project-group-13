package ca.mcgill.ecse321.projectgroup13.controller;

import java.sql.Date;
import java.sql.Time;

import java.util.*;
import java.util.stream.Collectors;

import ca.mcgill.ecse321.projectgroup13.dto.*;
import ca.mcgill.ecse321.projectgroup13.model.*;
import ca.mcgill.ecse321.projectgroup13.services.OrderService;
import ca.mcgill.ecse321.projectgroup13.services.ShipmentService;
import ca.mcgill.ecse321.projectgroup13.services.UserService;
import ca.mcgill.ecse321.projectgroup13.services.AddressService;
import ca.mcgill.ecse321.projectgroup13.services.ArtworkService;
import ca.mcgill.ecse321.projectgroup13.services.exception.RegistrationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import ca.mcgill.ecse321.projectgroup13.dao.UserRepository;
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
	@Autowired
	private ArtworkService artworkService;
//------------------ toDTO methods ---------------------------------------------------------------------------------------------------
	
	private PaymentDto convertToDto(Payment e) {
		if (e == null) {
			return null;
//			throw new IllegalArgumentException("There is no such payment!");
		}
		PaymentDto dto = new PaymentDto(e.getPaymentID(), e.getCardNumber(),e.getExpirationDate(),e.getNameOnCard(),e.getCvv());
		return dto;
	}


	private OrderDto convertToDto(Order order) {
		if (order == null) {
			return null;
		//throw new IllegalArgumentException("There is no such order!");
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
			return null;
//			throw new IllegalArgumentException("There is no such artwork!");
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
			return null;
//			throw new IllegalArgumentException("There is no such user!");
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
			return null;
		}

		Set<ArtworkDto> artworksDto = new HashSet<ArtworkDto>();
		for (Artwork artwork : cart.getArtwork()) {
			artworksDto.add(convertToDto(artwork));
		}

		CartDto dto = new CartDto(cart.getCartID(), cart.getTotalCost(), artworksDto);
		return dto;
	}


	private ShipmentDto convertToDto(Shipment shipment) {
		if (shipment == null) {
			return null;
		}
		ShipmentDto dto = new ShipmentDto(shipment.getShipmentID(), shipment.getShipmentInfo(), shipment.getEstimatedDateOfArrival(), shipment.getEstimatedTimeOfArrival(), convertToDto(shipment.getAddress()), shipment.isShipmentMethodIsDelivery());
		return dto;
	}

	private AddressDto convertToDto(Address address) {
		if (address == null) {
			//throw new IllegalArgumentException("There is no such address!");
			return null;
		}
		AddressDto dto = new AddressDto(address.getAddressID(), address.getStreetAddress1(), address.getStreetAddress2(), address.getCity(), address.getProvince(), address.getCountry(), address.getPostalCode());
		return dto;
	}

//-----------------------------------------------------------------------------------------------------------------------------------------------------





// ---------------------------------------------- USER CONTROLLER METHODS

	// public User createUser(UserDto userDto) 
	
	//public User createUser(String username, String email, String password) 
	@PostMapping(value = {"/newuser", "/newuser/"})
	public UserDto createUser(@RequestParam(name = "username") String username, @RequestParam(name = "email") String email, @RequestParam(name = "password") String password) throws RegistrationException{
		System.out.println(username+ " " + email + " " + password);
		try{
			UserDto userDto = convertToDto(userService.createUser(username, email, password));
			return userDto;
		}catch(Exception e){
			System.out.println("user null " + e.toString());
		}
		return null;
	}

	//public void deleteUser(String username)

	//public User getUserByUsername(String username)
	
	//public void editEmail(String username, String newEmail)
	
	//public String login(LoginDto loginDto)
	
	//public void editBio(String username, String newBio)
	
	//public void editProfilePictureUrl(String username, String newUrl)
	
	//public String createToken(User user)
	
	//public String generateRandomPassword()

// ---------------------------------------------- PAYMENT CONTROLLER METHODS




	//public Payment createPayment(long cardNumber, Date expirationDate, String nameOnCard, int cvv, Order order) 
	@PostMapping(value = { "/order/{orderId}/pay", "/order/{orderId}/pay/" })
	public PaymentDto PayForOrder(@RequestParam(name="card") long cardNumber, @RequestParam(name="expiry") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) java.util.Date expirationDate, @RequestParam(name="name") String nameOnCard, @RequestParam(name="cvv") int cvv, @PathVariable("user") Integer orderId) throws IllegalArgumentException {
		Order order = orderService.getOrder(orderId);
		
		Payment payment = paymentService.createPayment(cardNumber, new java.sql.Date(expirationDate.getTime()), nameOnCard, cvv, order);
		try { orderService.addPaymentToOrder(order, payment);} 
		catch (IllegalArgumentException e) {
			System.out.println("Could not create payment! Error : [" + e.toString() + "]");
			throw new IllegalArgumentException("Could not create payment! Error : [" + e.toString() + "]");
			//TODO: Maybe return null?
		}
		return convertToDto(payment);
	}
	
	
	//public int calculateGalleryCommissionAfter(Date date)
	@GetMapping(value = { "/payments/gallery", "/payments/gallery/"})
	 public double getGalleryCommissionAfter(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) java.util.Date date){
		return paymentService.calculateGalleryCommissionAfter(new java.sql.Date(date.getTime()));
	}
	
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
	@GetMapping(value = { "/payments/{artist}/artist", "/payments/{artist}/artist/"})
	public Set<PaymentDto> getPaymentsForArtist(@PathVariable("artist") String username){
		User artist = userService.getUserByUsername(username);
		Set<PaymentDto> paymentsDto = new HashSet<PaymentDto>();
		for(Payment p : paymentService.getPaymentsForArtist(artist)) {
			paymentsDto.add(convertToDto(p));
		}
		return paymentsDto;
	}
	

	//public Payment getPayment(int paymentID)
	//TODO: should only be able to get payments linked to your account!
	@GetMapping(value = { "/payments/{id}", "/payments/{id}/" })
	public PaymentDto getPaymentById(@PathVariable("id") Integer id) throws IllegalArgumentException {
		Payment payment = paymentService.getPayment(id);
		return convertToDto(payment);
	}



	// ---------------------------------------------- ORDER CONTROLLER METHODS

	//public Order createOrder(User user)
	@PostMapping(value = { "/user/{username}/new/order", "/user/{username}/new/order/" })
	public OrderDto createOrder(@PathVariable String username){
		User user = userService.getUserByUsername(username);
		Order order = orderService.createOrder(user);
		OrderDto orderDto = convertToDto(order);
		return orderDto;
	}
	
	//public Order createOrder(User user, Set<Artwork> art)

	//public List<Order> getOrdersFromUser(User user)
	@GetMapping(value = {"/user/{username}/orders", "/user/{username}/orders/"})
	public List<OrderDto> getAllOrdersOfUser(@PathVariable String username){
		List<OrderDto> orders = new ArrayList<OrderDto>();
		User user = userService.getUserByUsername(username);
		for(Order order: orderService.getOrdersFromUser(user)){
			orders.add(convertToDto(order));
		}
		return orders;
	}
	
	//public Order getMostRecentOrder(User user)
	@GetMapping(value = {"/user/{username}/orders/most-recent", "/user/{username}/orders/most-recent/"})
	public OrderDto getMostRecentOrderOfUser(@PathVariable String username){
		OrderDto order;
		User user = userService.getUserByUsername(username);
		order = convertToDto(orderService.getMostRecentOrder(user));
		
		return order;
	}
	
	//public Order getOrder(int orderID)
	@GetMapping(value = { "/user/{username}/order/{orderId}", "/user/{username}/order/{orderId}/" })
	public OrderDto getOrderDtoById(@PathVariable("orderId") Integer id, @PathVariable("username") String username) throws IllegalArgumentException {
		Order order = null;
		User user = userService.getUserByUsername(username);
		for(Order o: orderService.getOrdersFromUser(user)){		//cannot get order with only the orderId, also need to userId
			if (o.getOrderID()==id) {
				order = o;
				break;
			}
		}
		return convertToDto(order);
	}
	
	//public boolean deleteOrder(Order order)
	@DeleteMapping(value = { "/user/{username}/delete/order/{orderId}", "/user/{username}/delete/order/{orderId}/" })
	public boolean deleteOrder(@PathVariable("username") String username, @PathVariable("orderId") Integer id){
		User user = userService.getUserByUsername(username);
		Order order = null;

		for(Order o: orderService.getOrdersFromUser(user)){		//cannot get order with only the orderId, also need to userId
			if (o.getOrderID()==id) {
				order = o;
				break;
			}
		}
		
		if (order == null) 		//TODO: what to do if user is not authorized to delete order
			return true;		//there was nothing to delete, therefore we successfully complete operation?
		
		return orderService.deleteOrder(order);
	}
	
	//public boolean removeFromOrder(Order order, Artwork art)
	@PutMapping(value = { "/user/{username}/edit-/order/{orderId}", "/user/{username}/edit-/order/{orderId}/" })
	public boolean removeFromOrder(@PathVariable("username") String username, @PathVariable("orderId") Integer id, @RequestParam(name="artid") Integer artId){
		Order order = null;
		User user = userService.getUserByUsername(username);
		for(Order o: orderService.getOrdersFromUser(user)){		//cannot get order with only the orderId, also need to userId
			if (o.getOrderID()==id) {
				order = o;
				break;
			}
		}
		
		if (order == null)
			return false;
		
		Artwork art = artworkService.getArtworkByID(artId);
		return orderService.removeFromOrder(order, art);
	}
	
	//public boolean addToOrder(Order order, Artwork art)
	@PutMapping(value = { "/user/{username}/edit+/order/{orderId}", "/user/{username}/edit+/order/{orderId}/" })
	public boolean addToOrder(@PathVariable("username") String username, @PathVariable("orderId") Integer id, @RequestParam(name="artid") Integer artId) {
		Order order = null;
		User user = userService.getUserByUsername(username);
		for(Order o: orderService.getOrdersFromUser(user)){		//cannot get order with only the orderId, also need to userId
			if (o.getOrderID()==id) {
				order = o;
				break;
			}
		}
		
		if (order == null)
			return false;
		
		Artwork art = artworkService.getArtworkByID(artId);
		return orderService.addToOrder(order, art);
	}
	
	//TODO: Are these two methods necessary? If so, how do we implement their RESTful service controller methods?
	//public boolean removeFromOrder(Order order, Set<Artwork> art)
	//public boolean addToOrder(Order order, Set<Artwork> art)
	
	
	
	// ---------------------------------------------- SHIPMENT CONTROLLER METHODS


	/**
	 * RESTful service to create a shipment once an order is placed
	 * @param orderId
	 * @return shipment dto
	 */
	//public Shipment createShipment(Order order, Address address, ShipmentStatus status, Date estimatedDateOfArrival, Time estimatedTimeOfArrival, boolean isDelivery)
	@PostMapping(value = { "/order/{id}/shipping", "/order/{id}/shipping/"})
	public ShipmentDto createShipment(@PathVariable("id") int orderId, @RequestParam(name="address") Address address, @RequestParam(name="status") ShipmentStatus status, @RequestParam(name="delivery") boolean isDelivery){
		Order order = orderService.getOrder(orderId);
		ShipmentDto shipmentDto = null;
		if (order.isShipmentMethodIsDelivery() == true) {
			shipmentDto = convertToDto(shipmentService.createShipment(orderService.getOrder(orderId), address, status, Date.valueOf("2020-8-04"), Time.valueOf("18:07"), isDelivery));
		}else{
			System.out.println("order is not to be delivered");
		}
		return shipmentDto;
	}

	//public Shipment getShipment(int shipmentID)
	
	//public User getUserOfShipment(int shipmentID)
	
	//public Shipment getShipmentOfOrder(Order order)
	
	//public Set<Shipment> getShipmentsOfUser(User user)
	/**
	 * RESTful service that gets all shipments of a user
	 * @param username
	 * @return DTO shipments
	 */
	@GetMapping(value = { "/user/{username}/shipments", "/user/{username}/shipments/"})
	public Set<ShipmentDto> getAllShipmentsOfUser(@PathVariable String username){
		Set<ShipmentDto> shipmentsDto = new HashSet<ShipmentDto>();
		User user = userService.getUserByUsername(username);
		for(Shipment shipment : shipmentService.getShipmentsOfUser(user)) {
			shipmentsDto.add(convertToDto(shipment));
		}
		return shipmentsDto;
	}
	
	//public Shipment editShipmentStatus (Shipment shipment, ShipmentStatus status)
	
	//public Shipment editShipmentEstimatedDate (Shipment shipment, Date estimatedDate)
	
	//public Shipment editShipmentEstimatedTime (Shipment shipment, Time estimatedTime)
	
	/**
	 * RESTful service that gets all shipments in database
	 * @return DTO shipments
	 */
	//public Set<Shipment> getAllShipments() 
	//TODO: Should this method exist? It is a security flaw since it sends data about all users to anyone
	@GetMapping(value = { "/shipments", "/shipments/"})
	public Set<ShipmentDto> getAllShipments(){
		Set<ShipmentDto> shipmentsDto = new HashSet<ShipmentDto>();
		for(Shipment shipment : shipmentService.getAllShipments()) {
			shipmentsDto.add(convertToDto(shipment));
		}
		return shipmentsDto;
	}

	// ---------------------------------------------- ADDRESS CONTROLLER METHODS

	/**
	 * RESTful service that adds address to user
	 */
	//public Address createAddress(String username, String streetAddress1, String streetAddress2, String city, String province, String country, String postalCode) 
	@PostMapping(value = { "/user/{username}/new/address", "/user/{username}/new/address/" })
	public AddressDto createAddress(@PathVariable("username") String username, String streetAddress1, String streetAddress2, String city, String province, String country, String postalCode){
		//User user = userService.getUserByUsername(username);
		AddressDto addressDto = convertToDto(addressService.createAddress(username, streetAddress1, streetAddress2, city, province, country, postalCode));
		return addressDto;
	}

	//public User getUserOfAddress(Integer addressID)
	
	//public Address getAddressById(Integer addressID) 
	
	
	//public boolean deleteAddress(int addressId)
	/**
	 * RESTful service that deletes address by id
	 */
	@DeleteMapping(value = {"/address/{addressId}/delete", "/address/{addressId}/delete/"})
	public boolean deleteAddress(@PathVariable(name = "addressId") Integer addressId) {
		if (addressId == null) {
			throw new IllegalArgumentException("There is no such Address Id!");
		} else {
			return addressService.deleteAddress(addressId);
		}
	}
	
	//public void updateAddress(Address oldAddress, String streetAddress1, String streetAddress2, String city, String province, String country, String postalCode)
	/**
	 * RESTful service that updates address by id
	 */
	@PutMapping(value = {"/address/{addressId}/update", "/address/{addressId}/update/"})
	public AddressDto updateAddress(@PathVariable(name = "addressId") @RequestParam Integer addressId, @RequestParam String streetAddress1, @RequestParam String streetAddress2, 
			@RequestParam String city, @RequestParam String province, @RequestParam String country, @RequestParam String postalCode) throws IllegalArgumentException {
		if (addressId == null) {
			throw new IllegalArgumentException("There must be an addressID to update");
		} else if (streetAddress1 == null){
			throw new IllegalArgumentException("streetAddress1 cannot be null");
		} else if (streetAddress2 == null){
			throw new IllegalArgumentException("streetAddress2 cannot be null");
		} else if (city == null){
			throw new IllegalArgumentException("city cannot be null");
		} else if (province == null){
			throw new IllegalArgumentException("province cannot be null");
		} else if (country == null){
			throw new IllegalArgumentException("country cannot be null");
		} else if (postalCode == null){
			throw new IllegalArgumentException("postalCode cannot be null");
		} else {

			Address oldAddress = addressService.getAddressById(addressId);
			addressService.updateAddress(oldAddress, streetAddress1, streetAddress2, city, province, country, postalCode);
			return convertToDto(oldAddress);
		}
	}
	
	/**
	 * RESTful service that returns user's addresses
	 */
	//public List<Address> getAddressesByUser(User user)
	@GetMapping(value = { "/user/{username}/addresses", "/user/{username}/addresses/"})
	public Set<AddressDto> getAllAddressesOfUser(@RequestParam String username){
		User user = userService.getUserByUsername(username);
		Set<AddressDto> addressesDto = new HashSet<AddressDto>();
		for(Address address : addressService.getAddressesByUser(user)) {
			addressesDto.add(convertToDto(address));
		}
		return addressesDto;
	}
	
// ---------------------------------------------- CART CONTROLLER METHODS
	//public Cart createCart(User user)
	
	//public Cart createCart(User user, Artwork art)
	
	//public Cart createCart(User user, Set<Artwork> art)
	
	//public Cart getCart(int cartID)
	
	//public Cart getCartFromUser(User user)
	
	//public boolean deleteCart(int cartID)
	
	//public void deleteCart(Cart cart) 
	
	//public boolean removeFromCart(Cart cart, Artwork art)
	
	//public boolean addToCart(Cart cart, Artwork art)
	
	//public Set<Artwork> removeFromCart(Cart cart, Set<Artwork> art)
	
	//public Set<Artwork> addToCart(Cart cart, Set<Artwork> art)
	
	
	
// ---------------------------------------------- ARTWORK CONTROLLER METHODS
	//public Artwork createArtwork(ArtworkDto artworkDto)
	
	//public Artwork createArtwork(String Title, ArrayList<String> usernames, Double worth)
	
	//public void deleteArtwork(Artwork artwork)
	
	//public void deleteArtworkById(int artworkId)
	
	//public Artwork getArtworkByID(int artworkID)
	
	//public Set<Artwork> getArtworksOfArtist(String username)
	
	//public void editArtworkDescription(Artwork artwork, String description)
	
	//TODO: Missing a bunch of update methods in ArtworkService. Need to write those and then add RESTful methods here to map to them.
	
}