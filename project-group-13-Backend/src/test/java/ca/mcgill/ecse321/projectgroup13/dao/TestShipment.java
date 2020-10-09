package ca.mcgill.ecse321.projectgroup13.dao;

import ca.mcgill.ecse321.projectgroup13.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration
public class TestShipment {

    /**
     * test Class for Shipment
     */

    @Autowired
    private ShipmentRepository shipmentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private AddressRepository addressRepository;


    // this is to clear database prior to every run
    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        shipmentRepository.deleteAll();
        orderRepository.deleteAll();
        addressRepository.deleteAll();
        userRepository.deleteAll();
    }

    /**
     * Testing for Shipment Persistence
     */

    @Test
    public void testPersistAndLoadShipment() {

        //need instances of these classes
        Order order = new Order();
        Address address = new Address();
        User user = new User();
        Shipment shipment = new Shipment();

        //parameters for order
        String orderID = "myOrderID";
        OrderStatus orderStatus = OrderStatus.Shipped;
        Double totalAmount = 100.02;

        //parameters for address
        String city = "city";
        String country = "country";
        String postalCode = "postalCode";
        String province = "province";
        String street1 = "street1";
        String street2 = "street2";
        String addressID = "TESTaddressID";


        //parameters for user
        String username = "Cesar";
        String password = "myPassW0rd";
        String email = "myEmail@gmail.com";
        String profilePicURL = "//yes.com/img.jpg";
        Set<Order> orders= new HashSet<>();


        //parameters for shipment
        String shipmentID = "myShipmentID";
        ShipmentStatus shipmentInfo = ShipmentStatus.OnRoute;
        Date date = Date.valueOf("2020-02-02");

        //set order parameters
        order.setOrderID(orderID);
        order.setTotalAmount(totalAmount);
        order.setUser(user);

        //set address parameters
        address.setCity(city);
        address.setCountry(country);
        address.setPostalCode(postalCode);
        address.setProvince(province);
        address.setStreetAddress1(street1);
        address.setStreetAddress2(street2);
        address.setAddressID(addressID);
        address.setUser(user);

        //set user parameters
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setProfilePictureURL(profilePicURL);
        user.setOrder(orders);


        //set shipment parameters
        shipment.setShipmentID(shipmentID);
        shipment.setShipmentInfo(shipmentInfo);
        shipment.setEstimatedDateOfArrival(date);
        shipment.setShipmentMethodIsDelivery(true);
        shipment.setOrder(order);
        shipment.setAddress(address);


        //save instances to database
        user = userRepository.save(user);
        address = addressRepository.save(address);
        order = orderRepository.save(order);
        shipment = shipmentRepository.save(shipment);

        //restore shipment instance from database
        Shipment shipmentPersisted = shipmentRepository.findShipmentByOrder(order);

        //assert if instance retrieved from database equals the original
        assertEquals(true, shipment.equals(shipmentPersisted));
    }
}