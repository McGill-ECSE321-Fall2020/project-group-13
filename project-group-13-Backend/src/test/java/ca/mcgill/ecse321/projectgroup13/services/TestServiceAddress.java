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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TestServiceAddress {
	@Mock
	private AddressRepository addressRepository;
	
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private ArtworkRepository artworkRepository;
	
	@InjectMocks
	private AddressService addressService;
	
	@InjectMocks
	private UserService userService;
	
	private static final String ADDRESS="47 Cesar Avenue";
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
	
	 @BeforeEach
	    public void setMockOutput() {
	        lenient().when(addressRepository.save(any(Address.class))).thenAnswer((InvocationOnMock invocation) -> {
	            Address address = new Address();
	            address.setCity(ADDRESS);
	            address.setAddressID(ADDRESSID);
	            return address;
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
	    }
	 
	@Test
	public void testCreateAddressValid() {
		
		Address address = null;
		String error = null;
		try{

			address = addressService.createAddress(USERNAME ,ADDRESS, null, "MONTREAL", "QUEBEC", "CANADA", "H4C2C4");
		}catch(IllegalArgumentException e){
			error = e.getMessage();
		}
		assertTrue(address==null);
		assertTrue(error.contentEquals("invalid user"));
	}

	@Test
	public void testCreateAddressInvalidUser() {
		
		Address address = null;
		String error = null;
		try{

			address = addressService.createAddress("jokesonyoubaby" ,ADDRESS, null, "MONTREAL", "QUEBEC", "CANADA", "H4C2C4");
		}catch(IllegalArgumentException e){
			error = e.getMessage();
		}
		//assertTrue(address==null);
		assertTrue(error.contentEquals("invalid user"));
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
		assertTrue(error.contentEquals("missing parameter"));
	}
	
	@Test 
	public void testGetUserOfAddress() {
		
		try {
			userService.createUser("jake", "jake@google.com", "wellsaidwellsaid");
			Address address = addressService.createAddress("jake" ,ADDRESS, null, "MONTREAL", "QUEBEC", "CANADA", "H4C2C4");
			Integer ID = address.getAddressID();
			
			
			User user = addressService.getUserOfAddress(ID);
			assertTrue(user.getUsername().contentEquals("jake"));
		} catch (IllegalArgumentException | RegistrationException e) {
			
		}
	}
	
	@Test 
	public void testInvalidGetUserOfAddress() {
		String error = "";
		User user = null;
		
		try {
			user = addressService.getUserOfAddress(1);
			
		}catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(user);
		assertTrue(error.contentEquals("invalid address"));
	}
	
	@Test 
	public void testGetAddressByAddressID() {
		try {
			User juser = userService.createUser("johnson", "johnny@godfsogle.com", "wellsaidwellsaid");
			Address address = addressService.createAddress("johnson" ,ADDRESS, null, "MONTREAL", "QUEBEC", "CANADA", "H4C2C4");
			Address checkAdd = addressRepository.findAddressByAddressID(ADDRESSID);
			Address add = addressService.getAddressById(address.getAddressID());
			assertTrue(checkAdd.getCity().contentEquals(add.getCity()) && checkAdd.getAddressID()==add.getAddressID());
		} catch (IllegalArgumentException | RegistrationException e) {
			assertTrue(false);
		}
	}
	
	@Test 
	public void testGetAddressByAddressIDInvalid() {
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
		
		try {
			User juser = userService.createUser("jake", "jake@google.com", "wellsaidwellsaid");
			Address address = addressService.createAddress("jake" ,ADDRESS, null, "MONTREAL", "QUEBEC", "CANADA", "H4C2C4");
			List<Address> check = new ArrayList<Address>();
			check.add(address);
			List<Address> result = addressService.getAddressesByUser("Jake");
			
			assertTrue(result.equals(check));
		} catch (IllegalArgumentException | RegistrationException e) {
			
		}
	}
	
	@Test 
	public void testInvalidGetAddressesByUser() {

		
		List<Address> add = null;
		String error = null;
	
		
		try {
			add = addressService.getAddressesByUser("johndahoe");
			
		}catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(add);
		assertTrue(error.contentEquals("invalid user"));
	}
	
	@Test
	public void testDeleteAddress() {
		try {
			User juser = userService.createUser("jake", "jake@google.com", "wellsaidwellsaid");
			Address address = addressService.createAddress("jake" ,ADDRESS, null, "MONTREAL", "QUEBEC", "CANADA", "H4C2C4");
			Address checkAdd = addressRepository.findAddressByAddressID(ADDRESSID);
		
		addressService.deleteAddress(address.getAddressID());
		assertTrue(checkAdd!=null);
		} catch(IllegalArgumentException | RegistrationException e) {
			assertTrue(false);
		}
		assertNull(addressRepository.findAddressByAddressID(ADDRESSID));
		
	}
	
	
	@Test
	public void testUpdateAddress() {
		
		String origCity = null;
		Address address = null;
		Integer ID = null;
		
		
		try {
			userService.createUser("jake", "jake@google.com", "wellsaidwellsaid");
			address = addressService.createAddress("jake" ,ADDRESS, null, "MONTREAL", "QUEBEC", "CANADA", "H4C2C4");
			ID = address.getAddressID();
			origCity = address.getCity();
			
		
			addressService.updateAddress(ADDRESSID, ADDRESS,null, "SuckyToronto", "QUEBEC", "CANADA", "H4C2C4");
			
		}catch(IllegalArgumentException | RegistrationException e) {
			
		}
		assertFalse(addressRepository.findAddressByAddressID(ID).getCity().contentEquals(origCity));
		
		
	}
	
	
	@Test
	public void testNonExistingUpdateAddress() {
		String error = "";
		
		try {
			addressService.updateAddress(111, ADDRESS,null, "MONTREAL", "QUEBEC", "CANADA", "H4C2C4");
			
		}catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertTrue(error.contentEquals("invalid address"));
		
	}
	
	
		
		
	
	
}

