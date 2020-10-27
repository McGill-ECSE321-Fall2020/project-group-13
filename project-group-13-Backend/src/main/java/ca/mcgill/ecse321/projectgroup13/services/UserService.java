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
import java.util.List;
import java.util.stream.Collectors;


@Service

/**
 * Service to handle login and creation of user account.
 */
public class UserService {

    /**
	 * Create userDTO from user obj input
	 * @param user user object
	 * @return dto object
	 */
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;

	public static UserDTO userToDto(User user) {
		UserDTO userDto = new UserDTO();
		userDto.setEmail(user.getEmail());
		userDto.setUsername(user.getUserName());
        userDto.setUserType(user.getUserType());
		return userDto;
	}

    /** 
     * If we are given valid user params, go ahead and create a user, otherwise throws an exception
     * 
     */

    public UserDTO createUser(UserDTO user) throws RegistrationException {
        //must verify that no other user is associated with the same email
        if(userRepository.findUserByEmail(user.getEmail()) != null) throw new RegistrationException("Email already in use");
        //make sure Username is unique
        if(userRepository.findUserByUsername(user.getUsername())!=null)  throw new RegistrationException("Username already in use");
        //invalid password -- password must contain at least one letter and one number
        String password = user.getPassword();
        if(!password.matches(".*\\d.*") || !password.matches(".*[a-Z].*")) throw new  RegistrationException("invalid password entered, contain number and letter");
        
        //ALL CONDITIONS HAVE PASSED
        User newUser = new initializeUser(user);


        userRepository.save(newUser);
        cartRepository.save(newUser.getCart());
        return userToDto(user1);
    }

    public boolean deleteUser(String username) throws RegistrationException {
        User user = userRepository.findUserByUsername(username);
        if(user==null) throw new RegistrationException("User does not exist");
    }

    //To reset user password
    public String resetPassword(String username) throws RegistrationException {
        User user = userRepository.findUserByUsername(username);
        if(user == null) throw new RegistrationException("No user found");
        String tmpPassword = randomPassword();
        user.setPassword(tmpPassword);
        return tmpPassword;
    }


    //HELPER METHODS BELOW
    //must test this not sure if it works
    private String randomPassword(){
        
        PasswordGenerator generator = new PasswordGenerator();
        CharacterData digit = EnglishCharacterData.Digit;
        CharacterData upperCaseChars= EnglishCharacterData.UpperCase, lowerCaseChars = EnglishCharacterData.UpperCase;
        
        //set first rule
        lowerCaseRule.setNumberOfCharacters(4);
        upperCaseRule.setNumberOfCharacters(2);
        digit.setNumberOfCharacters(3);
        String password = generator.generatePassword(10, upperCaseRule, lowerCaseRule, digitRule);


    
    }

    //must fix maybe?
    private User initializeUser(UserDTO user) {
        User newUser = new User();
        //set parameters of user
        newUser.setUsername(user.getUsername());
    
        newUser.setEmail(user.getEmail());
        newUser.setPassword( user.getPassword());
        //should I add the cart also? give user a cart
        Cart cart = new Cart();
        cart.setUser(newUser);
        cart.setCartID(newUser.getUsername() + "cart");

        return newUser;
    }




}