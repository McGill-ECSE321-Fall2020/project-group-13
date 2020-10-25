package ca.mcgill.ecse321.projectgroup13.controller;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.projectgroup13.dto.PaymentDto;
import ca.mcgill.ecse321.projectgroup13.model.Order;
import ca.mcgill.ecse321.projectgroup13.model.Payment;
import ca.mcgill.ecse321.projectgroup13.services.ArtServices;

@CrossOrigin(origins = "*")
@RestController
public class ProjectGroup13Controller {

	@Autowired
	private ArtServices service;
	
	private PaymentDto convertToDto(Payment e) {
		if (e == null) {
			throw new IllegalArgumentException("There is no such Event!");
		}
		PaymentDto dto = new PaymentDto(e.getCardNumber(),e.getExpirationDate(),e.getNameOnCard(),e.getCvv(),e.getOrder());
		return dto;
	}
	
	@GetMapping(value = { "/payments", "/payments/" })
	public List<PaymentDto> getAllPayments() {
		return service.getAllPayments().stream().map(p -> convertToDto(p)).collect(Collectors.toList());
	}

	@PostMapping(value = { "/pay", "/pay/" })
	public PaymentDto PayForOrder(@RequestParam(name="card") double cardNumber, @RequestParam(name="expiry") Date expirationDate, @RequestParam(name="name") String nameOnCard, @RequestParam(name="cvv") int cvv, @RequestParam(name="order") OrderDto orderDto) throws IllegalArgumentException {
		Order order = service.getOrder(orderDto.getOrderID());
		Payment payment = service.createPayment(cardNumber, expirationDate, nameOnCard, cvv, order);
		return convertToDto(payment);
	}

}
