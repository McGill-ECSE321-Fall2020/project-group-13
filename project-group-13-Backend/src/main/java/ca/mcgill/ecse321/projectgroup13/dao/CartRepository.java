package ca.mcgill.ecse321.projectgroup13.dao;

import java.util.List;

import ca.mcgill.ecse321.projectgroup13.model.Cart;
import ca.mcgill.ecse321.projectgroup13.model.User;
import ca.mcgill.ecse321.projectgroup13.model.Artwork;

import org.springframework.data.repository.CrudRepository;

public interface CartRepository extends CrudRepository<Cart, Long>{
	
	List<Cart> findCartbyArtwork(Artwork artwork);
    Cart findCartByUser(User user);
    
}
