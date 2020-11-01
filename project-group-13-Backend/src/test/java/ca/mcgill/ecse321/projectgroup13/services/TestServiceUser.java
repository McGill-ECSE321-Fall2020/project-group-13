package ca.mcgill.ecse321.projectgroup13.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.projectgroup13.dao.*;
import ca.mcgill.ecse321.projectgroup13.dao.UserRepository;
import ca.mcgill.ecse321.projectgroup13.dto.UserDto;
import ca.mcgill.ecse321.projectgroup13.model.*;
import ca.mcgill.ecse321.projectgroup13.services.exception.RegistrationException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class TestServiceUser {
	
	private static final String USERNAME = "person1";
	private static final String USER_PASSWORD= "Thatguy123#";
	private static final String USER_EMAIL= "person1@gmail.com";
	
	private static final String USERNAME2 = "person2";
	private static final String USER_PASSWORD2= "Thatgirl123#";
	private static final String USER_EMAIL2= "person2@gmail.com";
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private ArtworkRepository artworkRepository;
	
	@InjectMocks
	private UserService userService;
	
	@BeforeEach
	public void setMockOutput() {
		MockitoAnnotations.initMocks(this);
		lenient().when(userRepository.findUserByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(USERNAME)) {
				User user = new User();
				user.setUsername(USERNAME);
				user.setEmail(USER_EMAIL);
				user.setPassword(USER_PASSWORD);
				return user;
			} else {
				return null;
			}
		});
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		
		lenient().when(userRepository.save(any(User.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(artworkRepository.save(any(Artwork.class))).thenAnswer(returnParameterAsAnswer);
	}
	
	@Test
	public void testValidRegistration() {
		User newUser = null; 
		try {
			newUser = userService.createUser(USERNAME2, USER_PASSWORD2, USER_EMAIL2);
		} catch (RegistrationException e) {
			e.printStackTrace();
		}
		
		assertNotNull(newUser);
		assertEquals(USER_EMAIL2,newUser.getEmail());
		assertEquals(USERNAME2,newUser.getUsername());
		assertEquals(USER_PASSWORD2,newUser.getPassword());
	}
	
	
	@Test
	public void testCreateWithInvalidUsername() {

		User newUser = null; 
		try {
			newUser = userService.createUser(USERNAME, USER_PASSWORD2, USER_EMAIL2);
		} catch (RegistrationException e) {
			assertNull(newUser);
			assertEquals( "invalid username" , e.getMessage());
		}
	}
	
	@Test
	
	public void testCreateWithEmptyPassword() {
		User newUser = null; 
		try {
			newUser = userService.createUser(USERNAME2, USER_EMAIL2, " ");
		} catch (RegistrationException e) {
			assertNull(newUser);
			assertEquals("invalid password", e.getMessage());
		}
	}
	@Test
	public void testCreateNoUsername() {
	
		User newUser = null; 
		try {
			newUser = userService.createUser("", USER_EMAIL2,USER_PASSWORD2);
		} catch (RegistrationException e) {
			assertNull(newUser);
			assertEquals("invalid username", e.getMessage());
		}
	}
	@Test	
	public void testCreateInvalidEmail() {
		
		String email = "jokesOnYou";
		User newUser = null; 
		try {
			newUser = userService.createUser(USERNAME2, email, USER_PASSWORD2);
		} catch (RegistrationException e) {
			assertNull(newUser);
			assertEquals("invalid email", e.getMessage());
		}
	}
	
	@Test 
	public void testGetUserByUsername() {
		User user = null;
		try {
			user = userService.getUserByUsername(USERNAME);
		} catch (IllegalArgumentException e) {
			
		}
		assertTrue(user!=null);
		assertTrue(user.getEmail().contentEquals(USER_EMAIL));
	}
	
	@Test 
	public void testInvalidGetUserByUsername() {
		try {
			userService.getUserByUsername("");
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), "invalid username");
		}
		
	}
	
	
	@Test 
	public void testInvalidEditEmail() {
		try {
			userService.editEmail(USERNAME, "thisaintyomamasemail");
		} catch (IllegalArgumentException | RegistrationException e) {
			assertEquals(e.getMessage(), "invalid email");
		}
		
	}
	
	@Test 
	public void testValidEditEmail() {
		assertEquals(userRepository.findUserByUsername(USERNAME).getEmail(), USER_EMAIL);
		try {
			userService.editEmail(USERNAME, USER_EMAIL2);
		} catch (IllegalArgumentException | RegistrationException e) {
		
		}
		assertEquals(userRepository.findUserByUsername(USERNAME).getEmail(), USER_EMAIL2);
		
	}
	

	
	@Test
	public void testResetPasswordGenerated() {
		
		try {
			userService.getUserByUsername("");
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), "invalid username");
		}
	}
	
	@Test
	public void testPasswordNoNumber() {
		
		try {
			userService.getUserByUsername("");
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), "invalid username");
		}
	}
	
	@Test
	public void testPasswordNoAlphabet() {
		
		try {
			userService.getUserByUsername("");
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), "invalid username");
		}
	}
	
	@Test
	public void testDeleteInvalidUser() {
		
		try {
			userService.getUserByUsername("");
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), "invalid username");
		}
	}
	
	@Test
	public void testEditDescription() {
		try {
			userService.getUserByUsername("");
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), "invalid username");
		}
		
	}
		
		
	
	
}

