package ca.mcgill.ecse321.projectgroup13.controller;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import ca.mcgill.ecse321.projectgroup13.dto.*;
import ca.mcgill.ecse321.projectgroup13.model.*;
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
	private ArtServices service;
	
	private PaymentDto convertToDto(Payment e) {
		if (e == null) {
			throw new IllegalArgumentException("There is no such payment!");
		}
		PaymentDto dto = new PaymentDto(e.getCardNumber(),e.getExpirationDate(),e.getNameOnCard(),e.getCvv(),convertToDto(e.getOrder()));
		return dto;
	}


	private OrderDto convertToDto(Order order) {
		if (order == null) {
			throw new IllegalArgumentException("There is no such order!");
		}
		OrderDto dto = new OrderDto(order.getTotalAmount(), order.getOrderID(), order.getOrderStatus(), convertToDto(order.getArtwork()), convertToDto(order.getUser()), convertToDto(order.getPayment()), convertToDto(order.getShipment()));
		return dto;
	}


	private ArtworkDto convertToDto(Artwork artwork) {
		if (artwork == null) {
			throw new IllegalArgumentException("There is no such artwork!");
		}
		ArtworkDto dto = new ArtworkDto(artwork.isIsOnPremise(), convertToDto(artwork.getArtist()), artwork.getArtworkID(), convertToDto(artwork.getOrder()), artwork.getWorth(), artwork.isArtworkSold(), artwork.getDescription(), artwork.getTitle(), artwork.getCreationDate(), artwork.getDimensions(), artwork.getMedium(), artwork.getCollection(), artwork.getImageUrl());
		return dto;
	}


	private Set<ArtworkDto> convertToDto(Set<Artwork> artworks) {
		if (artworks == null) {
			throw new IllegalArgumentException("There are no such artworks!");
		}
		Set<ArtworkDto> dto = new HashSet<ArtworkDto>();
		for (Artwork artwork : artworks) {
			dto.add(convertToDto(artwork));
		}
		return dto;
	}


	private UserDto convertToDto(User user) {
		if (user == null) {
			throw new IllegalArgumentException("There is no such user!");
		}
		UserDto dto = new UserDto(convertToDto(user.getCart()), convertToDto(user.getArtwork()), user.getBio(), convertToDto(user.getAddress()), user.getUsername(), user.getEmail(), user.getProfilePictureURL());
		return dto;
	}


//	private Set<UserDto> convertToDto(Set<User> users) {
//		if (users == null) {
//			throw new IllegalArgumentException("There are no such artworks!");
//		}
//		Set<UserDto> dto = new HashSet<UserDto>();
//		for (User user : users) {
//			dto.add(convertToDto(user));
//		}
//		return dto;
//	}


	private CartDto convertToDto(Cart cart) {
		if (cart == null) {
			throw new IllegalArgumentException("There is no such cart!");
		}
		CartDto dto = new CartDto(cart.getCartID(), cart.getTotalCost(), convertToDto(cart.getArtwork()), convertToDto(cart.getUser()));
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


//	private Set<UserDto> convertToDto(Set<User> users) {
//		if (users == null) {
//			throw new IllegalArgumentException("There are no such users!");
//		}
//		Set<UserDto> dto = new HashSet<UserDto>();
//		for (User user : users) {
//			dto.add(convertToDto(user));
//		}
//		return dto;
//	}






	@GetMapping(value = { "/payments", "/payments/" })
	public List<PaymentDto> getAllPayments() {
		return service.getAllPayments().stream().map(p -> convertToDto(p)).collect(Collectors.toList());
	}

//	@PostMapping(value = { "/pay", "/pay/" })
//	public PaymentDto PayForOrder(@RequestParam(name="card") double cardNumber, @RequestParam(name="expiry") Date expirationDate, @RequestParam(name="name") String nameOnCard, @RequestParam(name="cvv") int cvv, @RequestParam(name="order") OrderDto orderDto) throws IllegalArgumentException {
//		Order order = service.getOrder(orderDto.getOrderID());
//		Payment payment = service.createPayment(cardNumber, expirationDate, nameOnCard, cvv, order);
//		return convertToDto(payment);
//	}

}
