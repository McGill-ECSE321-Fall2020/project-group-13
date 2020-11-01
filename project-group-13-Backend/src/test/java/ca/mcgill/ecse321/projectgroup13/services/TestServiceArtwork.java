package ca.mcgill.ecse321.projectgroup13.services;

import ca.mcgill.ecse321.projectgroup13.dao.*;
import ca.mcgill.ecse321.projectgroup13.dto.ArtworkDto;
import ca.mcgill.ecse321.projectgroup13.dto.UserDto;
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
	 
	 
	 
	 @Test
	 public void testCreateValidArtwork() {
		 ArtworkDto artworkDto = new ArtworkDto();
		 UserDto userDto = new UserDto();
		 userDto.setUsername(USERNAME);
		 Set<UserDto> artDto= new HashSet<>();
		 artDto.add(userDto);
		 artworkDto.setArtist(artDto);
		 artworkDto.setTitle("done with it");
		 artworkDto.setWorth(23.99);
		 
		 Artwork artwork = null;
		try {
		 artwork=artworkService.createArtwork(artworkDto);
		}catch(illegalArgumentException e) {
			e.getMessage();
		}
		assertNotNull(artwork);
		assertEquals(artwork.getTitle(), artworkDto.getTitle());
		 
	 }
	 
	 @Test
	 public void testSpaceNameCreateArtwork() {
		 Artwork artwork = null;
		 ArrayList<String> artists = new ArrayList<String>();
		 artists.add(USERNAME);
		 try {
			 artwork = artworkService.createArtwork(" ", artists, 100.00);
			 
		 }catch(illegalArgumentException e) {
			
		 }
		 assertNull(artwork);
	 }
	 
	 @Test
	 public void testZeroWorthCreateArtwork() {
		 Artwork artwork = null;
		 ArrayList<String> artists = new ArrayList<String>();
		 artists.add(USERNAME);
		 String error ="";
		 try {
			 artwork = artworkService.createArtwork(" ", artists, 0.0);
			 
		 }catch(illegalArgumentException e) {
			error=e.getLocalizedMessage();
		 }
		 assertEquals(error , "invalid worth");
		 assertNull(artwork);
	 }
	 
	 @Test
	 public void testIsOnPremiseCreateArtwork() {
		 
	 }
	 
	 @Test
	 public void testGetUserOfAddress() {
		 
	 }
	 
	 @Test
	 public void testAddValidArtist() {
		 
		 
	 }
	 
	 @Test
	 public void testAddInvalidArtist() {
		 
	 }
	 
	 @Test
	 public void testArtworkWithInvalidArtist() {
		 Artwork artwork = null;
		 String error ="";
		 ArrayList<String> artists = new ArrayList<String>();
		 artists.add("InvisibleBoy");
		 try {
			 artwork = artworkService.createArtwork(" ", artists, 100.00);
			 
		 }catch(illegalArgumentException e) {
			error = e.getMessage();
		 }
		 assertEquals(error , "invalid user");
		 assertNull(artwork);
	 }
	 
	 @Test
	 public void testDeleteExistingArtwork()  {
		 ArtworkDto artworkDto = new ArtworkDto();
		 UserDto userDto = new UserDto();
		 userDto.setUsername(USERNAME);
		 Set<UserDto> artDto= new HashSet<>();
		 artDto.add(userDto);
		 artworkDto.setArtist(artDto);
		 artworkDto.setTitle("done with it");
		 artworkDto.setWorth(23.99);
		 Artwork checker=null;
		 try {
			 Artwork artwork=artworkService.createArtwork(artworkDto);
			 checker = artworkRepository.findArtworkByArtworkID(ARTWORKID);
		 } catch (illegalArgumentException e) {
			 assertNull(checker);
		 }
		 
		 
		 artworkService.deleteArtworkById(ARTWORKID);
		 assertNull(artworkRepository.findArtworkByArtworkID(ARTWORKID));
	 }
	 
	 public static <T> List<T> toList(Iterable<T> iterable) {
	        List<T> lst = new ArrayList<T>();
	        for (T t : iterable) {
	            lst.add(t);
	        }
	        
	        return lst;
	    }
	 
	
}
