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
	
	/**
	 * CREATE address object with provided address information and user object
	 * 
	 * @param username
	 * @param streetAddress1
	 * @param streetAddress2
	 * @param city
	 * @param province
	 * @param country
	 * @param postalCode
	 * @return
	 */
	
	@Transactional
		//TODO: validate parameters
	public Address createAddress(String username, String streetAddress1, String streetAddress2, String city, String province, String country, String postalCode) {
		if(username==null||streetAddress1==null||city==null|province==null||country==null||postalCode==null) throw new IllegalArgumentException("missing parameter");
		if(userRepo.findUserByUsername(username)==null) throw new IllegalArgumentException("invalid user");

		Address address = new Address();
		address.setUser(userRepo.findUserByUsername(username));
		address.setStreetAddress1(streetAddress1);
		address.setStreetAddress2(streetAddress2);
		address.setCity(city);
		address.setProvince(province);
		address.setCountry(country);
		address.setPostalCode(postalCode);
		address = addressRepo.save(address);
		return address;
	}
	
	/**
	 * GET the user object given the addressID
	 * @param addressID
	 * @return
	 */
	
	@Transactional
	//TODO: cannot get user of address
	public User getUserOfAddress(Integer addressID) {
		Address address = addressRepo.findAddressByAddressID(addressID);
		if(address == null) throw new IllegalArgumentException("invalid address");
		User user = address.getUser();
		return user;
	}
	
	/**
	 * GET address object given addressID
	 * 
	 * @param addressID
	 * @return
	 */
	
	@Transactional
	public Address getAddressById(Integer addressID) {
		if(addressID == null)
			throw new IllegalArgumentException("invalid address");

		return addressRepo.findAddressByAddressID(addressID);		
	}
	
	/**
	 * GET all addresses given userID
	 * 
	 * @param username
	 * @return
	 */
	@Transactional
	public List<Address> getAddressesByUser(String username) {
		if(username == null||userRepo.findUserByUsername(username)==null) throw new IllegalArgumentException("invalid user");
		List<Address> addresses = addressRepo.findAddressesByUser(userRepo.findUserByUsername(username));
		return addresses;
	}
	
	/**
	 * DELETE address given an addressID
	 * 
	 * @param addressId
	 * @return
	 */
	
	@Transactional
	public boolean deleteAddress(int addressId) {
		Address address = addressRepo.findAddressByAddressID(addressId);
		
		//Removing user association
		User addressUser = address.getUser();
		
		//Getting set of user addresses, remove address, set new list
		Set<Address> addressUserAddresses = addressUser.getAddress();
		addressUserAddresses.remove(address);
		addressUser.setAddress(addressUserAddresses);
		
		//Deleting from repository
		addressRepo.delete(address);
		
		return true;
	}
	
	/**
	 * UPDATE details of an existing address
	 */
	
	@Transactional	
	public void updateAddress(Integer addressID, String streetAddress1, String streetAddress2, String city, String province, String country, String postalCode) {
		if (addressRepo.findAddressByAddressID(addressID)==null) throw new IllegalArgumentException("invalid address");
		//TODO: validate parameters
		Address oldAddress = addressRepo.findAddressByAddressID(addressID);
		//Creating updated address
		oldAddress.setStreetAddress1(streetAddress1);
		oldAddress.setStreetAddress2(streetAddress2);
		oldAddress.setCity(city);
		oldAddress.setProvince(province);
		oldAddress.setCountry(country);
		oldAddress.setPostalCode(postalCode);
		oldAddress = addressRepo.save(oldAddress);
	}
	
	
	
}