package ca.mcgill.ecse321.projectgroup13.services;

import ca.mcgill.ecse321.projectgroup13.dao.*;
import ca.mcgill.ecse321.projectgroup13.dto.*;


import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ca.mcgill.ecse321.projectgroup13.model.*;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/*
 * Service to handle login and registration of users.
 */
//TODO: Change name to PaymentService
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
    
    
    //TODO: when payment sets order, order should also set payment
    @Transactional
	public Payment createPayment(long cardNumber, Date expirationDate, String nameOnCard, int cvv, Order order) {
		Payment payment = new Payment();
		payment.setTotalAmount(order.getTotalAmount());
		payment.setPaymentDate(new Date(System.currentTimeMillis()));
		payment.setPaymentTime(new Time(System.currentTimeMillis()));
		payment.setCardNumber(cardNumber);
		payment.setExpirationDate(expirationDate);
		payment.setNameOnCard(nameOnCard);
		payment.setCvv(cvv);
		payment.setOrder(order);
		paymentRepo.save(payment);
		return payment;
	}

	@Transactional
	public int calculateGalleryCommissionAfter(Date date) {
		int result = 0;
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
	

	@Transactional
	public List<Payment> getAllPayments() {
		return toList(paymentRepo.findAll());
	}
    // DECLARE 

    //get necessary data from the existing repositories 
    //      // @Transactional
             // public List<User> getAllUsers() {
             //     return toList(userRepository.findAll());
    //      }
    @Transactional
    public List<Artwork> getAllArt() {
        return toList(artworkRepository.findAll());
    } 
    //declare CRUD methods for Art
    //
    




}

