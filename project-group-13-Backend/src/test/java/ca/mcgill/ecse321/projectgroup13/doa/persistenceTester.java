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
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration
public class persistenceTester {
    @Autowired
    private ArtworkRepository artworkRepository;
    // this is to clear database prior to every run
    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        artworkRepository.deleteAll();
    }


    public Artwork createArtwork() {
    	
   
        Artwork artwork = new Artwork();
        System.out.println("TESTED BABY1");
        artwork.setArtworkSold(true);
    
        artwork.setTitle("beauty");
        artwork.setArtworkID("cesar_baby");
        System.out.println("TESTED BABY1.5");
        artworkRepository.saveAndFlush(artwork);
        System.out.println("TESTED BABY2");
        return artwork;
    }


    @Test
    public void testPersistAndLoadUser() {
        //parameters for users
    	System.out.println("TESTED BABY");
    	createArtwork();
        Artwork artwork = artworkRepository.findArtworkByArtworkID("cesar_baby");
        //asserts if everything can be retrieved from database
       
        assertEquals(artwork.isArtworkSold(), true);
       
        
    }
    

}