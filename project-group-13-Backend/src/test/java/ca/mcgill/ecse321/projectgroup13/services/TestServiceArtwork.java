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
import org.mockito.stubbing.Answer;

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
	
	private static final int invalidID = 404;

	private static final String USERNAME = "person1";
	private static final String USER_PASSWORD= "Thatguy123#";
	private static final String USER_EMAIL= "person1@gmail.com";
	
	private static final String USERNAME2 = "person2";
	private static final String USER_PASSWORD2= "Thatgirl123#";
	private static final String USER_EMAIL2= "person2@gmail.com";
	private static final String ARTWORK_TITLE= "BEAUTY";
	private static final String COUNTRY= "CANADA";
	private static final String CITY= "MONTREAL";
	private static final Integer ARTWORK_ID= 1234;
	private static final Integer ORDERID= 999;
	private static final Integer ADDRESS_ID= 111;

	private static final Integer SHIPMENTID = 200;
	private static final String[] ARTISTS = {USERNAME};
	private static final Double WORTH = 100.00;
	private static final String TITLE = "BEAUTY";
	

	
	@Mock
	private ArtworkRepository artworkRepository;
	@Mock
	private Order order;
	@Mock
	private User user;
	@Mock
	private Address address;
	@Mock 
	private OrderRepository orderRepo;
	@Mock
	private ShipmentRepository shipmentRepo;
	@Mock
	private UserRepository userRepo;
	@Mock
	private AddressRepository addressRepo;
	@Mock
	private ArtworkRepository artworkRepo;
	 
		@InjectMocks
		private ArtworkService artworkService;
		
		
		
		@BeforeEach
		public void setMockOutput() {
			MockitoAnnotations.initMocks(this);
			Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
				return invocation.getArgument(0);
			};
			lenient().when(addressRepo.save(any(Address.class))).thenAnswer(returnParameterAsAnswer);
			lenient().when(orderRepo.save(any(Order.class))).thenAnswer(returnParameterAsAnswer);
			lenient().when(shipmentRepo.save(any(Shipment.class))).thenAnswer(returnParameterAsAnswer);
			lenient().when(artworkRepo.save(any(Artwork.class))).thenAnswer(returnParameterAsAnswer);
			
			lenient().when(addressRepo.findAddressByAddressID(ADDRESS_ID)).thenAnswer((InvocationOnMock invocation) -> {
				User user = new User();
				user.setUsername(USERNAME);
				user.setEmail(USER_EMAIL);
				user.setPassword(USER_PASSWORD);
				
				Address address = new Address();
				
				address.setAddressID(ADDRESS_ID);
				address.setCity(CITY);
				address.setCountry(COUNTRY);
				address.setPostalCode("H4C2C4");
				address.setUser(user);
				
				HashSet<Address> set = new HashSet<Address>();
				set.add(address);
				user.setAddress(set);
				return address;
			});
			lenient().when(orderRepo.findOrderByOrderID(ORDERID)).thenAnswer((InvocationOnMock invocation) -> {
				User user = new User();
				user.setUsername(USERNAME);
				user.setEmail(USER_EMAIL);
				user.setPassword(USER_PASSWORD);
				
				Order order = new Order();
				order.setOrderID(ORDERID);
				order.setUser(user);
				
				Artwork artwork = new Artwork();
				artwork.setArtworkID(ARTWORK_ID);
				
				HashSet<Artwork> set = new HashSet<Artwork>();
				set.add(artwork);
				
				return order;
			});
			
			lenient().when(artworkRepo.findArtworkByArtworkID(ARTWORK_ID)).thenAnswer((InvocationOnMock invocation) -> {
				User user = new User();
				user.setUsername(USERNAME);
				user.setEmail(USER_EMAIL);
				user.setPassword(USER_PASSWORD);
				
				Order order = new Order();
				order.setOrderID(ORDERID);
				order.setUser(user);
				
				Artwork artwork = new Artwork();
				artwork.setArtworkID(ARTWORK_ID);
				
				
				HashSet<Artwork> set = new HashSet<Artwork>();
				set.add(artwork);
				
				user.setArtwork(set);
				
				HashSet<User> artistss = new HashSet<User>();
				artistss.add(user);
				artwork.setArtist(artistss);
				
				return artwork;
			});
			
			lenient().when(userRepo.findUserByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
				if(((String)(invocation.getArgument(0))).contentEquals(USERNAME)) {
					User user = new User();
					user.setUsername(USERNAME);
					user.setEmail(USER_EMAIL);
					user.setPassword(USER_PASSWORD);
					
					Order order = new Order();
					order.setOrderID(ORDERID);
					order.setUser(user);
					
					Artwork artwork = new Artwork();
					artwork.setArtworkID(ARTWORK_ID);
					
					
					HashSet<Artwork> set = new HashSet<Artwork>();
					set.add(artwork);
					
					user.setArtwork(set);
					
					HashSet<User> artistss = new HashSet<User>();
					artistss.add(user);
					artwork.setArtist(artistss);
				
				return user;
				} else {
					throw new IllegalArgumentException("invalid user");
				}
			});
			
			
			
		 
		 
	 }
	 
	 
	 
	 @Test
	 public void testCreateValidArtwork() {
			//assertEquals(0, service.getAllPayments().size());
			Artwork artwork = null;

			String error = null;
			//address.setAddressID(111);

			try {
				artwork = artworkService.createArtwork(ARTWORK_TITLE, ARTISTS, WORTH);   
			}catch (IllegalArgumentException e) {
				error = e.getMessage();
			}
			
			assertNotNull(artwork);
			assertEquals(artwork.getTitle(), ARTWORK_TITLE);
			assertEquals(error,null);
			
		 
	 }
	 
	 @Test
	 public void testSpaceNameCreateArtwork() {
		 Artwork artwork = null;
		 String error = null;
		 
		 try {
			 artwork = artworkService.createArtwork(" ", ARTISTS, WORTH);
			 
		 }catch(IllegalArgumentException e) {
			 error = e.getMessage();
		 }
		 assertNull(artwork);
		 assertEquals(error, "invalid title");
	 }
	 
	 @Test
	 public void testZeroWorthCreateArtwork() {
		 Artwork artwork = null;
		 String[] artists = {USERNAME};
		 String error ="";
		 try {
			 artwork = artworkService.createArtwork(ARTWORK_TITLE, ARTISTS, 0.0);
			 
		 }catch(IllegalArgumentException e) {
			error=e.getLocalizedMessage();
		 }
		 assertEquals(error , "invalid worth");
		 assertNull(artwork);
	 }
	 
	 @Test
	 public void testIsOnPremiseCreateArtwork() {
		 
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
		 String[] artists = {"invisibleBoy"};
		 try {
			 artwork = artworkService.createArtwork(ARTWORK_TITLE, artists, 100.00);
			 
		 }catch(IllegalArgumentException e) {
			error = e.getMessage();
		 }
		 assertEquals(error , "invalid user");
		 assertNull(artwork);
	 }
	 
	 @Test
	 public void testDeleteExistingArtwork()  {
		
		
	 }
	 
	 public static <T> List<T> toList(Iterable<T> iterable) {
	        List<T> lst = new ArrayList<T>();
	        for (T t : iterable) {
	            lst.add(t);
	        }
	        
	        return lst;
	    }
	 
	 
	
}
