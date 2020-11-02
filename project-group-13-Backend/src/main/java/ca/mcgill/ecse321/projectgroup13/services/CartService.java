package ca.mcgill.ecse321.projectgroup13.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.projectgroup13.dao.ArtworkRepository;
import ca.mcgill.ecse321.projectgroup13.dao.CartRepository;
import ca.mcgill.ecse321.projectgroup13.dao.UserRepository;
import ca.mcgill.ecse321.projectgroup13.model.Artwork;
import ca.mcgill.ecse321.projectgroup13.model.Cart;
import ca.mcgill.ecse321.projectgroup13.model.User;

@Service
public class CartService {
	
	@Autowired
	CartRepository cartRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	ArtworkRepository artworkRepository;
	/**
	 * Create empty cart and associate it with user
	 * @param user user to link to cart
	 * @return new cart
	 */
	@Transactional
	public Cart createCart(User user) {
		if (user == null)
			throw new IllegalArgumentException("invalid user");
		
		Cart cart = new Cart();
		cart.setUser(user);
		cart.setTotalCost(0);
		cart.setArtwork(new HashSet<Artwork>());
		cart = cartRepository.save(cart);
		return cart;
	}
	
	/**
	 * Create non-empty cart and associate it with user
	 * @param user user to link to cart
	 * @param art artwork to add to cart
	 * @return new cart
	 */
	@Transactional
	public Cart createCart(User user, Artwork art) {
		if (art == null||user==null)
			throw new IllegalArgumentException("invalid argument");
		
		Cart cart = new Cart();
		cart.setUser(user);
		cart.setTotalCost(0);
		cart.setArtwork(new HashSet<Artwork>());
		cart.getArtwork().add(art);
		cart = cartRepository.save(cart);
		return cart;
	}
	
	/**
	 * create non-empty cart with association with user
	 * @param user user to link to cart
	 * @param art collection of artwork to add to cart
	 * @return new cart
	 */
	@Transactional
	public Cart createCart(User user, Set<Artwork> art) {
		if (user == null)
			throw new IllegalArgumentException("user cannot be null");
		if (userRepository.findUserByUsername(user.getUsername())==null) throw new IllegalArgumentException("invalid user");
		if (art == null) 
			throw new IllegalArgumentException("set<artwork> cannot be null");	//must check parameter is not null
		//make sure they are all valid additions
		for(Artwork artsy : art) {
			artworkRepository.findArtworkByArtworkID(artsy.getArtworkID());
		}
		
		Cart cart = new Cart();
		cart.setUser(user);
		cart.setArtwork(art);
		updateTotal(cart);
		cart = cartRepository.save(cart);
		return cart;
	}
	
	/**
	 * get cart from database 
	 * @param cartID 
	 * @return cart object from database
	 */
	@Transactional
	public Cart getCart(int cartID) {
		Cart cart = cartRepository.findCartByCartID(cartID);
		return cart;
	}
	
	/**
	 * get cart associated to user in database
	 * @param user user linked to cart
	 * @return cart associated with user
	 */
	@Transactional
	public Cart getCartFromUser(User user) {
		if (user == null)
			throw new IllegalArgumentException("user cannot be null");
		return cartRepository.findCartByUser(user);
	}
	
	/**
	 * delete cart from database
	 * @param cartID 
	 * @return whether the operation was successful
	 */
	@Transactional
	public boolean deleteCart(int cartID) {
		User user = cartRepository.findCartByCartID(cartID).getUser();
		user.setCart(null);
		return cartRepository.deleteCartByCartID(cartID);
	}
	
	/**
	 * delete cart object and remove it from the database
	 * @param cart cart object to delete
	 */
	@Transactional
	public void deleteCart(Cart cart) {
		if (cart == null)
			throw new IllegalArgumentException("cart cannot be null");
		
		cart.getUser().setCart(null);
		cart.setUser(null);
		cartRepository.delete(cart);
	}
	
	/**
	 * removes art from a cart
	 * @param cart cart to remove from
	 * @param art art to remove from cart
	 * @return whether the art was successfully removed from the cart
	 */
	@Transactional
	public boolean removeFromCart(Cart cart, Artwork art) {
		if (cart == null) 															//check that user parameter is not null
			throw new IllegalArgumentException("cart cannot be null");
		if (art == null) 															//check that art parameter is not null
			throw new IllegalArgumentException("artwork cannot be null");
		if(artworkRepository.findArtworkByArtworkID(art.getArtworkID())==null) 		//invalid artwork is passed
			throw new IllegalArgumentException("invalid artwork");
		
		boolean b = cart.getArtwork().remove(art);
		updateTotal(cart);
		cartRepository.save(cart);
		return b;
	}
	
	/**
	 * removes a collection of art from a cart
	 * @param cart cart to remove from
	 * @param art collection to remove from cart
	 * @return whether the art was successfully removed
	 */
	@Transactional
	public Set<Artwork> removeFromCart(Cart cart, Set<Artwork> art) {
		if (cart == null)															//must check parameter is not null
			throw new IllegalArgumentException("cart cannot be null");
		if (art == null)															//must check parameter is not null
			throw new IllegalArgumentException("set<artwork> cannot be null");	
		
		//this will only add valid artworks
		Set<Artwork> filteredSet = art.stream()
                .filter(s -> artworkRepository.findArtworkByArtworkID(s.getArtworkID())!=null)
                .collect(Collectors.toSet());
		
		boolean b = cart.getArtwork().removeAll(filteredSet);
		updateTotal(cart);
		cartRepository.save(cart);
		return filteredSet; //these are those which are removed
	}
	
	/**
	 * add a collection of artwork to a cart
	 * @param cart cart to add to 
	 * @param art collection to add to cart
	 * @return whether the operation was successful
	 */
	@Transactional
	public Set<Artwork> addToCart(Cart cart, Set<Artwork> art) {
		if (cart == null)															//must check parameter is not null
			throw new IllegalArgumentException("cart cannot be null");
		if (art == null)															//must check parameter is not null
			throw new IllegalArgumentException("set<artwork> cannot be null");
		//make sure all artwork are valid
		
		//this will only add valid artworks
		Set<Artwork> filteredSet = art.stream()
                .filter(s -> artworkRepository.findArtworkByArtworkID(s.getArtworkID())!=null)
                .collect(Collectors.toSet());
		
		
		updateTotal(cart);
		cartRepository.save(cart);
		return filteredSet;
	}
	
	/**
	 * add art to a cart
	 * @param cart cart to add to
	 * @param art art to add to cart
	 * @return whether the operation was successful
	 */
	@Transactional
	public boolean addToCart(Cart cart, Artwork art) {
		if (cart == null) 															//must check parameter is not null
			throw new IllegalArgumentException("cart cannot be null");
		if (art == null) 															//must check parameter is not null
			throw new IllegalArgumentException("artwork cannot be null");
		
		boolean b = cart.getArtwork().add(art);
		updateTotal(cart);
		cartRepository.save(cart);
		return b;
	}
	
	/**
	 * iterate through all artwork associated with a cart and sum up all their costs. This sum is used to update the cart.total attribute
	 * @param cart cart whose total will be updated
	 */
	private void updateTotal(Cart cart) {
		List<Artwork> arts = toList(cart.getArtwork());
		if (arts.isEmpty()) {
			cart.setTotalCost(0.0);
			return;
		}
		double total = 0;
		
		for (Artwork a : arts) {
			total += a.getWorth();
		}
		cart.setTotalCost(total);
	}
	
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		
		if (iterable != null) {
			for (T t : iterable) {
				resultList.add(t);
			}
		}
		return resultList;
	}
}
