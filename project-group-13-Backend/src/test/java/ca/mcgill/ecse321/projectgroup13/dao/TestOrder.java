package ca.mcgill.ecse321.projectgroup13.dao;

import ca.mcgill.ecse321.projectgroup13.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration
public class

/**
 *  The TestOrder class implements JUnit for reading and writing orders to the database
 *
 */


TestOrder {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private OrderRepository orderRepository;
    
    /**
     *  Deletes all information from cartRepository and
     *  userRepository
     */
 
    @BeforeEach
    @AfterEach
    public void clearDatabase() {
    	userRepository.deleteAll();
    	cartRepository.deleteAll();
    	orderRepository.deleteAll();
    }

  
    
    /**
     * Creates instances of address, order and cart
     * populates them with test information, saves them to the database and then checks that 
     * the order was saved to the database properly.
     */
    
    @Test
    public void testPersistAndLoadAddress() {
    	//need instances of these classes
	    User user = new User();
	    Cart cart = new Cart();
	    Order order = new Order();
	    
	    //parameters for user
	    String username = "testUser20";
	    String password = "passW0rd";
	    String email = "me@home.com";
	    String profilePicURL = "//yes.com/img.jpg";
	    Set<Address> addrs= new HashSet<>();
	    Set<Artwork> artworks= new HashSet<>();
	    Set<Order> orders= new HashSet<>();
	    	orders.add(order);
	    	
	    //parameters for cart
	    double totalCost = 100.1;
	    Set<Artwork> artworksInCart = new HashSet<>();
	    
	    //parameters for order
	    //Integer orderID = "testORDERID".hashCode();
	    double totalAmount = 10000.3;
	    OrderStatus orderStatus = OrderStatus.PaymentPending;
	    Set<Artwork> OrderArtworks = new HashSet<>();
	    
	    //set user parameters
	    user.setUsername(username);
	    user.setPassword(password);
	    user.setEmail(email);
	    user.setProfilePictureURL(profilePicURL);
	    user.setCart(cart);
	    user.setAddress(addrs);
	    //user.setOrder(orders);
	    user.setArtwork(artworks);
	    
	    
	    //set cart parameters
	    //cart.setCartID(cartID);
	    cart.setTotalCost(totalCost);
	    cart.setUser(user);
	    cart.setArtwork(artworksInCart);
	    
	    //set order parameters
	    order.setArtwork(OrderArtworks);
	    //order.setOrderID(orderID);
	    order.setOrderStatus(orderStatus);
	    order.setPayment(null);
	    order.setShipment(null);
	    order.setTotalAmount(totalAmount);
	    order.setUser(user);
	    
	    //save instances to database 
	    user = userRepository.save(user);
	    //cart = cartRepository.save(cart);
	    order = orderRepository.save(order);
	    
        //restore address instance from database
        Order orderPersisted = orderRepository.findOrderByOrderID(order.getOrderID());

        //assert if instance retrieved from database equals the original
        assertEquals(order.getOrderID(), orderPersisted.getOrderID());
    }
}