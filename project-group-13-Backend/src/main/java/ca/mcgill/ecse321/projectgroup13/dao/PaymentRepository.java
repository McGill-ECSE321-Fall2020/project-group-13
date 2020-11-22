package ca.mcgill.ecse321.projectgroup13.dao;

import ca.mcgill.ecse321.projectgroup13.model.Order;
import ca.mcgill.ecse321.projectgroup13.model.Payment;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.List;

public interface PaymentRepository extends CrudRepository<Payment, String> {

	Payment findPaymentByPaymentID(Integer paymentID);
	Payment findPaymentByOrder(Order order);
	List<Payment> findByPaymentDateAfter(Date date);
	List<Payment> findByPaymentDateBefore(Date date);
	List<Payment> findByPaymentDateBetween(Date startDate, Date endDate);
	List<Payment> findByCardNumber(double cardNumber);
	List<Payment> findByTotalAmountGreaterThan(double value);
	List<Payment> findByTotalAmountLessThan(double value);

}
