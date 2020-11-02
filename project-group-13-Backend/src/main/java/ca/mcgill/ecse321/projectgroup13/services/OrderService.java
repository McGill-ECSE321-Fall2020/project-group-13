package ca.mcgill.ecse321.projectgroup13.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ca.mcgill.ecse321.projectgroup13.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.projectgroup13.dao.OrderRepository;
import ca.mcgill.ecse321.projectgroup13.dao.PaymentRepository;
import ca.mcgill.ecse321.projectgroup13.model.Artwork;
import ca.mcgill.ecse321.projectgroup13.model.Order;
import ca.mcgill.ecse321.projectgroup13.model.OrderStatus;
import ca.mcgill.ecse321.projectgroup13.model.Payment;
import ca.mcgill.ecse321.projectgroup13.model.Shipment;
import ca.mcgill.ecse321.projectgroup13.model.User;

@Service
public class OrderService {
	
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	PaymentRepository paymentRepository;
	
	
	/**
	 * create a new order with a user and collection of art
	 * @param user	User who owns the order
	 * @param art Set of art to add to order
	 * @return new order object
	 */
	@Transactional
	public Order createOrder(User user, Set<Artwork> art) {
		if (user == null) 														//must check parameter is not null
			throw new IllegalArgumentException("user cannot be null");
		if (art == null) 
			throw new IllegalArgumentException("set<artwork> cannot be null");	//must check parameter is not null
		
		Order newOrder = new Order();
		newOrder.setOrderStatus(OrderStatus.PaymentPending);
		newOrder.setUser(user);
		newOrder.setArtwork(art);
		updateTotal(newOrder);
		newOrder = orderRepository.save(newOrder);
		return newOrder;
	}

	
	/**
	 * create a new order with a user and no art
	 * @param user User who owns the order
	 * @return new order object
	 */
	@Transactional
	public Order createOrder(User user) {
		if (user == null) 												//must check parameter is not null
			throw new IllegalArgumentException("user cannot be null");
		
		Order newOrder = new Order();
		newOrder.setOrderStatus(OrderStatus.PaymentPending);
		newOrder.setUser(user);
		newOrder.setArtwork(new HashSet<Artwork>());
		newOrder.setTotalAmount(0);
		newOrder = orderRepository.save(newOrder);
		return newOrder;
	}
	
	/**
	 * get order from order ID
	 * @param orderID
	 * @return order if order exists. null otherwise
	 * TODO: what is behavior of repository when object doesn't exist?
	 */
	@Transactional
	public Order getOrder(int orderID) {
		Order order = orderRepository.findOrderByOrderID(orderID);
		return order;
	}
	
	/**
	 * get all orders linked to a user
	 * @param user user whose orders we are finding
	 * @return list of Order objects
	 */
	@Transactional
	public List<Order> getOrdersFromUser(User user) {
		if (user == null)													//must check parameter is not null
			throw new IllegalArgumentException("user cannot be null");
		
		List<Order> order = toList(orderRepository.findOrdersByUser(user));
		return order;
	}
	
	/**
	 * get most recent order made by User
	 * @param user user whose order wer are finding
	 * @return most recent order made by user
	 */
	@Transactional
	public Order getMostRecentOrder(User user) {
		if (user == null) 																//must check parameter is not null
			throw new IllegalArgumentException("user cannot be null");
		if (orderRepository.findOrdersByUser(user).isEmpty())							//user needs to have orders to get recent order
			throw new IllegalArgumentException("user has no orders associated to it");
		
		List<Order> orders = toList(orderRepository.findOrdersByUser(user));
		
		Order order = orders.get(0);
		for (Order o : orders) {
			
			if (o.getPayment()!=null && order.getPayment().getPaymentDate().before(o.getPayment().getPaymentDate())) 
				order = o;
		}
		return order;
	}
	
	
	/**
	 * delete an unfinalized order and removes it from the database
	 * @param order order to delete
	 * @return whether the order was successfully deleted
	 */
	@Transactional
	public boolean deleteOrder(Order order) {
		if (order == null)															//must check parameter is not null
			throw new IllegalArgumentException("order cannot be null");
		if (order.getOrderStatus() != OrderStatus.PaymentPending)					//must check that order hasn't been finalized. Cannot delete order that has payment and shipment
			throw new IllegalArgumentException("Cannot delete a finalized order");
		
		boolean b = true;
		
		//remove order and artworks association
		if (!order.getArtwork().isEmpty()) {
			List<Artwork> arts = toList(order.getArtwork());
			for (Artwork a: arts) 
				a.setOrder(null);
			b = b && order.getArtwork().removeAll(arts);
		}
		
		//remove order and user association
		User user = order.getUser();
		user.getOrder().remove(order);
		order.setUser(null);
		paymentRepository.delete(order.getPayment());
		orderRepository.delete(order);
		
		return b;
	}
	
	/**
	 * removes art from an unfinalized order
	 * @param order order to remove from
	 * @param art art to remove from order
	 * @return whether the art was successfully removed from the order
	 */
	@Transactional
	public boolean removeFromOrder(Order order, Artwork art) {
		if (order == null) 															//check that user parameter is not null
			throw new IllegalArgumentException("order cannot be null");
		if (art == null) 															//check that art parameter is not null
			throw new IllegalArgumentException("artwork cannot be null");
		if (order.getOrderStatus() != OrderStatus.PaymentPending)					//must check that order hasn't been finalized
			throw new IllegalArgumentException("Cannot alter a finalized order");
		
		boolean b = order.getArtwork().remove(art);
		updateTotal(order);
		orderRepository.save(order);
		return b;
	}
	
	/**
	 * removes a collection of art from an unfinalized order
	 * @param order order to remove from
	 * @param art collection to remove from order
	 * @return whether the art was successfully removed
	 */
	@Transactional
	public boolean removeFromOrder(Order order, Set<Artwork> art) {
		if (order == null)															//must check parameter is not null
			throw new IllegalArgumentException("order cannot be null");
		if (art == null)															//must check parameter is not null
			throw new IllegalArgumentException("set<artwork> cannot be null");	
		if (order.getOrderStatus() != OrderStatus.PaymentPending)					//must check that order hasn't been finalized
			throw new IllegalArgumentException("Cannot alter a finalized order");
		
		boolean b = order.getArtwork().removeAll(art);
		updateTotal(order);
		orderRepository.save(order);
		return b;
	}
	
	/**
	 * add a collection of artwork to an unfinalized order
	 * @param order order to add to 
	 * @param art collection to add to order
	 * @return whether the operation was successful
	 */
	@Transactional
	public boolean addToOrder(Order order, Set<Artwork> art) {
		if (order == null)															//must check parameter is not null
			throw new IllegalArgumentException("order cannot be null");
		if (art == null)															//must check parameter is not null
			throw new IllegalArgumentException("set<artwork> cannot be null");
		if (order.getOrderStatus() != OrderStatus.PaymentPending)					//must check that order hasn't been finalized
			throw new IllegalArgumentException("Cannot alter a finalized order");
		
		boolean b = order.getArtwork().addAll(art);
		updateTotal(order);
		orderRepository.save(order);
		return b;
	}
	
	/**
	 * add art to an unfinalized order
	 * @param order order to add to
	 * @param art art to add to order
	 * @return whether the operation was successful
	 */
	@Transactional
	public boolean addToOrder(Order order, Artwork art) {
		if (order == null) 															//must check parameter is not null
			throw new IllegalArgumentException("order cannot be null");
		if (art == null) 															//must check parameter is not null
			throw new IllegalArgumentException("artwork cannot be null");
		if (order.getOrderStatus() != OrderStatus.PaymentPending)					//must check that order hasn't been finalized
			throw new IllegalArgumentException("Cannot alter a finalized order");
		
		boolean b = order.getArtwork().add(art);
		updateTotal(order);
		orderRepository.save(order);
		return b;
	}
	
	/**
	 * add payment to an unfinalized order
	 * @param order order to add payment to 
	 * @param payment payment to add to order
	 */
	@Transactional
	public void addPaymentToOrder(Order order, Payment payment) {
		if (order.getOrderStatus() != OrderStatus.PaymentPending)					//must check that order hasn't been finalized
			throw new IllegalArgumentException("Cannot alter a finalized order");
		
		order.setPayment(payment);
		order.setOrderStatus(OrderStatus.Placed);
		orderRepository.save(order);
	}
	
	/**
	 * add shipment to unshipped order. Order needs to have a payment in order to add a shipment
	 * @param order order has to be unshipped but have a payment
	 * @param shipment shipment to add to order
	 */
	@Transactional
	public void addShipmentToOrder(Order order, Shipment shipment) {
		if (order.getOrderStatus() == OrderStatus.Shipped || order.getOrderStatus() == OrderStatus.Delivered)	//must check that order hasn't been finalized.
			throw new IllegalArgumentException("Cannot alter shipment after an order was shipped");
		if (order.getOrderStatus() != OrderStatus.Placed)														//check that order has been paid
			throw new IllegalArgumentException("Cannot alter shipment until Order is payed");
		
		order.setShipment(shipment);
		order.setOrderStatus(OrderStatus.Shipped);
		
		orderRepository.save(order);
	}


	/**
	 * service method to change isDelivery boolean of order with orderId
	 * @param orderId
	 * @param isDelivery
	 */
	@Transactional
	public void editIsDelivery(int orderId, boolean isDelivery){
		Order order = orderRepository.findOrderByOrderID(orderId);
		order.setShipmentMethodIsDelivery(isDelivery);
		order = orderRepository.save(order);
	}


	/**
	 * service method to change isDelivery boolean of order
	 * @param order
	 * @param isDelivery
	 */
	@Transactional
	public void editIsDelivery(Order order, boolean isDelivery){
		order.setShipmentMethodIsDelivery(isDelivery);
		order = orderRepository.save(order);
	}

	
	/**
	 * iterate through all artwork associated with an order and sum up all their costs. This sum is used to update the order.totalAmount attribute
	 * @param order order whose total will be updated
	 */
	private void updateTotal(Order order) {
		List<Artwork> arts = toList(order.getArtwork());
		if (arts.isEmpty()) {
			order.setTotalAmount(0.0);
			return;
		}
		double total = 0;
		
		for (Artwork a : arts) {
			total += a.getWorth();
		}
		order.setTotalAmount(total);
	}


	
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		
		if (iterable != null) {
			for (T t : iterable) {
				resultList.add(t);
			}
		}
		return resultList;
	}
	
}
