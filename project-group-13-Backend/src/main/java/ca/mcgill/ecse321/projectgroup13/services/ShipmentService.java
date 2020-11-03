package ca.mcgill.ecse321.projectgroup13.services;


import ca.mcgill.ecse321.projectgroup13.dao.AddressRepository;
import ca.mcgill.ecse321.projectgroup13.dao.OrderRepository;
import ca.mcgill.ecse321.projectgroup13.dao.ShipmentRepository;
import ca.mcgill.ecse321.projectgroup13.dao.UserRepository;
import ca.mcgill.ecse321.projectgroup13.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.sql.Time;
import java.util.HashSet;
import java.util.Set;

@Service
public class ShipmentService {

    @Autowired
    private ShipmentRepository shipmentRepo;
    @Autowired
    private OrderRepository orderRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private AddressRepository addressRepo;
    //import other services
    
    

    //create a new shipment
    @Transactional
    /**
     * creates shipment
     * @param orderId
     * @param addressId
     * @param estimatedDateOfArrival
     * @param estimatedTimeOfArrival
     * @return
     */
    public Shipment createShipment(int orderId, int addressId, Date estimatedDateOfArrival, Time estimatedTimeOfArrival) {

        Order order = orderRepo.findOrderByOrderID(orderId);
        Address address = addressRepo.findAddressByAddressID(addressId);
        ShipmentStatus status = ShipmentStatus.OnRoute;

        if (address == null)
            throw new IllegalArgumentException("address cannot be null");
        if (order == null)
            throw new IllegalArgumentException("order cannot be null");
        if (status == null)
            throw new IllegalArgumentException("invalid status");
        if (estimatedDateOfArrival == null)
            throw new IllegalArgumentException("invalid estimatedDate");
        if (estimatedTimeOfArrival == null)
            throw new IllegalArgumentException("invalid TimeOfArrival");

        Shipment shipment = new Shipment();

        //initialization of all required fields
        shipment.setOrder(order);
        shipment.setAddress(address);
        shipment.setShipmentInfo(status);
        shipment.setEstimatedDateOfArrival(estimatedDateOfArrival);
        shipment.setEstimatedTimeOfArrival(estimatedTimeOfArrival);

        shipmentRepo.save(shipment);
        return shipment;
    }


    //get shipment by id
    @Transactional
    /**
     * gets shipment
     * @param shipmentID
     * @return shipment
     */
    public Shipment getShipment(int shipmentID){
        Shipment shipment = shipmentRepo.findShipmentByShipmentID(shipmentID);
        return shipment;
    }


    //get user of shipment
    @Transactional
    public User getUserOfShipment(int shipmentID){
        Shipment shipment = shipmentRepo.findShipmentByShipmentID(shipmentID);
        User user = shipment.getOrder().getUser();
        return user;
    }


    //get address of shipment
    public Address getAddressOfShipment(int shipmentID){
        Shipment shipment = shipmentRepo.findShipmentByShipmentID(shipmentID);
        Address address = shipment.getAddress();
        return address;
    }

    //get shipment of a single order
    @Transactional
    public Shipment getShipmentOfOrder(Order order) {
        if (order == null||orderRepo.findOrderByOrderID(order.getOrderID())==null) 														//must check parameter is not null
            throw new IllegalArgumentException("invalid order");

        Shipment shipment = shipmentRepo.findShipmentByOrder(order);

        return shipment;
    }



    /**
     * service method to get all the shipments of a user
     * @param user
     * @return dto shipments
     */
    @Transactional
    public Set<Shipment> getShipmentsOfUser(User user) {
        if (user == null||userRepo.findUserByUsername(user.getUsername())==null) 														//must check parameter is not null
            throw new IllegalArgumentException("invalid user");

        Set<Shipment> shipments = new HashSet<Shipment>();
        for (Order order : orderRepo.findOrdersByUser(user)) {
            shipments.add(shipmentRepo.findShipmentByOrder(order));
        }
        return shipments;
    }



    /**
     * service method to edit shipment status
     * @param shipment
     * @param status
     * @return
     */
    @Transactional
    public Shipment editShipmentStatus (Shipment shipment, ShipmentStatus status){
        if (shipment == null||status == null) 														//must check parameter is not null
            throw new IllegalArgumentException("null argument");

        shipment.setShipmentInfo(status);
        shipmentRepo.save(shipment);
        return shipment;
    }



    /**
     * service method to edit shipment estimated date of arrival
     * @param shipment
     * @param estimatedDate
     * @return
     */
    @Transactional
    public Shipment editShipmentEstimatedDate (Shipment shipment, Date estimatedDate){
        if (shipment == null||estimatedDate==null) 														//must check parameter is not null
            throw new IllegalArgumentException("null argument");

        shipment.setEstimatedDateOfArrival(estimatedDate);
        shipmentRepo.save(shipment);
        return shipment;
    }


    /**
     * service method to edit shipment estimated time of arrival
     * @param shipment
     * @param estimatedTime
     * @return
     */
    @Transactional
    public Shipment editShipmentEstimatedTime (Shipment shipment, Time estimatedTime) throws IllegalArgumentException {
        if (shipment == null||estimatedTime==null) {														//must check parameter is not null
            throw new IllegalArgumentException("null argument");
        }
     
        shipment.setEstimatedTimeOfArrival(estimatedTime);
        shipmentRepo.save(shipment);
        return shipment;
    }



}