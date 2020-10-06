package ca.mcgill.ecse321.projectgroup13.doa;
import ca.mcgill.ecse321.projectgroup13.dao.ArtworkRepository;
import ca.mcgill.ecse321.projectgroup13.dao.UserRepository;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration
public class persistenceTester {
    @Autowired
    private ArtworkRepository artworkRepository;
    @Autowired
    private UserRepository userRepository;


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
        artwork.setArtworkID("cesar_baby");
        artworkRepository.saveAndFlush(artwork);
        return artwork;
    }


    @Test
    public void testPersistAndLoadArtwork() {
        //parameters for users
    	createArtwork();
        Artwork artwork = artworkRepository.findArtworkByArtworkID("cesar_baby");
        //asserts if everything can be retrieved from database
        assertEquals(artwork.isArtworkSold(), true);
       
        
    }

    @Test
    public void testPersistAndLoadUser() {

        String username = "TestUser";
        // First example for object save/load
        User user = new User();
        // First example for attribute save/load
        user.setUsername(username);
        userRepository.save(user);

        user = null;

        user = userRepository.findUserByUsername(username);
        assertNotNull(user);
        assertEquals(username, user.getUsername());

    }
    

}