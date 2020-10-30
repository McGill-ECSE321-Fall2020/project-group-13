package ca.mcgill.ecse321.projectgroup13.services;


import ca.mcgill.ecse321.projectgroup13.dao.OrderRepository;
import ca.mcgill.ecse321.projectgroup13.dao.ShipmentRepository;
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


    //create a new shipment
    @Transactional
    public Shipment createShipment(Order order, Address address, ShipmentStatus status, Date estimatedDateOfArrival, Time estimatedTimeOfArrival, boolean isDelivery) {
        Shipment shipment = new Shipment();
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
    public User getUserOfShipment(Integer shipmentID){
        Shipment shipment = shipmentRepo.findShipmentByShipmentID(shipmentID);
        User user = shipment.getOrder().getUser();
        return user;
    }

    //get all shipments of a single order since one order could have different shipments (e.g multiple items...)
    @Transactional
    public Set<Shipment> getShipmentsOfOrder(Order order) {
        Set<Shipment> shipments = new HashSet<Shipment>();
        for (Shipment shipment : shipmentRepo.findShipmentByOrder(order)) {
            shipments.add(shipment);
        }
        return shipments;
    }


    /**
     * service method to get all the shipments of a user
     * @param user
     * @return dto shipments
     */
//    @Transactional
//    public Set<Shipment> getShipmentsOfUser(User user) {
//        Set<Shipment> shipments = new HashSet<Shipment>();
//        for (Shipment shipment : shipmentRepo.findOrderByUser(user)) {
//            shipments.add(shipment);
//        }
//        return shipments;
//    }



//    @Transactional
//    public Shipment editShipmentStatus (ShipmentStatus status){
//
//    }



}
