package ca.mcgill.ecse321.projectgroup13.services;


import ca.mcgill.ecse321.projectgroup13.dao.AddressRepository;
import ca.mcgill.ecse321.projectgroup13.dao.OrderRepository;
import ca.mcgill.ecse321.projectgroup13.dao.ShipmentRepository;
import ca.mcgill.ecse321.projectgroup13.dao.UserRepository;
import ca.mcgill.ecse321.projectgroup13.dto.ShipmentDto;
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
    @Autowired
    private OrderService orderService;
    
    

    //create a new shipment
    @Transactional
    public Shipment createShipment(Order order, Address address, ShipmentStatus status, Date estimatedDateOfArrival, Time estimatedTimeOfArrival, boolean isDelivery) {
    	
        if (address == null||addressRepo.findAddressByAddressID(address.getAddressID())==null)
            throw new IllegalArgumentException("invalid address");
        if (order == null||orderRepo.findOrderByOrderID(order.getOrderID())==null)
            throw new IllegalArgumentException("invalid order");
        if (status == null)
            throw new IllegalArgumentException("invalid status");
        if (estimatedDateOfArrival == null)
            throw new IllegalArgumentException("invalid estimatedDate");
        if (estimatedTimeOfArrival == null)
            throw new IllegalArgumentException("invalid TimeOfArrival");

        Shipment shipment = new Shipment();

        try {
            orderService.addShipmentToOrder(order, shipment);
        } catch (IllegalArgumentException e) {
            System.out.println("Could not create shipment! Error : [" + e.toString() + "]");
            throw new IllegalArgumentException("Could not create shipment! Error : [" + e.toString() + "]");
            //TODO: Maybe we should return a null value instead of throwing an exception???
        }

        //initialization of all required fields
        shipment.setOrder(order);
        shipment.setAddress(address);
        shipment.setShipmentInfo(status);
        shipment.setEstimatedDateOfArrival(estimatedDateOfArrival);
        shipment.setEstimatedTimeOfArrival(estimatedTimeOfArrival);
        shipment.setShipmentMethodIsDelivery(isDelivery);
        shipmentRepo.save(shipment);
        return shipment;
    }


    //get shipment by id
    @Transactional
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

    //get shipment of a single order
    @Transactional
    public Shipment getShipmentOfOrder(Order order) {
        if (order == null||orderRepo.findOrderByOrderID(order.getOrderID())==null) 														//must check parameter is not null
            throw new IllegalArgumentException("order cannot be null");

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
            throw new IllegalArgumentException("user cannot be null");

        Set<Shipment> shipments = new HashSet<Shipment>();
        for (Order order : orderRepo.findOrdersByUser(user)) {
            shipments.add(shipmentRepo.findShipmentByOrder(order));
        }
        return shipments;
    }


    /**
     * service method to get all the shipments in database
     * @return
     */
    @Transactional
    public Set<Shipment> getAllShipments() {
        Set<Shipment> shipments = new HashSet<Shipment>();
        for (Shipment shipment : shipmentRepo.findAll()) {
            shipments.add(shipment);
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
        if (shipment == null) 														//must check parameter is not null
            throw new IllegalArgumentException("shipment cannot be null");
        if (status == null) 														//must check parameter is not null
            throw new IllegalArgumentException("status cannot be null");

        shipment.setShipmentInfo(status);
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
        if (shipment == null) 														//must check parameter is not null
            throw new IllegalArgumentException("shipment cannot be null");
        if (estimatedDate == null) 														//must check parameter is not null
            throw new IllegalArgumentException("estimatedDate cannot be null");

        shipment.setEstimatedDateOfArrival(estimatedDate);
        return shipment;
    }


    /**
     * service method to edit shipment estimated time of arrival
     * @param shipment
     * @param estimatedTime
     * @return
     */
    @Transactional
    public Shipment editShipmentEstimatedTime (Shipment shipment, Time estimatedTime){
        if (shipment == null) 														//must check parameter is not null
            throw new IllegalArgumentException("shipment cannot be null");
        if (estimatedTime == null) 														//must check parameter is not null
            throw new IllegalArgumentException("estimatedTime cannot be null");

        shipment.setEstimatedTimeOfArrival(estimatedTime);
        return shipment;
    }



}