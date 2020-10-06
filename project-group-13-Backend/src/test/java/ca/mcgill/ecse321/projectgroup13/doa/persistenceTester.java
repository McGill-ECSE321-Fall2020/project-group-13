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
        artwork.setArtworkSold(true);
    
        artwork.setTitle("beauty");
        artworkRepository.save(artwork);
        return artwork;
    }


    @Test
    public void testPersistAndLoadUser() {
        //parameters for users
    	createArtwork();
        Artwork artwork = artworkRepository.findArtworkByTitle("beauty");
        //asserts if everything can be retrieved from database
        assertEquals(artwork.isArtworkSold(), true);
        System.out.println("TESTED BABY");
        
    }
    

}