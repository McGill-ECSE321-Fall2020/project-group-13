package ca.mcgill.ecse321.projectgroup13.services;

import java.util.ArrayList;
import java.util.HashSet;
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
import ca.mcgill.ecse321.projectgroup13.model.Shipment;
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
	public Order getOrder(int orderID) {
		Order order = orderRepository.findOrderByOrderID(orderID);
		return order;
	}
	

	public List<Order> getOrdersFromUser(User user) {
		if (user == null) {
			throw new IllegalArgumentException("user cannot be null");
		}
		List<Order> order = toList(orderRepository.findOrdersByUser(user));
		return order;
	}
	

	public Order getMostRecentOrder(User user) {
		if (user == null) {
			throw new IllegalArgumentException("user cannot be null");
		}
		List<Order> orders = toList(orderRepository.findOrdersByUser(user));
		Order order = orders.get(0);
		for (Order o : orders) {
			if (order.getPayment().getPaymentDate().before(o.getPayment().getPaymentDate())) {
				order = o;
			}
		}
		return order;
	}
	
	/**
	 * createOrder
	 * OrderStatus is PaymentPending when order is created.
	*/
	public Order createOrder(User user, Set<Artwork> art) {
		if (user == null) {
			throw new IllegalArgumentException("user cannot be null");
		}
		if (art == null) {
			throw new IllegalArgumentException("set<artwork> cannot be null");
		}
		Order newOrder = new Order();
		newOrder.setOrderStatus(OrderStatus.PaymentPending);
		newOrder.setUser(user);
		newOrder.setArtwork(art);
		updateTotal(newOrder);
		newOrder = orderRepository.save(newOrder);
		return newOrder;
	}
	/**
	 * createOrder
	 * OrderStatus is PaymentPending when order is created.
	*/
	public Order createOrder(User user) {
		if (user == null) {
			throw new IllegalArgumentException("user cannot be null");
		}
		Order newOrder = new Order();
		newOrder.setOrderStatus(OrderStatus.PaymentPending);
		newOrder.setUser(user);
		newOrder.setArtwork(new HashSet<Artwork>());
		newOrder.setTotalAmount(0);
		newOrder = orderRepository.save(newOrder);
		return newOrder;
	}
	
	
	
	//deleteOrder
	public boolean deleteOrder(Order order) {
		boolean b = true;
		
		//remove order and artworks association
		List<Artwork> arts = toList(order.getArtwork());
		for (Artwork a: arts) {
			a.setOrder(null);
		}
		b = b && order.getArtwork().removeAll(arts);
		
		//remove order and payments association
		Payment p = order.getPayment();
		//TODO: delete payment 
		order.setPayment(null);
		
		//remove order and shipments association
		Shipment shipment = order.getShipment();
		//TODO: delete shipment
		order.setShipment(null);
		
		//remove order and user association
		User user = order.getUser();
		user.getOrder().remove(order);
		order.setUser(null);
		
		orderRepository.delete(order);
		
		return b;
	}
	
	public boolean removeFromOrder(Order order, Artwork art) {
		boolean b = order.getArtwork().remove(art);
		updateTotal(order);
		orderRepository.save(order);
		//TODO: update order in database
		return b;
	}
	
	public boolean removeFromOrder(Order order, Set<Artwork> art) {
		boolean b = order.getArtwork().removeAll(art);
		updateTotal(order);
		orderRepository.save(order);
		//TODO: update order in database
		return b;
	}
	
	public boolean addToOrder(Order order, Set<Artwork> art) {
		boolean b = order.getArtwork().addAll(art);
		updateTotal(order);
		orderRepository.save(order);
		//TODO: update order in database
		return b;
	}
	
	public boolean addToOrder(Order order, Artwork art) {
		boolean b = order.getArtwork().add(art);
		updateTotal(order);
		orderRepository.save(order);
		//TODO: update order in database
		return b;
	}
	
	private void updateTotal(Order order) {
		List<Artwork> arts = toList(order.getArtwork());
		double total = 0;
		for (Artwork a : arts) {
			total += a.getWorth();
		}
		order.setTotalAmount(total);
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
		//TODO: update order in database
	}
	
	/**
	 * addShipmentToOrder
	 * TODO: shipment has one to one relationship with artwork in order, but there is no way to know which is for which.
	*/
	public void addShipmentToOrder(Order order, Shipment shipment) {
		order.setShipment(shipment);
		if (order.getOrderStatus().equals(OrderStatus.Placed))
			order.setOrderStatus(OrderStatus.Shipped);
		//TODO: update order in database
	}
	
	
	/**
	 * TODO: should we be able to getAllOrders?
	 * 		I don't think so...
	 * @return
	 */
	//should not be able to get all orders since that would be a security/privacy breach. Can only gain access to orders linked to user account 
//	@Transactional
//	public List<Order> getAllOrders(){
//		return toList(orderRepository.findAll());
//	}
	
	
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
	
}
