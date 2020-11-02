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
import ca.mcgill.ecse321.projectgroup13.services.CartService;
import ca.mcgill.ecse321.projectgroup13.services.exception.RegistrationException;
import ca.mcgill.ecse321.projectgroup13.services.exception.illegalArgumentException;

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

import javax.persistence.PreUpdate;


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
	@Autowired
	private CartService cartService;
	
//------------------ toDTO methods ---------------------------------------------------------------------------------------------------
	
	private PaymentDto convertToDto(Payment e) {
		if (e == null) {
			return null;
//			throw new IllegalArgumentException("There is no such payment!");
		}
		PaymentDto dto = new PaymentDto(e.getPaymentID(), e.getTotalAmount(), e.getCardNumber(),e.getExpirationDate(),e.getNameOnCard(),e.getCvv(), e.getPaymentDate(), e.getPaymentTime(), convertToDto(e.getOrder()));
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

//		ShipmentDto shipmentsDto = convertToDto(order.getShipment());

		OrderDto dto = new OrderDto(order.getOrderID(), order.getTotalAmount(), order.getOrderStatus(), artworksDto, convertToDto(order.getUser()));
		return dto;
	}


	private ArtworkDto convertToDto(Artwork artwork) {
		if (artwork == null) {
			return null;
//			throw new IllegalArgumentException("There is no such artwork!");
		}

		Set<UserDto> artistsDto = new HashSet<UserDto>();
		for (User artist : artwork.getArtist()) {
			artistsDto.add(convertToDto(artist));
		}

		ArtworkDto dto = new ArtworkDto(artwork.getArtworkID(), artwork.isIsOnPremise(), artwork.getWorth(), artwork.isArtworkSold(), artwork.getDescription(), artwork.getTitle(), artwork.getCreationDate(), artwork.getDimensions(), artwork.getMedium(), artwork.getCollection(), artwork.getImageUrl(), artistsDto);
		return dto;
	}



	private UserDto convertToDto(User user) {
		if (user == null) {
			return null;
//			throw new IllegalArgumentException("There is no such user!");
		}

		UserDto dto = new UserDto(user.getUsername(), user.getEmail(), user.getBio(), user.getProfilePictureURL());
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

		CartDto dto = new CartDto(cart.getCartID(), cart.getTotalCost(), artworksDto, convertToDto(cart.getUser()));
		return dto;
	}


	private ShipmentDto convertToDto(Shipment shipment) {
		if (shipment == null) {
			return null;
		}
		ShipmentDto dto = new ShipmentDto(shipment.getShipmentID(), shipment.getShipmentInfo(), shipment.getEstimatedDateOfArrival(), shipment.getEstimatedTimeOfArrival(), convertToDto(shipment.getAddress()));
		return dto;
	}

	private AddressDto convertToDto(Address address) {
		if (address == null) {
			//throw new IllegalArgumentException("There is no such address!");
			return null;
		}
		AddressDto dto = new AddressDto(address.getAddressID(), address.getUser().getUsername(), address.getStreetAddress1(), address.getStreetAddress2(), address.getCity(), address.getProvince(), address.getCountry(), address.getPostalCode(), convertToDto(address.getUser()));
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

	//delete a user from db
	@DeleteMapping(value = {"/user/{username}/delete" , "/user/{username}/delete/"})
	public boolean deleteUser(@PathVariable("username") String username) throws RegistrationException{
		try{
			userService.deleteUser(username);
			return true;
		}catch(Exception e){
			System.out.println(e.toString());
			return false;
		}
	}


	//edit user
	@PutMapping(value = {"/user/{username}/edit" , "/user/{username}/edit/"})
	public UserDto editUser(@PathVariable("username") String username, @RequestBody User user) {
		try{
			userService.editEmail(username, user.getEmail());
			userService.editBio(username, user.getBio());
			userService.editProfilePictureUrl(username, user.getProfilePictureURL());
		}catch(Exception e){
			System.out.println(e.toString());
		}
		return convertToDto(userService.getUserByUsername(username));
	}


	//public User getUserByUsername(String username)
	//public String login(LoginDto loginDto)


// ---------------------------------------------- PAYMENT CONTROLLER METHODS




	//public Payment createPayment(long cardNumber, Date expirationDate, String nameOnCard, int cvv, Order order) 
	@PostMapping(value = { "/order/{orderId}/pay", "/order/{orderId}/pay/" })
	public PaymentDto PayForOrder(@PathVariable("orderId") int orderId, @RequestBody Payment payment) throws IllegalArgumentException {

		Order order = orderService.getOrder(orderId);
		Payment p = paymentService.createPayment(payment.getCardNumber(), new java.sql.Date(payment.getExpirationDate().getTime()), payment.getNameOnCard(), payment.getCvv(), orderId);
		
		try {
			orderService.addPaymentToOrder(order, p);
			Cart cart = order.getUser().getCart();
			cartService.deleteCart(cart);
		}
		catch (IllegalArgumentException e) {
			System.out.println("Could not create payment! Error : [" + e.toString() + "]");
			throw new IllegalArgumentException("Could not create payment! Error : [" + e.toString() + "]");
		}
		PaymentDto paymentDto = convertToDto(p);
		return paymentDto;
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
		Set<Artwork> artworks = user.getCart().getArtwork();
		
		Order order = null;
		try {
			order = orderService.createOrder(user, artworks);
		}
		catch (Exception e) {
			System.out.println("exception is: " + e.toString());
		}

		OrderDto orderDto = convertToDto(order);
		return orderDto;
	}


	//set method of delivery for order
	@PutMapping(value = { "/order/{orderId}/delivery", "/order/{orderId}/delivery/" })
	public OrderDto editIsDelivery(@PathVariable int orderId, @RequestParam(name = "delivery") boolean isDelivery){
		try{
			orderService.editIsDelivery(orderId, isDelivery);
		}catch(Exception e){
			System.out.println("NOPE " + e.toString());
		}
		return convertToDto(orderService.getOrder(orderId));
	}


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


	
	
	
	// ---------------------------------------------- SHIPMENT CONTROLLER METHODS


	/**
	 * RESTful service to create a shipment once an order is placed
	 * @param orderId
	 * @return shipment dto
	 */
	//public Shipment createShipment(Order order, Address address, ShipmentStatus status, Date estimatedDateOfArrival, Time estimatedTimeOfArrival, boolean isDelivery)

	@PostMapping(value = { "/order/{id}/shipping", "/order/{id}/shipping/"})
	public ShipmentDto createShipment(@PathVariable("id") int orderId, @RequestParam(name="address") int addressId, @RequestBody Shipment shipment){
		System.out.println("reached");
		Order order = orderService.getOrder(orderId);
//		Address address = addressService.getAddressById(addressId);

		Shipment newShipment = null;
		if (order.isShipmentMethodIsDelivery() == true) {
			newShipment = shipmentService.createShipment(orderId, addressId, shipment.getEstimatedDateOfArrival(), shipment.getEstimatedTimeOfArrival());
			try {
				orderService.addShipmentToOrder(order, newShipment);
			} catch (IllegalArgumentException e) {
				System.out.println("Could not create shipment! Error : [" + e.toString() + "]");
				throw new IllegalArgumentException("Could not create shipment! Error : [" + e.toString() + "]");
				//TODO: Maybe we should return a null value instead of throwing an exception???
			}
		}else{
			System.out.println("order is not to be delivered");
		}
		return convertToDto(newShipment);
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



	// ---------------------------------------------- ADDRESS CONTROLLER METHODS


	/**
	 * RESTful service that adds address to user
	 */
	//public Address createAddress(String username, String streetAddress1, String streetAddress2, String city, String province, String country, String postalCode) 
	@PostMapping(value = { "/user/{username}/new/address", "/user/{username}/new/address/" })
	public AddressDto createAddress(@PathVariable("username") String username, @RequestBody Address address){
		//User user = userService.getUserByUsername(username);
		AddressDto addressDto = convertToDto(addressService.createAddress(username, address.getStreetAddress1(), address.getStreetAddress2(), address.getCity(), address.getProvince(), address.getCountry(), address.getPostalCode()));
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
	public AddressDto updateAddress(@PathVariable(name = "addressId") Integer addressId, @RequestBody Address address) throws IllegalArgumentException {
		if (addressId == null) {
			throw new IllegalArgumentException("There must be an addressID to update");
		} else if (address.getStreetAddress1() == null){
			throw new IllegalArgumentException("streetAddress1 cannot be null");
		} else if (address.getStreetAddress2() == null){
			throw new IllegalArgumentException("streetAddress2 cannot be null");
		} else if (address.getCity() == null){
			throw new IllegalArgumentException("city cannot be null");
		} else if (address.getProvince() == null){
			throw new IllegalArgumentException("province cannot be null");
		} else if (address.getCountry() == null){
			throw new IllegalArgumentException("country cannot be null");
		} else if (address.getPostalCode() == null){
			throw new IllegalArgumentException("postalCode cannot be null");
		} else {

			
			addressService.updateAddress(addressId, address.getStreetAddress1(), address.getStreetAddress2(), address.getCity(), 
													address.getProvince(), address.getCountry(), address.getPostalCode());
			Address oldAddress = addressService.getAddressById(addressId);

			return convertToDto(oldAddress);
		}
	}
	
	/**
	 * RESTful service that returns user's addresses
	 */
	//public List<Address> getAddressesByUser(User user)
	@GetMapping(value = { "/user/{username}/addresses", "/user/{username}/addresses/"})
	public Set<AddressDto> getAllAddressesOfUser(@PathVariable String username){
		User user = userService.getUserByUsername(username);
		Set<AddressDto> addressesDto = new HashSet<AddressDto>();
		for(Address address : addressService.getAddressesByUser(username)) {
			addressesDto.add(convertToDto(address));
		}
		return addressesDto;
	}
	
// ---------------------------------------------- CART CONTROLLER METHODS



//	//public Cart createCart(User user)
//	@PostMapping(value = { "/user/{username}/new/cart/empty", "/user/{username}/new/cart/empty/" })
//	public CartDto createCart(@PathVariable String username){
//		User user = userService.getUserByUsername(username);
//		Cart cart = cartService.createCart(user);
//		CartDto cartDto = convertToDto(cart);
//		return cartDto;
//	}

	//public boolean addToCart(Cart cart, Artwork art)
	@PutMapping(value = { "/user/{username}/edit+/cart", "/user/{username}/edit+/cart/" })
	public CartDto addToCart(@PathVariable("username") String username, @RequestParam(name="artid") Integer artId){
		User user = userService.getUserByUsername(username);
		Artwork artwork = artworkService.getArtworkByID(artId);

		Cart cart;
		if(user.getCart() == null){			//if no cart existed
			cart = cartService.createCart(user);
			cartService.addToCart(cart, artwork);
			CartDto cartDto = convertToDto(cart);
		}else{								//if there was already a cart
			cart = user.getCart();
			cartService.addToCart(cart, artwork);
		}
		return convertToDto(cart);
	}

	
	//public Cart getCart(int cartID)
	@GetMapping(value = { "/user/{username}/cart/{cartId}", "/user/{username}/cart/{cartId}/" })
	public CartDto getCartDtoById(@PathVariable("cartId") Integer id, @PathVariable("username") String username) throws IllegalArgumentException {
		Cart cart = null;
		User user = userService.getUserByUsername(username);
		Cart userCart = cartService.getCartFromUser(user);
		if (userCart.getCartID()==id)
			cart = userCart;
	
		return convertToDto(cart);
	}
	
	//public Cart getCartFromUser(User user)
	@GetMapping(value = {"/user/{username}/cart", "/user/{username}/cart/"})
	public CartDto getCartOfUser(@PathVariable String username){
		User user = userService.getUserByUsername(username);
		Cart userCart = cartService.getCartFromUser(user);

		return convertToDto(userCart);
	}
	
	//public void deleteCart(Cart cart) 
	@DeleteMapping(value = { "/user/{username}/delete/cart/{cartId}", "/user/{username}/delete/cart/{cartId}/" })
	public boolean deleteCart(@PathVariable("username") String username, @PathVariable("cartId") Integer id){
		User user = userService.getUserByUsername(username);
		Cart cart = null;

		Cart userCart = cartService.getCartFromUser(user);
		if (userCart.getCartID()==id)
			cart = userCart;
		
		if (cart == null) 		//TODO: what to do if user is not authorized to delete cart
			return true;		//there was nothing to delete, therefore we successfully complete operation?
		
		return cartService.deleteCart(cart);
	}
	
	//public boolean removeFromCart(Cart cart, Artwork art)
	@PutMapping(value = { "/user/{username}/edit-/cart", "/user/{username}/edit-/cart/" })
	public CartDto removeFromCart(@PathVariable("username") String username, @RequestParam(name="artid") Integer artId){
		User user = userService.getUserByUsername(username);

		Cart userCart = cartService.getCartFromUser(user);
		
		if (userCart == null)
			return null;		//there was nothing to delete, therefore we successfully complete operation?
		
		Artwork art = artworkService.getArtworkByID(artId);
		try{
			cartService.removeFromCart(userCart, art);
			return convertToDto(userCart);
		}catch(Exception e){
			return convertToDto(userCart);
		}
	}
	
	
	
// ---------------------------------------------- ARTWORK CONTROLLER METHODS
	//public Artwork createArtwork(ArtworkDto artworkDto)
		
	//public Artwork createArtwork(String Title, ArrayList<String> usernames, Double worth)
	@PostMapping(value = { "/artwork/new", "/artwork/new/" })
	public ArtworkDto createArtwork(@RequestParam(name="artid") String title, @RequestParam(name="artist") String[] artists , @RequestParam(name="worth") double worth ) throws illegalArgumentException{
		//User artist = userService.getUserByUsername(username);
		Artwork art = artworkService.createArtwork(title, artists, worth);
		return convertToDto(art);
	}
	
	
	//public Artwork getArtworkByID(int artworkID)
	@GetMapping(value = { "artwork/{artId}", "artwork/{artId}/" })
	public ArtworkDto getArtworkById(@PathVariable("artId") Integer id) throws IllegalArgumentException {
		Artwork art = artworkService.getArtworkByID(id);
		return convertToDto(art);
	}
	//public void deleteArtworkById(int artworkId)
	@DeleteMapping(value = { "artwork/{artId}/delete", "artwork/{artId}/delete/" })
	public boolean deleteArtworkById(@PathVariable("artId") Integer id) throws IllegalArgumentException {
		Artwork artwork = artworkService.getArtworkByID(id);
		try{
			artworkService.deleteArtwork(artwork);
			return true;
		}catch(Exception e) {
			System.out.println(e.toString());
			return false;
		}
	}
	
	//public Set<Artwork> getArtworksOfArtist(String username)
	
	//public void editArtworkDescription(Artwork artwork, String description)
	//public void editArtwork_title(Artwork artwork, String title)
	//public void editArtwork_creationDate(Artwork artwork, String date)
	//public void editArtwork_dimensions(Artwork artwork, String dimensions)
	//public void editArtwork_medium(Artwork artwork, String medium)
	//public void editArtwork_collection(Artwork artwork, String collection)
	//public void editArtwork_imageURL(Artwork artwork, String image)
	//public void setArtwork_artworkSold(Artwork artwork)
	//public void editArtwork_isOnPremise(Artwork artwork, boolean onPremise)
	//public void editArtwork_worth(Artwork artwork, double worth)
	@PutMapping(value={"/artwork/{id}/update", "/artwork/{id}/update/"})
	public ArtworkDto updateArtwork(@PathVariable("id") Integer artId, @RequestParam(name="description", required=false) String description,
			@RequestParam(name="title", required=false) String title, @RequestParam(name="creationDate", required=false) String creationDate,
			@RequestParam(name="dimensions", required=false) String dimensions, @RequestParam(name="medium", required=false) String medium,
			@RequestParam(name="collection", required=false) String collection, @RequestParam(name="imageURL", required=false) String imageURL,
			@RequestParam(name="artworkSold", required=false, defaultValue = "false") boolean artworkSold, @RequestParam(name="OnPremise", required=false, defaultValue = "false") boolean OnPremise,
			@RequestParam(name="worth", required=false, defaultValue = "-1") double worth) {
		
		Artwork artwork = artworkService.getArtworkByID(artId);
		
		if (description != null)
			artworkService.editArtwork_description(artwork, description);
		if (title != null)
			artworkService.editArtwork_title(artwork, title);
		if (creationDate != null)
			artworkService.editArtwork_creationDate(artwork, creationDate);
		if (dimensions != null)
			artworkService.editArtwork_dimensions(artwork, dimensions);
		if (medium != null)
			artworkService.editArtwork_medium(artwork, medium);
		if (collection != null)
			artworkService.editArtwork_collection(artwork, collection);
		if (imageURL != null)
			artworkService.editArtwork_imageURL(artwork, imageURL);
		if (artworkSold)
			artworkService.setArtwork_artworkSold(artwork);
		if (OnPremise != artwork.isIsOnPremise())
			artworkService.editArtwork_isOnPremise(artwork, OnPremise);
		if (worth >= 0)
			artworkService.editArtwork_worth(artwork, worth);
		return convertToDto(artwork);
	}
	
	
}