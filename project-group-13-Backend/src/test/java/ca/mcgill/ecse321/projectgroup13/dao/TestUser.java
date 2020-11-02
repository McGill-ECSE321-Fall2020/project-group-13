package ca.mcgill.ecse321.projectgroup13.dao;

import ca.mcgill.ecse321.projectgroup13.model.Address;
import ca.mcgill.ecse321.projectgroup13.model.Artwork;
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


/**
 *  The TestUser class implements JUnit for reading and writing artwork to the database
 *  and also tests navigating the associations of artwork
 *
 */

public class TestUser {
    @Autowired
    private UserRepository userRepository;
    //Remove this
    @Autowired
    private ArtworkRepository artworkRepository;
    @Autowired
    private AddressRepository addressRepository;
    //Remove ends here
    

    /**
     *  Deletes all information from addressRepository, artoworkRepository and
     *  userRepository
     */
    
    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        addressRepository.deleteAll();
        artworkRepository.deleteAll();
        userRepository.deleteAll();
    }
    
    
    /**
     * Creates instances of address, user and art
     * populates them with test information, saves them to the database
     */
    
    public User initDatabase() {
    	//    Artwork<---User-->Address
    	//create address
    	
    	User artist = new User();
    	artist.setUsername("Julius Cesar Arnouk");
    	artist.setEmail("JCesar@RussianBrides.com");
    	artist.setOrder(new HashSet<>());
    	userRepository.save(artist);
    	
    	Address address = new Address();
    	address.setUser(artist);
    	address.setCity("Montreal");
    	address.setCountry("Canada");
    	address.setStreetAddress1("3302 St-Catherine");
    	//address.setAddressID("3732St-Catherine".hashCode());
    	Set<Address> addresss = new HashSet<>();
    	addresss.add(address);
    	artist.setAddress(addresss);
    	
    	//create Artwork
    	Artwork artwork = new Artwork();
    	artwork.setTitle("Beauty");
    	//artwork.setArtworkID("Beauty".hashCode());
    	Set<User> artists = new HashSet<User>();
    	
    	Set<Artwork> artworks = new HashSet<Artwork>();
    	
    	artists.add(artist);
    	artworks.add(artwork);
    	System.out.println("ERROR DETECTOR PASSED");
    	addressRepository.save(address);
    	
    	artwork.setArtist(artists);
    	artist.setArtwork(artworks);
    	artwork = artworkRepository.save(artwork);
    	artist = userRepository.save(artist);
    	return artist;
    	
    }
   
    
    /**
     * Tests that the user was successfully persisted to database, has the
     * same attributes and the same associations as the saved user
     */
    @Test
    public void testPersistAndLoadUser() {
    	User user = initDatabase();
    	//get artwork beauty from repo
//    	Artwork artwork=artworkRepository.findArtworkByArtworkID((Artwork [])user.getArtwork().toArray())[0]);
//    	Set<User> artists= artwork.getArtist();
//    	Iterator<User> iter = artists.iterator();
//        //assertEquals(user.getUsername(),"TestUser");
//    	User artist = iter.next();
//    	//now check if this username is same as the one it should be 
//    	String email = artist.getEmail();
    	User queryUser = userRepository.findUserByEmail(user.getEmail());
    	assertEquals(queryUser.getUsername(),"Julius Cesar Arnouk");
    	System.out.println(user);
    	System.out.println(queryUser);
  
    	assertEquals(true, user.equals(queryUser));
    }
    
}