package ca.mcgill.ecse321.projectgroup13.doa;
import ca.mcgill.ecse321.projectgroup13.model.*;
import ca.mcgill.ecse321.projectgroup13.dao.*;

import org.junit.jupiter.api.Test;
import org.apache.tomcat.util.buf.CharsetCache;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.sql.Time;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration
public class TestAddressPersistance {
    
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    
    // this is to clear database prior to every run
    @BeforeEach
    @AfterEach
    public void clearDatabase() {
    	addressRepository.deleteAll();
    	userRepository.deleteAll();
    	cartRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadAddress() {
    	
    	//need instances of these classes
	    Address address = new Address();
	    User user = new User();
	    Cart cart = new Cart();
	    
	    
	    //parameters for address
	    String city = "city";
	    String country = "country";
	    String postalCode = "postalCode";
	    String province = "province";
	    String street1 = "street1";
	    String street2 = "street2";
	    String addressID = "TESTaddressID";
	    
	    
	    //parameters for user
	    String username = "testUser20";
	    String password = "passW0rd";
	    String email = "me@home.com";
	    String profilePicURL = "//yes.com/img.jpg";
	    
	    
	    //parameters for cart
	    String cartID = "TESTcartID";
	    double totalCost = 100.1;
	    
	    //set address parameters
	    address.setCity(city);
	    address.setCountry(country);
	    address.setPostalCode(postalCode);
	    address.setProvince(province);
	    address.setStreetAddress1(street1);
	    address.setStreetAddress2(street2);
	    address.setAddressID(addressID);
	    address.setUser(user);
	    
	    //set user parameters
	    user.setUsername(username);
	    user.setPassword(password);
	    user.setEmail(email);
	    user.setProfilePictureURL(profilePicURL);
	    user.setCart(cart);
	    
	    //set cart parameters
	    cart.setCartID(cartID);
	    cart.setTotalCost(totalCost);
	    cart.setUser(user);
	    
	    
	    
	    System.out.println("1");
	    userRepository.save(user);
	    System.out.println("2");
	    cartRepository.save(cart);
	    System.out.println("3");
	    addressRepository.save(address);
	    System.out.println("4");

        
        
        Address addressPersisted = addressRepository.findAddressByAddressID(addressID);
        System.out.println("5");
        //asserts if everything can be retrieved from database
        System.out.println(user);
        //System.out.println(addressPersisted.getUser());
        assertEquals(true, user.equals(addressPersisted.getUser()));
        //assertEquals(true, address.equals(addressPersisted));
    }
}