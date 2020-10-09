package ca.mcgill.ecse321.projectgroup13.dao;

import ca.mcgill.ecse321.projectgroup13.model.Cart;
import ca.mcgill.ecse321.projectgroup13.model.User;
import org.springframework.data.repository.CrudRepository;

public interface CartRepository extends CrudRepository<Cart, Long>{
    Cart findCartByUser(User user);
    Cart findCartByCartID(String cartID);
}
