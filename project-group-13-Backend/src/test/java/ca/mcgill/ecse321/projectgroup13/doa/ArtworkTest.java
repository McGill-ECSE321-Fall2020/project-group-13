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
        artworkRepository.deleteAll();
    }
    
    
    
    //read and write 
    
    //Here we populate the database
    
    public void initializeDatabase() {
    	//    Artwork<---User-->Address
    	//create address
    	
    	//first create User
    	User artist = new User();
    	artist.setEmail("JCesar@RussianBrides.com");
    	artist.setUsername("Julius Cesar Arnouk");
    	userRepository.save(artist);
    	//create Artwork and associate it with User
    	
    	Artwork artwork = new Artwork();
    	artwork.setTitle("Beauty");
    	artwork.setArtworkID("Beauty");
    	Set<User> artists = new HashSet<User>();
    	
    	
    	//create Address and associate it with User
    	Address address = new Address();
    	address.setCity("Montreal");
    	address.setCountry("Canada");
    	address.setStreetAddress1("3302 St-Catherine");
    	address.setAddressID("3732St-Catherine");
    	address.setUser(artist);
    	
    	
    		//create hashset
    	Set<Address> addresss = new HashSet<>();
    	addresss.add(address);
    	artist.setAddress(addresss);
   
   
    	//artists.add(artist);
    	addressRepository.save(address);
    	artworkRepository.save(artwork);
    	artwork.setArtist(artists);
    	
    	
    	
    }
    
    //Navigate associations to the Artwork's artist's city 
    @Test
    public void findArtistCityTest() {
    	initializeDatabase();
    	Set<String> artists = new HashSet<>();
    	artists.add("Julius Cesar Arnouk");
    	//Set<Artwork> artwork = artworkRepository.findArtworkByArtist(artists);
    	//assertEquals(artwork.getTitle(),"Beauty");
    	
    	
    }
    
    
    }
    
