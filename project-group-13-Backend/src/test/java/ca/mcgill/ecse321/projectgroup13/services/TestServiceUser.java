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
		UserDto userDto = new UserDto(); 
		userDto.setUsername(USERNAME2);
		userDto.setPassword(USER_PASSWORD2);
		userDto.setEmail(USER_EMAIL2);
		User newUser = null; 
		try {
			newUser = userService.createUser(userDto);
		} catch (RegistrationException e) {
			e.printStackTrace();
		}
		
		assertNotNull(newUser);
		assertEquals(userDto.getEmail(),newUser.getEmail());
		assertEquals(userDto.getUsername(),newUser.getUsername());
		assertEquals(userDto.getPassword(),newUser.getPassword());
	}
	
	
	@Test
	public void testCreateWithInvalidUsername() {
		UserDto userDto = new UserDto(); 
		userDto.setUsername(USERNAME); //this is the username of the first user that's already been populated in the database
		userDto.setPassword(USER_PASSWORD2);
		userDto.setEmail(USER_EMAIL2);
		User newUser = null; 
		try {
			newUser = userService.createUser(userDto);
		} catch (RegistrationException e) {
			assertNull(newUser);
			assertEquals( "invalid username" , e.getMessage());
		}
	}
	
	@Test
	
	public void testCreateWithEmptyPassword() {
		UserDto userDto = new UserDto(); 
		userDto.setUsername(USERNAME2);
		userDto.setPassword(" ");
		userDto.setEmail(USER_EMAIL2);
		User newUser = null; 
		try {
			newUser = userService.createUser(userDto);
		} catch (RegistrationException e) {
			assertNull(newUser);
			assertEquals("invalid password", e.getMessage());
		}
	}
	@Test
	public void testCreateNoUsername() {
		UserDto userDto = new UserDto(); 
		userDto.setUsername("");
		userDto.setPassword(USER_PASSWORD2);
		userDto.setEmail(USER_EMAIL2);
		User newUser = null; 
		try {
			newUser = userService.createUser(userDto);
		} catch (RegistrationException e) {
			assertNull(newUser);
			assertEquals("invalid username", e.getMessage());
		}
	}
	@Test	
	public void testCreateInvalidEmail() {
		
		String email = "jokesOnYou";
		UserDto userDto = new UserDto(); 
		userDto.setUsername(USERNAME2);
		userDto.setPassword(USER_PASSWORD2);
		userDto.setEmail(email);
		User newUser = null; 
		try {
			newUser = userService.createUser(userDto);
		} catch (RegistrationException e) {
			assertNull(newUser);
			assertEquals("invalid email", e.getMessage());
		}
	}
	
	@Test
	public void testChangePasswordToInvalid() {
		
		
	}
	
	@Test
	public void testChangePasswordToEmpty() {
		
		
	}
	
	@Test
	public void testResetPasswordGenerated() {
		
		
	}
	
	@Test
	public void testPasswordNoNumber() {
		
		
	}
	
	@Test
	public void testPasswordNoAlphabet() {
		
		
	}
	
	@Test
	public void testDeleteInvalidUser() {
		
		
	}
	
	@Test
	public void testEditDescription() {
		
		
	}
		
		
	
	
}

