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
    	Address address = new Address();
    	address.setCity("Montreal");
    	address.setCountry("Canada");
    	address.setStreetAddress1("3302 St-Catherine");
    	//create User
    	User artist = new User();
    	
    	artist.setUsername("Julius Cesar Arnouk");
    	artist.setEmail("JCesar@RussianBrides.com");
    	Set<Address> addresss = new HashSet<>();
    	addresss.add(address);
    	artist.setAddress(addresss);
    	//create Artwork
    	Artwork artwork = new Artwork();
    	artwork.setTitle("Beauty");
    	artwork.setArtworkID("Beauty");
    	Set<User> artists = new HashSet<>();
    	artists.add(artist);
    	artwork.setArtist(artists);
    }
    
    //Navigate associations to the Artwork's artist's city 
    @Test
    public void findArtistCityTest() {
    	initializeDatabase();
    	Artwork artwork = artworkRepository.findArtworkByArtist("Julius Cesar Arnouk");
    	assertEquals(artwork.getTitle(),"Beauty");
    	
    	
    }
    
    
    }
    
