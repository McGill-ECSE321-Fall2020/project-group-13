package ca.mcgill.ecse321.projectgroup13.services;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.projectgroup13.dao.ArtworkRepository;
import ca.mcgill.ecse321.projectgroup13.dao.CartRepository;
import ca.mcgill.ecse321.projectgroup13.dao.PaymentRepository;
import ca.mcgill.ecse321.projectgroup13.dao.ShipmentRepository;
import ca.mcgill.ecse321.projectgroup13.dao.UserRepository;
import ca.mcgill.ecse321.projectgroup13.model.Artwork;
import ca.mcgill.ecse321.projectgroup13.model.Cart;
import ca.mcgill.ecse321.projectgroup13.model.User;

@Service
public class CartService {
	@Autowired
	ArtworkRepository artworkRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PaymentRepository paymentRepository;
	
	@Autowired
	ShipmentRepository shipmentRepository;
	
	@Autowired
	CartRepository cartRepository;
	
	@Transactional
	public Cart createCart(User user) {
		if (user == null)
			throw new IllegalArgumentException("user cannot be null");
		Cart cart = new Cart();
		cart.setUser(user);
		cart.setTotalCost(0);
		cart.setArtwork(new HashSet<Artwork>());
		cart = cartRepository.save(cart);
		return cart;
	}
	
	@Transactional
	public Cart getCart(int cartID) {
		Cart cart = cartRepository.findCartByCartID(cartID);
		return cart;
	}
	
	@Transactional
	public Cart getCartFromUser(User user) {
		if (user == null)
			throw new IllegalArgumentException("user cannot be null");
		if (user.getCart() == null)
			throw new IllegalArgumentException("user cart attribute cannot be null");
		
		return user.getCart();
	}
	
	@Transactional
	public boolean deleteCart(int cartID) {
		return cartRepository.deleteCartByCartID(cartID);
	}
	
	@Transactional
	public void deleteCart(Cart cart) {
		cart.getUser().setCart(null);
		cart.setUser(null);
		cartRepository.delete(cart);
	}
	
}
