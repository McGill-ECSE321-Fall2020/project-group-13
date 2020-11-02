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
import ca.mcgill.ecse321.projectgroup13.dto.UserDto;
import ca.mcgill.ecse321.projectgroup13.model.*;
import ca.mcgill.ecse321.projectgroup13.services.exception.RegistrationException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import java.util.HashSet;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class TestCartService {
	private static final String USERNAME = "person1";
	private static final String USER_PASSWORD= "Thatguy123#";
	private static final String USER_EMAIL= "person1@gmail.com";
	
	private static final String USERNAME2 = "person2";
	private static final String USER_PASSWORD2= "Thatgirl123#";
	private static final String USER_EMAIL2= "person2@gmail.com";
	private Integer CART_ID = 12342;
	private boolean success = false;
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
	private UserRepository userRepo;
	
	@Mock
	private ArtworkRepository artworkRepo;
	@Mock
	private CartRepository cartRepo;
	@Mock
	private AddressRepository addressRepo;
	@Mock
	private OrderRepository orderRepo;
	@InjectMocks
	private CartService cartService;
	
	
	@BeforeEach
	public void setMockOutput() {
		MockitoAnnotations.initMocks(this);
		lenient().when(userRepo.findUserByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
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
		lenient().when(userRepo.findUserByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
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
		
		lenient().when(artworkRepo.findArtworkByArtist(USERNAME)).thenAnswer((InvocationOnMock invocation) -> {
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
		
		lenient().when(cartRepo.findCartByCartID(any(Integer.class))).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(CART_ID)) {
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
			
			Cart cart = new Cart();
			cart.setCartID(CART_ID);
			
			cart.setUser(user);
			cart.setArtwork(set);
			
			return cart;
			} else {
				return null;
			}
		});
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		
		lenient().when(userRepo.save(any(User.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(artworkRepo.save(any(Artwork.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(cartRepo.save(any(Cart.class))).thenAnswer(returnParameterAsAnswer);
        
	}
	
	
	
	
	@Test
	public void testCreateCartSingleArtwork() {
		Cart cart = null; 
		String error = null;
		try {
			cart = cartService.createCart(userRepo.findUserByUsername(USERNAME));
		} catch (Exception e) {
			error=e.getMessage();
		}
		
		assertEquals(error, null);
		assertNotNull(cart);
	}
	

	@Test
	public void testInvalidCreateCartSingleArtwork() {
		Cart cart = null; 
		String error = null;
		User user = null;
		try {
			cart = cartService.createCart(user);
		} catch (Exception e) {
			error=e.getMessage();
		}
		
		assertEquals(error, "invalid user");
		assertNull(cart);
	}
	
	@Test
	public void testCreateCartNullArtwork() {
		Cart cart = null; 
		String error = null;
		Set<Artwork> nullArtworkSet = null; 
		
		try {
			cartService.createCart(userRepo.findUserByUsername(USERNAME), nullArtworkSet);
		} catch (Exception e) {
			error=e.getMessage();
		}
		
		assertEquals(error, "set<artwork> cannot be null");
		assertNull(cart);
	}
	
	
	@Test
	public void testCreateCartSingleArt() {
		Cart cart = null; 
		String error = null;
		User user = userRepo.findUserByUsername(USERNAME);
		Artwork art = artworkRepo.findArtworkByArtworkID(ARTWORK_ID);
		try {
			cart = cartService.createCart(user, art);
		} catch (Exception e) {
			error=e.getMessage();
		}
		
		assertEquals(error, "invalid user");
		assertNull(cart);
	}
	
	@Test
	public void testInvalidCreateCartSingleArt() {
		Cart cart = null; 
		String error = null;
		User user = userRepo.findUserByUsername(USERNAME);
		Artwork art =null;
		try {
			cart = cartService.createCart(user, art);
		} catch (Exception e) {
			error=e.getMessage();
		}
		
		assertEquals(error, "invalid argument");
		assertNull(cart);
	}
	
	@Test
	
	public void testAddSetValid() {
		Cart cart = null; 
		String error = null;
		User user = userRepo.findUserByUsername(USERNAME);
		Artwork art =artworkRepo.findArtworkByArtworkID(ARTWORK_ID);
		HashSet<Artwork> set = new HashSet<Artwork>();
		set.add(art);
		try {
			cart = cartService.createCart(user, set);
		} catch (Exception e) {
			error=e.getMessage();
		}
		
		assertEquals(error, null);
		assertNotNull(cart);
	}
	
	
@Test
	
	public void testInvalidUserAddSetValid() {
		Cart cart = null; 
		String error = null;
		User user = userRepo.findUserByUsername("");
		Artwork art =artworkRepo.findArtworkByArtworkID(ARTWORK_ID);
		HashSet<Artwork> set = new HashSet<Artwork>();
		set.add(art);
		try {
			cart = cartService.createCart(user, set);
		} catch (Exception e) {
			error=e.getMessage();
		}
		
		assertEquals(error, "invalid argument");
		assertNull(cart);
	}


@Test
public void testInvalidArtworkAddSetValid() {
	Cart cart = null; 
	String error = null;
	User user = userRepo.findUserByUsername("");
	HashSet<Artwork> set = null;
	try {
		cart = cartService.createCart(user, set);
	} catch (Exception e) {
		error=e.getMessage();
	}
	
	assertEquals(error, "invalid argument");
	assertNull(cart);
}
	
	@Test
	public void testAddMultipleValid() {
		
	}
	
	@Test
	public void testRemoveMultipleInvalid() {
		
		
	}
	
	@Test
	public void testGetCart() {
		Cart cart = cartService.getCart(CART_ID);
		assertNotNull(cart);
		assertEquals( cart.getUser().getUsername(), USERNAME);
	}
	
	@Test
	public void testDeleteCart() {
		
		
	}
	
	
		
		
	
	
}

