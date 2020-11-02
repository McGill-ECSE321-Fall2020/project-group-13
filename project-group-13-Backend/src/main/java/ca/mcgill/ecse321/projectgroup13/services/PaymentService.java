package ca.mcgill.ecse321.projectgroup13.services;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.projectgroup13.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.projectgroup13.dao.ArtworkRepository;
import ca.mcgill.ecse321.projectgroup13.dao.OrderRepository;
import ca.mcgill.ecse321.projectgroup13.dao.PaymentRepository;


@Service
public class PaymentService {
	

    //IMPORT REPOSITORIES ---> private UserRepository userRepository;
    @Autowired
    private ArtworkRepository artworkRepository;
    @Autowired
    private PaymentRepository paymentRepo;
    @Autowired
    private OrderRepository orderRepo;
    
    private double commissionRate = 0.05;
    
    //import other services
    private OrderService orderService;
    
    
    
    @Transactional
	public Payment createPayment(long cardNumber, Date expirationDate, String nameOnCard, int cvv, int orderId) {
    	Order order = orderRepo.findOrderByOrderID(orderId);
    	if (expirationDate == null)
			throw new IllegalArgumentException("expirationDate cannot be null");
    	if (order == null)
			throw new IllegalArgumentException("order cannot be null");
    	if (nameOnCard == null || nameOnCard.equals(""))
			throw new IllegalArgumentException("nameOnCard cannot be null or empty");
    	if(cardNumber<100000000000L) throw new IllegalArgumentException("Invalid card");
    	if(expirationDate.toLocalDate().isBefore(new Date(System.currentTimeMillis()).toLocalDate())) 
    		throw new IllegalArgumentException("Expired card");
		Payment payment = new Payment();
		
		
		
		payment.setTotalAmount(order.getTotalAmount());
		payment.setPaymentDate(new Date(System.currentTimeMillis()));
		payment.setPaymentTime(new Time(System.currentTimeMillis()));
		payment.setCardNumber(cardNumber);
		payment.setExpirationDate(expirationDate);
		payment.setNameOnCard(nameOnCard);
		payment.setCvv(cvv);
		payment.setOrder(order);
		payment = paymentRepo.save(payment);
		return payment;
	}

	@Transactional
	public double calculateGalleryCommissionAfter(Date date) {
		if (date == null)
			throw new IllegalArgumentException("date cannot be null");
		
		double result = 0;
		List<Payment> paymentList = paymentRepo.findByPaymentDateAfter(date);
		for(Payment payment : paymentList) {
			result += payment.getTotalAmount()*commissionRate;
		}
		return result;
	}
	
	@Transactional
	public List<Payment> getPaymentsForCustomer(User user){
		
		
		List<Payment> result = new ArrayList<Payment>();
		for (Order order:orderRepo.findOrdersByUser(user)) {
			result.add(order.getPayment());
		}
		return result;
	}
	
	@Transactional
	public List<Payment> getPaymentsForArtist(User user){
		
		List<Payment> result = new ArrayList<Payment>();
		for (Artwork art:artworkRepository.findByArtist(user)) {
			if(art.getOrder()!=null && art.getOrder().getPayment()!=null) {
				result.add(art.getOrder().getPayment());
			}
		}
		return result;
	}
	
	@Transactional
	public Payment getPayment(int paymentID) {
		Payment payment = paymentRepo.findPaymentByPaymentID(paymentID);
		return payment;
	}
	 
	//TODO:security issue. All card details would be sent to random user!
//	@Transactional
//	public List<Payment> getAllPayments() {
//		return toList(paymentRepo.findAll());
//	}
}

