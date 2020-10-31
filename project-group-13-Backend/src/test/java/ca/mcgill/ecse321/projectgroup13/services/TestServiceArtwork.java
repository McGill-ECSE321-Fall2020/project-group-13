package ca.mcgill.ecse321.projectgroup13.services;

import ca.mcgill.ecse321.projectgroup13.dao.*;
import ca.mcgill.ecse321.projectgroup13.services.exception.*;
import ca.mcgill.ecse321.projectgroup13.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class TestServiceArtwork {
	
	private static final String USERNAME = "person1";
	private static final String USER_PASSWORD= "Thatguy123#";
	private static final String USER_EMAIL= "person1@gmail.com";
	private static final Integer ARTWORKID = 12324324 ;
	private static final String TITLE = "Beauty of the times";
	private static final Integer ARTWORKID2 = 12312324 ;
	private static final String TITLE2 = "Beauty of the times";
	
	
	 @Mock
	    private UserRepository userRepository;
	 
	 @Mock
	    private ArtworkRepository artworkRepository;
	 
	 @Mock
	 	private CartRepository cartRepository;
	 
	 @Mock 
	 	private PaymentRepository paymentRepository;
	 
	 @InjectMocks
	 	private ArtworkService artworkService;
	 
	 
	 @BeforeEach
	 public void setMockOutput() {
		 MockitoAnnotations.initMocks(this);
		 lenient().when(artworkRepository.save(any(Artwork.class))).thenAnswer((InvocationOnMock invocation) -> {
			 	User user = new User();
				user.setUsername(USERNAME);
				user.setEmail(USER_EMAIL);
				user.setPassword(USER_PASSWORD);
				return user;
	        });
		 
		 lenient().when(artworkRepository.save(any(Artwork.class))).thenAnswer((InvocationOnMock invocation) -> {
	            Artwork artwork = new Artwork();
	            User user = userRepository.findUserByUsername(USERNAME);
	            
	            Set<Artwork> artworks = new HashSet<>();
	            Set<User> users = new HashSet<>();
	            artworks.add(artwork);
	            
	            user.setArtwork(artworks);
	            artwork.setArtist(users);
	            user.setArtwork(artworks);
	            artwork.setTitle(TITLE);
	            artwork.setArtworkID(ARTWORKID);
	            return artwork;
	        });
		 
		 
		 
	 }
	 
	 
	 
//	 @Test
//	 public void testNullWorthCreateArtwork() {
//		 Artwork artwork=null;
//		 String message;
//		 try {
//			 //TODO
//			// artwork = artworkService.createArtwork();
//		 } catch (illegalArgumentException e) {
//			 message = e.getMessage();
//		 }
//		 assertNull(artwork);
//		 assertEquals(message, "error: no worth added to artwork");
//	 }
	 
	 @Test
	 public void testNullNameCreateArtwork() {
		 
	 }
	 
	 @Test
	 public void testIsOnPremiseCreateArtwork() {
		 
	 }
	 
	 @Test
	 public void testAddArtist() {
		 
	 }
	 
	 
	 public static <T> List<T> toList(Iterable<T> iterable) {
	        List<T> lst = new ArrayList<T>();
	        for (T t : iterable) {
	            lst.add(t);
	        }
	        
	        return lst;
	    }
	 
	
}
