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
import ca.mcgill.ecse321.projectgroup13.dto.LoginDto;
import ca.mcgill.ecse321.projectgroup13.dto.UserDto;
import ca.mcgill.ecse321.projectgroup13.model.*;
import ca.mcgill.ecse321.projectgroup13.services.exception.RegistrationException;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import java.sql.Date;
import java.sql.Time;

@ExtendWith(MockitoExtension.class)
public class TestServiceUser {
	
	private static final String USERNAME = "person1";
	private static final String USER_PASSWORD= "Thatguy123#";
	private static final String USER_EMAIL= "person1@gmail.com";
	
	private static final String USERNAME2 = "person2";
	private static final String USER_PASSWORD2= "Thatgirl123#";
	private static final String USER_EMAIL2= "person2@gmail.com";
	private String error = "";
	private boolean success = false;
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
			} else if (invocation.getArgument(0).equals(USERNAME2)){
				User user = new User();
				user.setUsername(USERNAME2);
				user.setEmail(USER_EMAIL2);
				user.setPassword(USER_PASSWORD2);
				return user;
			} else {
				return null;
			}
		});
		lenient().when(userRepository.findUserByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(USER_EMAIL)) {
				User user = new User();
				user.setUsername(USERNAME);
				user.setEmail(USER_EMAIL);
				user.setPassword(USER_PASSWORD);
				return user;
			} else if (invocation.getArgument(0).equals(USER_EMAIL2)){
				User user = new User();
				user.setUsername(USERNAME2);
				user.setEmail(USER_EMAIL2);
				user.setPassword(USER_PASSWORD2);
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
        lenient().when(userRepository.findUserByEmail(any(String.class))).thenAnswer(returnParameterAsAnswer);
        
	}
	
	@Test
	public void testValidRegistration() {
		User newUser = null; 
		try {
			newUser = userService.createUser("DOGGYTHEDOUG",USER_EMAIL2,USER_PASSWORD2);
		} catch (RegistrationException e) {
			e.printStackTrace();
		}
		
		assertNotNull(newUser);
		assertEquals(newUser.getUsername(), "DOGGYTHEDOUG");
		assertEquals(newUser.getPassword(), USER_PASSWORD2);
	}
	
	
	@Test
	public void testCreateWithInvalidUsername() {
		User newUser = null; 
		try {
			newUser = userService.createUser(USERNAME,USER_PASSWORD2,USER_EMAIL2);
		} catch (RegistrationException e) {
			assertNull(newUser);
			assertEquals( "invalid username" , e.getMessage());
		}
	}
	
	@Test
	
	public void testCreateWithEmptyPassword() {
		User newUser = null; 
		try {
			newUser = userService.createUser("DOGGYMAN",USER_EMAIL2,"");
		} catch (RegistrationException e) {
			assertNull(newUser);
			assertEquals("invalid password", e.getMessage());
		}
	}
	@Test
	public void testCreateNoUsername() {
		User newUser = null; 
		try {
			newUser = userService.createUser("",USER_EMAIL2,USER_PASSWORD2);
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
			newUser = userService.createUser("ANOTHERTEST",email,USER_PASSWORD2);
		} catch (RegistrationException e) {
			assertNull(newUser);
			assertEquals("invalid email", e.getMessage());
		}
	}
	@Test
	public void testDeleteUser() {
		User user = null;
		error = null;
		try {
			
			userService.deleteUser(USERNAME);
		}catch (Exception e) {
			error = e.getMessage();
			System.out.println(error);
		}
		assertEquals(error,null);		
		
	}
	@Test
	public void testGetUserByNullUsername() {
		User user = null;
		try {
			user = userService.getUserByUsername(null);
		}catch (Exception e) {
			error = e.getMessage();
		}
		assertEquals(error, "invalid username");
		assertNull(user);
	}
	@Test
	public void testEditEmail() {
		User user = null;
		try {
			user = userService.editEmail(USERNAME, "doma@jakom.com");
		}catch (Exception e) {
			error = e.getMessage();
		}
		assertNotNull(user);
		assertEquals(user.getEmail(),"doma@jakom.com");	
	}
	
	@Test
	public void testLoginSuccess() {
		LoginDto dto = new LoginDto();
		dto.setUsername(USERNAME);
		dto.setPassword(USER_PASSWORD);
		String token = "";
		try {
			
			token = userService.login(dto);
		}catch (Exception e) {
			error = e.getMessage();
			System.out.println(error);
		}
		assertNotEquals(token,"");
		
	}
	
	@Test
	public void testLoginInvalidPassword() {
		LoginDto dto = new LoginDto();
		dto.setUsername(USERNAME);
		dto.setPassword(USER_PASSWORD2);
		String token = "";
		try {
			
			token = userService.login(dto);
		}catch (Exception e) {
			error = e.getMessage();
			assertEquals(error, "invalid password");
		}
		assertEquals(token,"");
		
	}
	@Test
	public void testEditBio() {
		String bio = "I am a busy artist that programs and writes readable tests";
		User user = null;
		try {
			
			user = userService.editBio(USERNAME, bio);
		}catch (Exception e) {
			error = e.getMessage();
			System.out.println(error);
		}
		assertEquals(user.getBio(),bio);
		
	}
	
	@Test
	public void testInvalidEditBio() {
		String bio = "I am a busy artist that programs and writes readable tests";
		User user = null;
		try {
			
			user = userService.editBio("megapipi123", bio);
		}catch (Exception e) {
			error = e.getMessage();
			System.out.println(error);
		}
		assertEquals(error,"invalid username");
		assertNull(user);
		
	}
	
	@Test
	public void testEditUrl() {
		String url = "www.google.ca/coolPeople";
		User user = null;
		try {
			
			user = userService.editProfilePictureUrl(USERNAME, url);
		}catch (Exception e) {
			error = e.getMessage();
			System.out.println(error);
		}
		assertEquals(user.getProfilePictureURL(),url);
		
	}
	@Test
	public void testInvalidResetPassword() {
		User user = null;
		try {
			user = userService.resetPassword("doggyboy");
		}catch (Exception e) {
			error = e.getMessage();
			System.out.println(error);
		}
		assertEquals(error,"invalid username");
		assertNull(user);
	}
	
	@Test
	public void testResetPassword() {
		User user = null;
		try {
			user = userService.resetPassword(USERNAME);
		}catch (Exception e) {
			error = e.getMessage();
			System.out.println(error);
		}
		assertNotNull(user);
	}
	
	
	@Test
	public void testChangePasswordToInvalid() {
		String bio = "I am a busy artist that programs and writes readable tests";
		User user = null;
		try {
			
			user = userService.editBio("megapipi123", bio);
		}catch (Exception e) {
			error = e.getMessage();
			System.out.println(error);
		}
		assertEquals(error,"invalid username");
		assertNull(user);
		
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

