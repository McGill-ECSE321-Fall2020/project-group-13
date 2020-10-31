package ca.mcgill.ecse321.projectgroup13.services;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.projectgroup13.dao.ArtworkRepository;
import ca.mcgill.ecse321.projectgroup13.dao.OrderRepository;
import ca.mcgill.ecse321.projectgroup13.dao.PaymentRepository;
import ca.mcgill.ecse321.projectgroup13.model.Artwork;
import ca.mcgill.ecse321.projectgroup13.model.Order;
import ca.mcgill.ecse321.projectgroup13.model.Payment;
import ca.mcgill.ecse321.projectgroup13.model.User;


@Service
public class PaymentService {
	public <T> List<T> toList(Iterable<T> iterable) {
        List<T> resultList = new ArrayList<T>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }

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
	public Payment createPayment(long cardNumber, Date expirationDate, String nameOnCard, int cvv, Order order) {
    	if (expirationDate == null)
			throw new IllegalArgumentException("expirationDate cannot be null");
    	if (order == null)
			throw new IllegalArgumentException("order cannot be null");
    	if (nameOnCard == null || nameOnCard.equals(""))
			throw new IllegalArgumentException("nameOnCard cannot be null or empty");
    	
		Payment payment = new Payment();
		
		try { orderService.addPaymentToOrder(order, payment);} 
		catch (IllegalArgumentException e) {
			System.out.println("Could not create payment! Error : [" + e.toString() + "]");
			throw new IllegalArgumentException("Could not create payment! Error : [" + e.toString() + "]");
		}
		
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
	public int calculateGalleryCommissionAfter(Date date) {
		if (date == null)
			throw new IllegalArgumentException("date cannot be null");
		
		int result = 0;
		List<Payment> paymentList = paymentRepo.findByPaymentDateAfter(date);
		for(Payment payment : paymentList) {
			result += payment.getTotalAmount()*commissionRate;
		}
		return result;
	}
	
	@Transactional
	public List<Payment> getPaymentsForCustomer(User user){
		if (user == null)
			throw new IllegalArgumentException("user cannot be null");
		
		List<Payment> result = new ArrayList<Payment>();
		for (Order order:orderRepo.findOrdersByUser(user)) {
			result.add(order.getPayment());
		}
		return result;
	}
	
	@Transactional
	public List<Payment> getPaymentsForArtist(User user){
		if (user == null)
			throw new IllegalArgumentException("user cannot be null");
		
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
	
	@Transactional
	public List<Payment> getAllPayments() {
		return toList(paymentRepo.findAll());
	}
}

