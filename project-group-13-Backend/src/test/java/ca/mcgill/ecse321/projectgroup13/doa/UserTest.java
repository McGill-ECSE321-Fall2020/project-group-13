package ca.mcgill.ecse321.projectgroup13.doa;
import ca.mcgill.ecse321.projectgroup13.dao.AddressRepository;
import ca.mcgill.ecse321.projectgroup13.dao.UserRepository;
import ca.mcgill.ecse321.projectgroup13.model.Address;
import ca.mcgill.ecse321.projectgroup13.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration

public class UserTest {
    @Autowired
    private UserRepository userRepository;
//Remove this
   @Autowired
   private AddressRepository addressRepository;
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
        String username = "TestUser";
        String username2 = "TestUser2";
        // First example for object save/load
        User user = new User();
        User user2 = new User();
        
        // First example for attribute save/load
        user.setUsername(username);
        user2.setUsername(username2);
        userRepository.save(user2);
        //ADDED CODE INCLUDING COMMENT
        Address address = new Address();
    	address.setCity("Montreal");
    	address.setCountry("Canada");
    	
    	address.setStreetAddress1("3302 St-Catherine");
    	address.setAddressID("3732St-Catherine");
    	address.setUser(user2);
    	Set<Address> addrs= new HashSet<>();
    	//creating a hashset
    	addrs.add(address);
    	user2.setAddress(addrs);
    	addressRepository.save(address);
        //END OF ADDED CODE
    	
    //    userRepository.save(user);
       

        user = null;
        user2 = null;

       // user = userRepository.findUserByUsername(username);
        user2 = userRepository.findUserByUsername(username2);
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