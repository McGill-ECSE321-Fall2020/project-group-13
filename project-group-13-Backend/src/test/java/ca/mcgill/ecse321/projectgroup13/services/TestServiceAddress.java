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

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.doAnswer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class TestServiceAddress {
	@Mock
	private AddressRepository addressRepository;
	
	@Mock
	private User user1;
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private ArtworkRepository artworkRepository;
	
	
	@InjectMocks
	private AddressService addressService;
	
	@InjectMocks
	private UserService userService;
	private static final int INVALID_ID = 404;
	private static final int ADDRESS_ID = 428571;
	private static final int ADDRESS_ID2 = 285714;
	private static final String ADDRESS="47 Cesar Avenue";
	private static final String ADDRESS2="47 Cesar Avenue";
	private static final String CITY="Montreal";
	private static final String PROVINCE="Quebec";
	private static final String COUNTRY="Canada";
	private static final String USERNAME = "person1";
	private static final String USER_PASSWORD= "Thatguy123#";
	private static final String USER_EMAIL= "person1@gmail.com";
	private static final Integer ARTWORKID = 12324324 ;
	private static final String TITLE = "Beauty of the times";
	private static final Integer ADDRESSID = 12312324 ;
	private static final String TITLE2 = "Beauty of the times";
	private static final String NONEXIST_USER = "jokes on you";
	private boolean isDeleted = false;
	private boolean isSaved = false;
	 @BeforeEach
	    public void setMockOutput() {
	        lenient().when(addressRepository.save(any(Address.class))).thenAnswer((InvocationOnMock invocation) -> {
	            isSaved = true;
	            return invocation.getArgument(0);
	        });
	        lenient().when(addressRepository.findAddressByAddressID(any(Integer.class))).thenAnswer((InvocationOnMock invocation) -> {
	        	User user1 = new User();
	        	Set<Address> set = new HashSet<Address>();
	        	user1.setUsername(USERNAME);
	        	Address address = new Address();
	            address.setCity(CITY);
	            address.setAddressID(invocation.getArgument(0));
	            address.setUser(user1);
	            set.add(address);
	            user1.setAddress(set);
	            if(invocation.getArgument(0).equals(INVALID_ID)) return null;
	            return address;
	        });
	        
	        lenient().when(addressRepository.findAddressesByUserUsername(any(String.class))).thenAnswer((InvocationOnMock invocation) -> {
	            if(invocation.getArgument(0)==NONEXIST_USER) return null;
	        	List<Address> list = new ArrayList<Address>();
	        	Address address = new Address();
	            address.setCity(CITY);
	            address.setAddressID(ADDRESSID);
	            Address address2 = new Address();
	            address2.setCity(CITY);
	            address2.setAddressID(ADDRESS_ID2);
	            list.add(address);

	            return list;
	        });
	        
	        lenient().when(userRepository.save(any(User.class))).thenAnswer((InvocationOnMock invocation) -> {
				 User user = new User();
				user.setUsername(USERNAME);
				user.setEmail(USER_EMAIL);
				user.setPassword(USER_PASSWORD);
				HashSet<Address> set = new HashSet<Address>();
				set.add(addressRepository.findAddressByAddressID(ADDRESSID));
				user.setAddress(set);
				return user;
	        });
	        lenient().when(userRepository.findUserByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
	        	if(invocation.getArgument(0)==NONEXIST_USER) return null;
				return new User();
			});
	        
	        
	    }
	 
	@Test
	public void testCreateAddressValid() {
		
		
		
		Address address = null;
		String error = "";
		User user = null;
		try{
			
			address = addressService.createAddress("jake",ADDRESS, ADDRESS2, "MONTREAL", "QUEBEC", "CANADA", "H4C2C4");
		}catch(IllegalArgumentException e){
			error = e.getMessage();
		}
		assertNotNull(address);
		assertEquals(address.getStreetAddress1(),ADDRESS);
		assertEquals(address.getCity(),"MONTREAL");
		assertEquals(error,"");

	}

	@Test
	public void testCreateAddressInvalidUser() {
		
		Address address = null;
		String error = "";
		try{
			address = addressService.createAddress(NONEXIST_USER,ADDRESS, ADDRESS2, "MONTREAL", "QUEBEC", "CANADA", "H4C2C4");
		}catch(IllegalArgumentException e){
			error = e.getMessage();
		}
		assertNull(address);
		assertEquals(error,"invalid user");
	}

	@Test
	public void testCreateAddressMissingArguments() {
		
		Address address = null;
		String error = null;
		try{
			address = addressService.createAddress(USERNAME, null, null, null,"QUEBEC", "CANADA", "H4C2C4" );
		}catch(IllegalArgumentException e){
			error = e.getMessage();
		}
		
		assertNull(address);
		assertEquals(error,"missing parameter");
	}
	
	@Test 
	public void testGetUserOfAddress() {
		User user = null;
		try {
			user = addressService.getUserOfAddress(ADDRESS_ID);
			
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertEquals(user.getUsername(),USERNAME);
	}
	
	@Test 
	public void testInvalidGetUserOfAddress() {
		String error = "";
		User user = null;
		
		try {
			user = addressService.getUserOfAddress(INVALID_ID);
			
		}catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(user);
		assertEquals(error,"invalid address");
	}
	
	@Test 
	public void testGetAddressByAddressID() {
		Address add = null;
		try {
			add = addressService.getAddressById(ADDRESS_ID);
			
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertEquals(add.getAddressID(),ADDRESS_ID);
	}
	
	@Test 
	public void testGetAddressByAddressIDNull() {
		Address add = null;
		String error = "";
		try {
			add = addressService.getAddressById(null);
			
		}catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(add);
		assertTrue(error.contentEquals("invalid address"));
	}
	
	@Test 
	public void testGetAddressesByUser() {
		List<Address> result = null;
		try {
			result = addressService.getAddressesByUser("Jake");
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(result);
	}
	
	@Test 
	public void testInvalidGetAddressesByUser() {

		
		List<Address> add = null;
		String error = null;
	
		
		try {
			add = addressService.getAddressesByUser(null);
			
		}catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(add);
		assertTrue(error.contentEquals("invalid user"));
	}
	
	@Test
	public void testDeleteAddress() {
		
		Integer addressID = null;
		boolean isDeleted = false;
		
		try {
			isDeleted = false;
		isDeleted = addressService.deleteAddress(ADDRESS_ID);
		} catch(IllegalArgumentException e) {
			//Throws an invalid email error for some reason, but address gets added and deleted
			fail();
		}
		assertTrue(user1.getAddress().isEmpty());
		assertTrue(isDeleted);
		
	}
	
	
	@Test
	public void testUpdateAddress() {
		
		
		try {
			isSaved = false;
			addressService.updateAddress(ADDRESS_ID, ADDRESS,ADDRESS2, "SuckyToronto", "QUEBEC", "CANADA", "H4C2C4");
			
		}catch(Exception e) {
			fail();
		}
		assertTrue(isSaved);
		
	}
	
	
	@Test
	public void testNonExistingUpdateAddress() {
		String error = "";
		try {
			addressService.updateAddress(INVALID_ID, ADDRESS,ADDRESS2, "MONTREAL", "QUEBEC", "CANADA", "H4C2C4");
			
		}catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertTrue(error.contentEquals("invalid address"));
		
	}
	
	
		
		
	
	
}

