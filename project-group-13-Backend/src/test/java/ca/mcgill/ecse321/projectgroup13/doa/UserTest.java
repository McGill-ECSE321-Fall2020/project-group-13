package ca.mcgill.ecse321.projectgroup13.doa;

import ca.mcgill.ecse321.projectgroup13.dao.AddressRepository;
import ca.mcgill.ecse321.projectgroup13.dao.CartRepository;
import ca.mcgill.ecse321.projectgroup13.dao.UserRepository;
import ca.mcgill.ecse321.projectgroup13.model.Address;
import ca.mcgill.ecse321.projectgroup13.model.Cart;
import ca.mcgill.ecse321.projectgroup13.model.User;

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

public class UserTest {
    @Autowired
    private UserRepository userRepository;
    //Remove this
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private CartRepository cartRepository;
    //Remove ends here

    // this is to clear database prior to every run
    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        userRepository.deleteAll();
        addressRepository.deleteAll();
    }
    
    public void initDatabase() {
    	System.out.println("test1");
        String username = "TestUser";
        String cartID = "123";

        // First example for object save/load
        User user = new User();
        user.setUsername(username);
       // userRepository.save(user);
        //create address
//        Address address = new Address();
//        address.setCity("Montreal");
//        address.setCountry("Canada");
//        address.setStreetAddress1("3302 St-Catherine");
//        address.setAddressID("3732St-Catherine");
//        address.setUser(user);
//        Set<Address> addrs= new HashSet<>();
//        addrs.add(address);
//        user.setAddress(addrs);
//        addressRepository.save(address);
//        //create the cart
        Cart cart = new Cart();
        cart.setCartID(cartID);
        cart.setUser(user);
        user.setCart(cart);
        userRepository.save(user);
        cartRepository.save(cart);
    }
    
    @Test
    public void testPersistAndLoadUser() {
    	initDatabase();
    	Cart cart = cartRepository.findCartByCartID("123");
    	
        User user = userRepository.findUserByCart(cart); 
        
        assertEquals(user.getUsername(),"TestUser");
    }
    
   
    
}