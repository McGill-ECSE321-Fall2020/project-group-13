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

import java.sql.Date;
import java.sql.Time;
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
	
	@Mock
	private CartRepository cartRepo;
	 
		@InjectMocks
		private ArtworkService artworkService;
		
		@Mock
		private CartService cartService;
		
		
		
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
			
			lenient().when(cartService.deleteCart(any(Cart.class))).thenAnswer((InvocationOnMock invocation) -> {
				return true;
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
			
			lenient().when(artworkRepo.findArtworkByArtworkID(any(Integer.class))).thenAnswer((InvocationOnMock invocation) -> {
				if (invocation.getArgument(0).equals(ARTWORK_ID)) {
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
					
					Order order = new Order();
					order.setOrderID(ORDERID);
					order.setUser(user);
					
					Artwork artwork = new Artwork();
					artwork.setArtworkID(ARTWORK_ID);
					
					
					HashSet<Artwork> sets = new HashSet<Artwork>();
					sets.add(artwork);
					
					user.setArtwork(sets);
					
					HashSet<User> artistss = new HashSet<User>();
					artistss.add(user);
					artwork.setArtist(artistss);
					
					Shipment shipment = new Shipment();
					shipment.setAddress(address);
					shipment.setOrder(order);
					shipment.setEstimatedTimeOfArrival(Time.valueOf("14:00:00"));
					shipment.setEstimatedDateOfArrival(Date.valueOf("2020-12-20"));
					return artwork;
					} else {
						return null;
					}
			});
			
			lenient().when(artworkRepo.getArtworkByArtist(any(User.class))).thenAnswer((InvocationOnMock invocation) -> {
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
				
				return set;
			});
			//findByArtist
			
			lenient().when(artworkRepo.findByArtist(any(User.class))).thenAnswer((InvocationOnMock invocation) -> {
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
				
				return set;
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
					return null;
				}
			});
			
			
			lenient().when(cartRepo.findCartsByArtwork(any(Artwork.class))).thenAnswer((InvocationOnMock invocation) -> {
				if(((Artwork)(invocation.getArgument(0))).getArtworkID()==ARTWORK_ID) {
					User user = new User();
					user.setUsername(USERNAME);
					user.setEmail(USER_EMAIL);
					user.setPassword(USER_PASSWORD);
					
					Order order = new Order();
					order.setOrderID(ORDERID);
					order.setUser(user);
					
					Artwork artwork = new Artwork();
					artwork.setArtworkID(ARTWORK_ID);
					artwork.setWorth(WORTH);
					

					HashSet<Artwork> set = new HashSet<Artwork>();
					set.add(artwork);
					
					user.setArtwork(set);
					
					HashSet<User> artistss = new HashSet<User>();
					artistss.add(user);
					artwork.setArtist(artistss);
		
					Cart cart = new Cart();
					cart.setArtwork(set);
					cart.setUser(user);
					
					HashSet<Cart> carts = new HashSet<Cart>();
					carts.add(cart);
				return carts;
				} else {
					return null;
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
				artwork = artworkService.createArtwork(ARTWORK_TITLE, ARTISTS, WORTH, "");   
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
			 artwork = artworkService.createArtwork(" ", ARTISTS, WORTH, "");
			 
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
			 artwork = artworkService.createArtwork(ARTWORK_TITLE, ARTISTS, 0.0, "");
			 
		 }catch(IllegalArgumentException e) {
			error=e.getLocalizedMessage();
		 }
		 assertEquals(error , "invalid worth");
		 assertNull(artwork);
	 }
	 
	 @Test
	 public void testArtworkWithInvalidArtist() {
		 Artwork artwork = null;
		 String error ="";
		 String[] artists = {"invisibleBoy"};
		 try {
			 artwork = artworkService.createArtwork(ARTWORK_TITLE, artists, 100.00, "");
			 
		 }catch(IllegalArgumentException e) {
			error = e.getMessage();
		 }
		 assertEquals(error , "invalid user");
		 assertNull(artwork);
	 }
	 
	 @Test
	 public void testDeleteExistingArtwork()  {
		 
		 Artwork artwork = artworkRepo.findArtworkByArtworkID(ARTWORK_ID);
		 Boolean test = false;
		 String error = null;
		try{
			
			test = artworkService.deleteArtwork(artwork);
		
		}
		catch(Exception e) {
			error = e.getMessage();
			
		}
		
		assertNull(error);
		assertTrue(test);
		
		
	 }
	
	 @Test
	 public void testGetArtworkByID()  {
		 Artwork art = null;
		art = artworkService.getArtworkByID(ARTWORK_ID);
		
		assertNotNull(art);
		
		
	 }
	 
	 @Test
	 public void testGetArtworksOfArtist()  {
		 Set<Artwork> artworks = null;
		 String error = null;
		try{artworks = artworkService.getArtworksOfArtist(USERNAME);}
		catch(Exception e) {
			error = e.getMessage();
		}
		assertNotNull(artworks);
		assertNull(error);
		assertTrue(!artworks.isEmpty());
		
	 }
	 @Test
	 public void testEditCollection()  {
		Artwork art = new Artwork();
		try{artworkService.editArtwork_collection(art, "McGill");}
		catch(Exception e) {
			fail();
		}
		assertEquals(art.getCollection(),"McGill");
	 }
	 @Test
	 public void testEditDate()  {
		 Artwork art = new Artwork();
		try{artworkService.editArtwork_creationDate(art, "2020-02-02");}
		catch(Exception e) {
			fail();
		}
		assertEquals(art.getCreationDate(),"2020-02-02");
	 }
	 @Test
	 public void testEditDescription()  {
		 Artwork art = new Artwork();
		 String des = "very nice paint";
		try{artworkService.editArtwork_description(art, des);}
		catch(Exception e) {
			fail();
		}
		assertEquals(art.getDescription(),des);
	 }
	 @Test
	 public void testEditDimension()  {
		 Artwork art = new Artwork();
		try{artworkService.editArtwork_dimensions(art, "large");}
		catch(Exception e) {
			fail();
		}
		assertEquals(art.getDimensions(),"large");
	 }
	 @Test
	 public void testEditUrl()  {
		 Artwork art = new Artwork();
		try{artworkService.editArtwork_imageURL(art, "www.notreal.com/d");}
		catch(Exception e) {
			fail();
		}
		assertEquals(art.getImageUrl(),"www.notreal.com/d");
	 }
	 @Test
	 public void testEditIsOnPremise()  {
		Artwork art = new Artwork();
		artworkService.editArtwork_isOnPremise(art,true);
		assertEquals(art.isIsOnPremise(),true);
		
	 }
	 @Test
	 public void testEditMedium()  {
		Artwork art = new Artwork();
		artworkService.editArtwork_medium(art, "universe dust");
		assertEquals(art.getMedium(),"universe dust");
		
	 }
	 @Test
	 public void testEditTitle()  {
		Artwork art = new Artwork();
		artworkService.editArtwork_title(art, TITLE);
		assertEquals(art.getTitle(),TITLE);
		
	 }
	 @Test
	 public void testEditWorth()  {
		Artwork art = new Artwork();
		artworkService.editArtwork_worth(art, 1500.0);
		assertEquals(art.getWorth(),1500.0);
	 }
	 @Test
	 public void testIsSold() {
		 	Artwork art = new Artwork();
			artworkService.setArtwork_artworkSold(art);;
			assertEquals(art.isArtworkSold(),true);
	 }
	
	 
	 
	
}