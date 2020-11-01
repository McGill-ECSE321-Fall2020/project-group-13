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
public class TestCartService {
	
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
			return null;
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
	public void testAddMultipleInvalid() {
		
	}
	
	
	@Test
	public void testAddSingleInvalid() {
		
	}
	
	@Test
	
	public void testAddSingleValid() {
		
	}
	@Test
	public void testAddMultipleValid() {
		
	}
	
	@Test
	public void testRemoveMultipleInvalid() {
		
		
	}
	
	@Test
	public void testDeleteCart() {
		
		
	}
	
	
		
		
	
	
}

