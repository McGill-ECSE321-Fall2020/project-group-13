package ca.mcgill.ecse321.projectgroup13.services;

import ca.mcgill.ecse321.projectgroup13.dao.*;
import ca.mcgill.ecse321.projectgroup13.dto.*;


import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ca.mcgill.ecse321.projectgroup13.model.*;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@ Service
public class AddressService {
	
	@Autowired 
	private UserRepository userRepo;
	@Autowired
	private ShipmentRepository shipmentRepo;
	@Autowired
	private AddressRepository addressRepo;
	
	// Address creation
	@Transactional
	public Address createAddress(User user, String streetAddress1, String streetAddress2, String city, String province, String country, String postalCode) {
		
		Address address = new Address();
		address.setUser(user);
		address.setStreetAddress1(streetAddress1);
		address.setStreetAddress2(streetAddress2);
		address.setCity(city);
		address.setProvince(province);
		address.setCountry(country);
		address.setPostalCode(postalCode);
		addressRepo.save(address);
		return address;
	}
	
	//Get User of Address By ID
	@Transactional
	public User getUserOfShipement(String addressID) {
		Address address = addressRepo.findAddressByAddressID(addressID);
		User user = address.getUser();
		return user;
	}
	
	// Get Address by ID
	@Transactional
	public Address getAddressById(String addressID) {
		
		if(addressID == null) {
			throw new IllegalArgumentException("You must input an ID");
		}
		
		
		Address address = addressRepo.findAddressByAddressID(addressID);
		
		if(address != null) {
			return address;
		} else {
			throw new IllegalArgumentException("Address not found");
		}
		
	}
	
	
//	//Delete Address
//	@Transactional
//	public boolean deleteAddress
//	
	
	// Get Addresses of User
	@Transactional
	public List<Address> getAddressesByUsername(String username) {
		
		User user = userRepo.findUserByUsername(username);
		
		if(user != null) {
			List<Address> addresses = addressRepo.findAddressesByUser(user);
			return addresses;
		} else {
			throw new IllegalArgumentException("No user with this ID found");
		}
		
		
//		@Query("SELECT ")
//		
//		List<Address> addressByPerson = new ArrayList<>();
//		
//		for(User user : addressRepo.find)
//		
		//Set<Address> addresses = new HashSet<Address>();
		
		//for(Address address: addressRepo.findAddressByAddressID())
	}
	
}
