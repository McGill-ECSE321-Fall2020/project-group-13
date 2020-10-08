package ca.mcgill.ecse321.projectgroup13.dao;

import java.sql.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.projectgroup13.model.Order;
import ca.mcgill.ecse321.projectgroup13.model.Payment;

public interface PaymentRepository extends CrudRepository<Payment, String> {
	Payment findPaymentByPaymentID(String paymentID);
	List<Payment> findByPaymentDateAfter(Date date);
	List<Payment> findByPaymentDateBefore(Date date);
	List<Payment> findByPaymentDateBetween(Date startDate, Date endDate);
	List<Payment> findByCardNumber(double cardNumber);
	List<Payment> findByTotalAmountGreaterThan(double value);
	List<Payment> findByTotalAmountLessThan(double value);
	
	
	
	
	
}
