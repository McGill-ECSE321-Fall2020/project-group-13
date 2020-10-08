package ca.mcgill.ecse321.projectgroup13.doa;

import ca.mcgill.ecse321.projectgroup13.dao.UserRepository;
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

public class UserTest {
    @Autowired
    private UserRepository userRepository;


    // this is to clear database prior to every run
    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        userRepository.deleteAll();
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
        userRepository.save(user);
        userRepository.save(user2);

        user = null;
        user2 = null;

        user = userRepository.findUserByUsername(username);
        user2 = userRepository.findUserByUsername(username2);
        assertNotNull(user);
        assertNotNull(user2);
        assertEquals(username, user.getUsername());
        assertEquals(username2, user2.getUsername());
    }
}