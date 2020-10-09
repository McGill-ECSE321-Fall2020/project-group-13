package ca.mcgill.ecse321.projectgroup13.doa;
import ca.mcgill.ecse321.projectgroup13.model.*;
import ca.mcgill.ecse321.projectgroup13.dao.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration
public class ArtworkTest {
    @Autowired
    private ArtworkRepository artworkRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressRepository addressRepository;
    // this is to clear database prior to every run
    @BeforeEach
    @AfterEach
    public void clearDatabase() {
    	addressRepository.deleteAll();
    	artworkRepository.deleteAll();
    	userRepository.deleteAll();
    	
        
    }
    
    
    
    //read and write 
    
    //Here we populate the database
    
    public void initializeDatabase() {
    	//    Artwork<---User-->Address
    	//create address
    	
    	User artist = new User();
    	artist.setUsername("Julius Cesar Arnouk");
    	artist.setEmail("JCesar@RussianBrides.com");
    	userRepository.save(artist);
    	
    	Address address = new Address();
    	address.setUser(artist);
    	address.setCity("Montreal");
    	address.setCountry("Canada");
    	address.setStreetAddress1("3302 St-Catherine");
    	address.setAddressID("3732St-Catherine");
    	Set<Address> addresss = new HashSet<>();
    	addresss.add(address);
    	
    	//create Artwork
    	Artwork artwork = new Artwork();
    	artwork.setTitle("Beauty");
    	artwork.setArtworkID("Beauty");
    	Set<User> artists = new HashSet<User>();
    	
    	
    	
    	Set<User> artists = new HashSet<User>();
    	Set<Artwork> artworks = new HashSet<Artwork>();

    	
    	
    	artists.add(artist);
    	artworks.add(artwork);
    	System.out.println("ERROR DETECTOR PASSED");
    	addressRepository.save(address);
    	
    	artwork.setArtist(artists);
    	artist.setArtwork(artworks);
    	artworkRepository.save(artwork);
    	userRepository.save(artist);
    	
    	
    }
    

    @Test
    public void artworkTest() {
    	initializeDatabase();
    	//System.out.println(userRepository.findUserByUsername("Julius Cesar Arnouk"));
    	User artist = userRepository.findUserByUsername("Julius Cesar Arnouk");
    	Artwork artwork = artworkRepository.findArtworkByArtist(artist); //test search using foreign keys
    	assertNotNull(artwork); // test reference of object
    	assertEquals(artwork.getTitle(),"Beauty"); // test attribute of object
    	assertEquals("Beauty",artist.getArtwork().iterator().next().getArtworkID()); //test navigating association of object
    	
    	
    	
    }
    
    
    }
    
