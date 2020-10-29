package ca.mcgill.ecse321.projectgroup13.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.projectgroup13.dao.ArtworkRepository;
import ca.mcgill.ecse321.projectgroup13.dao.OrderRepository;
import ca.mcgill.ecse321.projectgroup13.dao.PaymentRepository;
import ca.mcgill.ecse321.projectgroup13.dao.ShipmentRepository;
import ca.mcgill.ecse321.projectgroup13.dao.UserRepository;
import ca.mcgill.ecse321.projectgroup13.model.Artwork;
import ca.mcgill.ecse321.projectgroup13.model.Order;
import ca.mcgill.ecse321.projectgroup13.model.OrderStatus;
import ca.mcgill.ecse321.projectgroup13.model.Payment;
import ca.mcgill.ecse321.projectgroup13.model.User;

@Service
public class OrderService {
	@Autowired
	ArtworkRepository artworkRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PaymentRepository paymentRepository;
	
	@Autowired
	ShipmentRepository shipmentRepository;
	
	@Autowired
	OrderRepository orderRepository;
	
	@Transactional
	public Order getOrder(String orderID) {
		Order order = orderRepository.findOrderByOrderID(orderID);
		return order;
	}
	
	
	/**
	 * createOrder
	 * OrderStatus is PaymentPending when order is created.
	 * TODO: Change SetOrderID to ensure that consecutive calls generate the same value
	*/
	public Order createOrder(User user, Set<Artwork> art) {
		Order newOrder = new Order();
		newOrder.setOrderStatus(OrderStatus.PaymentPending);
		newOrder.setUser(user);
		newOrder.setArtwork(art);
		newOrder.setOrderID(Integer.toString(newOrder.hashCode()));
		
		orderRepository.save(newOrder);
		return newOrder;
	}
	
	/**
	 * payOrder
	 * TODO: Ensure that addPaymentToOrder should exist
	 * TODO: addPaymentToOrder should alter orderStatus to placed? or maybe not?
	 * 		This might be something that will be done on the payment side? i.e, to ensure that the payment is valid or something....?
	*/
	public void addPaymentToOrder(Order order, Payment payment) {
		order.setPayment(payment);
		order.setOrderStatus(OrderStatus.Placed);
	}
	//placeOrder
	
	//shipOrder
	
	//deliverOrder
	
	//getAllOrdersFromUser
	
	
	/**
	 * TODO: should we be able to getAllOrders?
	 * 		I don't think so...
	 * @return
	 */
	@Transactional
	public List<Order> getAllOrders(){
		return toList(orderRepository.findAll());
	}
	
	
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
	
}
