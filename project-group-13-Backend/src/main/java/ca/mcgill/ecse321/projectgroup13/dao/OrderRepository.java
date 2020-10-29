package ca.mcgill.ecse321.projectgroup13.dao;

import java.util.List;

import ca.mcgill.ecse321.projectgroup13.model.Order;
import ca.mcgill.ecse321.projectgroup13.model.User;
import ca.mcgill.ecse321.projectgroup13.model.Artwork;

import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long>{
	
	List<Order> findOrderByArtwork(Artwork artwork);
    List<Order> findOrderByUser(User user);
    Order findOrderByOrderID(Integer orderID);
    
}
