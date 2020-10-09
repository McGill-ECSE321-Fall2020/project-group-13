package ca.mcgill.ecse321.projectgroup13.doa;

import ca.mcgill.ecse321.projectgroup13.dao.AddressRepository;
import ca.mcgill.ecse321.projectgroup13.dao.CartRepository;
import ca.mcgill.ecse321.projectgroup13.dao.UserRepository;
import ca.mcgill.ecse321.projectgroup13.model.Address;
import ca.mcgill.ecse321.projectgroup13.model.User;
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
    //@AfterEach
    public void clearDatabase() {
        userRepository.deleteAll();
        addressRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadUser() {
        System.out.println("test1");
        String username = "TestUser";
        String cartID = "123";

        // First example for object save/load
        User user = new User();
        user.setUsername(username);
        userRepository.save(user);


        Address address = new Address();
        address.setCity("Montreal");
        address.setCountry("Canada");
        address.setStreetAddress1("3302 St-Catherine");
        address.setAddressID("3732St-Catherine");
        address.setUser(user);
        Set<Address> addrs= new HashSet<>();
        addrs.add(address);
        user.setAddress(addrs);
        addressRepository.save(address);

        //END OF ADDED CODE

        //userRepository.save(user);

        user = null;
        //cart = null;

        // user = userRepository.findUserByUsername(username);
        user = userRepository.findUserByUsername(username);
        //cart = cartRepository.findCartByCartID(cartID);
//        assertNotNull(user);
//        assertNotNull(user2);
//        assertEquals(username, user.getUsername());
//        assertEquals(username2, user2.getUsername());
//      
        address=null;
        Address theFkingAddress = addressRepository.findAddressByAddressID("3732St-Catherine");
        assertEquals(theFkingAddress.getCity(),"Montreal");
    }
}