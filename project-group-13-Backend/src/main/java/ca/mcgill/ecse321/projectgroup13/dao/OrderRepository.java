package ca.mcgill.ecse321.projectgroup13.dao;

import java.util.List;
import java.util.Set;

import ca.mcgill.ecse321.projectgroup13.model.Order;
import ca.mcgill.ecse321.projectgroup13.model.User;
import ca.mcgill.ecse321.projectgroup13.model.Artwork;

import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long>{
	
	Set<Order> findOrderByArtwork(Artwork artwork);
    Order findOrderByOrderID(Integer orderID);
    Set<Order> findOrdersByUser(User user);
    
}
