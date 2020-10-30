package ca.mcgill.ecse321.projectgroup13.dao;

import ca.mcgill.ecse321.projectgroup13.model.Order;
import ca.mcgill.ecse321.projectgroup13.model.Shipment;
import ca.mcgill.ecse321.projectgroup13.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ShipmentRepository extends CrudRepository<Shipment, Long>{
    Shipment findShipmentByShipmentID(int shipmentID);
    //Shipment findShipmentByOrder(Order order);
    Set<Shipment> findShipmentByOrder(Order order);
    //Set<Shipment> findShipmentByUser(User user);
}