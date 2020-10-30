package ca.mcgill.ecse321.projectgroup13.controller;

import java.sql.Date;
import java.sql.Time;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import ca.mcgill.ecse321.projectgroup13.dto.*;
import ca.mcgill.ecse321.projectgroup13.model.*;
import ca.mcgill.ecse321.projectgroup13.services.OrderService;
import ca.mcgill.ecse321.projectgroup13.services.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.projectgroup13.services.ArtServices;

@CrossOrigin(origins = "*")
@RestController
public class ProjectGroup13Controller {

	@Autowired
	private ArtServices paymentService;
	@Autowired
	private ShipmentService shipmentService;
	@Autowired
	private OrderService orderService;

	
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

		Set<ShipmentDto> shipmentsDto = new HashSet<ShipmentDto>();
		for (Shipment shipment : order.getShipment()) {
			shipmentsDto.add(convertToDto(shipment));
		}

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





	@GetMapping(value = { "/payments", "/payments/" })
	public List<PaymentDto> getAllPayments() {
		return paymentService.getAllPayments().stream().map(p -> convertToDto(p)).collect(Collectors.toList());
	}

//	@PostMapping(value = { "/pay", "/pay/" })
//	public PaymentDto PayForOrder(@RequestParam(name="card") double cardNumber, @RequestParam(name="expiry") Date expirationDate, @RequestParam(name="name") String nameOnCard, @RequestParam(name="cvv") int cvv, @RequestParam(name="order") OrderDto orderDto) throws IllegalArgumentException {
//		Order order = service.getOrder(orderDto.getOrderID());
//		Payment payment = service.createPayment(cardNumber, expirationDate, nameOnCard, cvv, order);
//		return convertToDto(payment);
//	}


	/**
	 * RESTful service that gets all shipments of an order
	 * @param order
	 * @return DTO shipments
	 */
	@GetMapping(value = { "/order//shipments", "/order//shipments/"})
	public Set<ShipmentDto> getAllShipmentsOfOrder(@RequestParam Order order){
		Set<ShipmentDto> shipmentsDto = new HashSet<ShipmentDto>();
		for(Shipment shipment : shipmentService.getShipmentsOfOrder(order)) {
			shipmentsDto.add(convertToDto(shipment));
		}
		return shipmentsDto;
	}


	/**
	 * RESTful service that gets all shipments of a user
	 * @param user
	 * @return DTO shipments
	 */
	@GetMapping(value = { "/user/{username}/shipments", "/user/{username}/shipments/"})
	public Set<ShipmentDto> getAllShipmentsOfUser(@RequestParam User user){
		Set<ShipmentDto> shipmentsDto = new HashSet<ShipmentDto>();
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
	@PostMapping(value = { "/order/{id}/shipping", "/order/{id}/shipping/"})
	public ShipmentDto createShipment(@PathVariable("id") int orderId, @RequestParam Address address, ShipmentStatus status, boolean isDelivery){
		ShipmentDto shipmentDto = convertToDto(shipmentService.createShipment(orderService.getOrder(orderId), address, status, Date.valueOf("2020-8-04"), Time.valueOf("18:07"), isDelivery));
		return shipmentDto;
	}

}