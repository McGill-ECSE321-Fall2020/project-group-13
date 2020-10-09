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

    
    // this is to clear database prior to every run
    @BeforeEach
    @AfterEach
    public void clearDatabase() {
    	addressRepository.deleteAll();
    	userRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadAddress() {
        //parameters for address
	    Address address = new Address();
	    String city = "city";
	    String country = "country";
	    String postalCode = "postalCode";
	    String province = "province";
	    String street1 = "street1";
	    String street2 = "street2";
	    User user = new User();
	    	user.setUsername("TestUser17");
	    Cart cart = new Cart();
	    System.out.println("1");
	    cart.setUser(user);
	    user.setCart(cart);
	    System.out.println("2");
	    
	    address.setCity(city);
	    address.setCountry(country);
	    address.setPostalCode(postalCode);
	    address.setProvince(province);
	    address.setStreetAddress1(street1);
	    address.setStreetAddress2(street2);
	    address.setUser(user);
	    System.out.println("3");
	    
	    userRepository.save(user);
	    
	    System.out.println("1");
        String ID = Integer.toString(address.hashCode());
        address.setAddressID(ID);
        System.out.println("2");
        addressRepository.save(address);
        
        
        System.out.println("3");
        Address addressPersisted = addressRepository.findAddressByAddressID(ID);
        //asserts if everything can be retrieved from database
        System.out.println(user);
        //System.out.println(addressPersisted.getUser());
        assertEquals(true, user.equals(addressPersisted.getUser()));
        //assertEquals(true, address.equals(addressPersisted));
    }
}