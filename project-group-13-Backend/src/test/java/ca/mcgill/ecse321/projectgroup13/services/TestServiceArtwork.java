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

@ExtendWith(MockitoExtension.class)
public class TestServiceArtwork {
	
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
	            Artwork artwork = new Artwork();
	            return artwork;
	        });
		 
		 lenient().when(artworkRepository.save(any(Artwork.class))).thenAnswer((InvocationOnMock invocation) -> {
	            Artwork artwork = new Artwork();
	            return artwork;
	        });
	 
		 
	 }
	 
	 
	 
	 @Test
	 public void testNullWorthCreateArtwork() {
		 Artwork artwork=null;
		 String message;
		 try {
			 //TODO
			 artwork = artworkService.createArtwork();
		 } catch (illegalArgumentException e) {
			 message = e.getMessage();
		 }
		 assertNull(artwork);
		 assertEquals(message, "error: no worth added to artwork");
	 }
	 
	 @Test
	 public void testNullNameCreateArtwork() {
		 
	 }
	 
	 @Test
	 public void testIsOnPremiseCreateArtwork() {
		 
	 }
	 
	 @Test
	 public void testAddArtist() {
		 
	 }
	 
	 
	 
	 
	
}
