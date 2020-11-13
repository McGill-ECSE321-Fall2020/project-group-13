package ca.mcgill.ecse321.projectgroup13.services;

import ca.mcgill.ecse321.projectgroup13.dao.*;

import ca.mcgill.ecse321.projectgroup13.dto.*;


import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ca.mcgill.ecse321.projectgroup13.model.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import java.util.regex.Pattern;
import java.util.stream.Collectors;
import ca.mcgill.ecse321.projectgroup13.services.exception.*;

/**
 * Service to handle login and creation of user account.
 */
@Service
public class UserService {
	
	
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ArtworkRepository artworkRepository;
	@Autowired
	private OrderService orderService;
	@Autowired
	private AddressService addressService;
	@Autowired
	private CartService cartService;
	
    //TODO must implement password encoder, was causing errors
    //@Autowired
    //private PasswordEncoder passwordEncoder;


    /**
     * service method to create a user
     * @param username
     * @param email
     * @param password
     * @return User
     * @throws RegistrationException
     */
    @Transactional
    public User createUser(String username, String email, String password) throws RegistrationException {
        User user = new User();
        if(userRepository.findUserByUsername(username) != null) throw new RegistrationException("invalid username");
        //checking if email syntax is valid
        if(!checkIfValidEmail(email)) {
            throw new RegistrationException("invalid email");
        }
        if(userRepository.findUserByEmail(email) != null) throw new RegistrationException("Email already in use");
        //ALL CONDITIONS HAVE PASSED
        user.setUsername(username);
        user.setEmail(email);
        //TODO implement the encoder -- was causing errors
        //user.setPassword(passwordEncoder.encode(password));
        user.setPassword(password);
        user.setArtwork(new HashSet<>());
        user.setOrder(new HashSet<>());
        user.setAddress(new HashSet<>());
        userRepository.save(user);
        return user;
    }
    
    
    
    
    //TODO make class to do password stuff

    /**
     * service method to reset user password
     * @param username
     * @return
     */
    @Transactional
    public User resetPassword(String username) {
        User user = userRepository.findUserByUsername(username);
        if(user==null) throw new IllegalArgumentException("invalid username");
    	String password = UserService.generateRandomPassword();
    	user.setPassword(password);
    	userRepository.save(user);
    	return user;
    }

    /**
     * service method to delete a certain user from Database
     * if user doesn't have artworks to his name, just delete
     * else, remove user from list of artists
     * if user is the only artist, delete artwork too
     * @param username
     * @throws RegistrationException
     */
    @Transactional
    public Boolean deleteUser(String username) throws RegistrationException {
        User user = userRepository.findUserByUsername(username);
        if(user==null) throw new RegistrationException("User does not exist");
        Set<Address> userAddresses = user.getAddress();
        Cart cart = user.getCart();
        Set<Order> orders = user.getOrder();
        Set<Artwork> Artworks = artworkRepository.getArtworkByArtist(user);
        
        if(userAddresses!=null) {
	        for(Address address:userAddresses) {
	        	addressService.deleteAddress(address.getAddressID());
	        }
        }
        if(orders!=null) {
	        for(Order order:orders) {
	        	if (order.getOrderStatus().equals(OrderStatus.PaymentPending))
	        		orderService.deleteOrder(order);
	        }
        }
        if (user.getCart() != null)
            cartRepository.delete(cart);

        for(Artwork artwork: Artworks){
            if(artwork.getArtist().size() == 1){
                Set<Cart> carts = cartRepository.findCartsByArtwork(artwork);
                for(Cart c : carts){
                    cartService.removeFromCart(c, artwork);
                }
                artworkRepository.delete(artwork);
            }else{
                artwork.getArtist().remove(user);
                artworkRepository.save(artwork);
            }
        }
        userRepository.delete(user);
        
        return true;
    }


    /**
     * service method th=o get a user by username
     * @param username
     * @return User
     */
    @Transactional
    public User getUserByUsername(String username){
        User user = userRepository.findUserByUsername(username);
        if(user ==null) throw new IllegalArgumentException("invalid username");
        return user;
    }
    
    /**
     * service method to get a user by username and password
     * @param username
     * @return User
     */
    @Transactional
    public User getUserByUsernameAndPassword(String username, String password){
        User user = userRepository.findUserByUsernameAndPassword(username, password);
        if(user ==null) throw new IllegalArgumentException("invalid username or password");
        return user;
    }


    /**
     * service method to edit user email
     * @param username
     * @param newEmail
     * @throws RegistrationException
     */
    @Transactional
    public User editEmail(String username, String newEmail) throws RegistrationException {
        User user = userRepository.findUserByUsername(username);
            if(!checkIfValidEmail(newEmail) || (userRepository.findUserByEmail(newEmail) != null)) {
                throw new RegistrationException("invalid email");
            }
                user.setEmail(newEmail);
                userRepository.save(user);
                return user;
    }

    //TODO: implement login for later
//    @Transactional
//    public String login(LoginDto loginDto) throws LoginException {
//    	//must validate login information
//    	//check if username exists
//    	User user = userRepository.findUserByUsername(loginDto.getUsername());
//    	if (user==null ) throw new LoginException("invalid username");
//    	//TODO must implement the password encoder
//    	//if (user.getPassword()!=passwordEncoder.encode(loginDto.getPassword())) throw new LoginException("invalid password");
//    	if (user.getPassword()!=loginDto.getPassword()) throw new LoginException("invalid password");
//    	return createToken(user);
//    }

    /**
     * service method to edit user bio
     * @param username
     * @param newBio
     */
    @Transactional
    public User editBio(String username, String newBio){
        User user = userRepository.findUserByUsername(username);
        if(user==null) throw new IllegalArgumentException("invalid username");
        user.setBio(newBio);
        userRepository.save(user);
        return user;
    }


    /**
     * service method to edit profile picture of user
     * @param username
     * @param newUrl
     */
    @Transactional
    public User editProfilePictureUrl(String username, String newUrl){
        User user = userRepository.findUserByUsername(username);
        user.setProfilePictureURL(newUrl);
        userRepository.save(user);
        return user;
    }

    
    //TODO update token sophistication
    /**
     * Create token upon login, and set it for corresponding user
     * 
     * @param user
     * @return String token 
     */
    private String createToken(User user){
    	String token = UUID.randomUUID().toString();
    	user.setApiToken(token);
    	userRepository.save(user);
    	return token;
    }


    /**
     * Generates a strong temporary password to be used in case of password reset.
     * @return randomly generated password
     */
    //TODO: should this be a public method?
    private static String generateRandomPassword() {
        String upperCaseLetters = RandomStringUtils.random(1, 65, 90, true, true);
        String lowerCaseLetters = RandomStringUtils.random(1, 97, 122, true, true);
        String numbers = RandomStringUtils.randomNumeric(1);
        String totalChars = RandomStringUtils.randomAlphanumeric(6);
        String combinedChars = upperCaseLetters.concat(lowerCaseLetters).concat(numbers).concat(totalChars);
        List<Character> pwdChars = combinedChars.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
        Collections.shuffle(pwdChars);
        return pwdChars.stream().collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();
    }





    //*************** HELPER METHODS ***************//

    /**
     * Checks for a valid email using regex from
     * https://stackoverflow.com/questions/8204680/java-regex-email
     *
     */
    private boolean checkIfValidEmail(String email) {
    	String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    	 Pattern pattern = Pattern.compile(regex);
    	 return pattern.matcher(email).matches();

    }
}