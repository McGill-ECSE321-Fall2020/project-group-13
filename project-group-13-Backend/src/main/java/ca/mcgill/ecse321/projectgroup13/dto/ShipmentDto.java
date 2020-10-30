package ca.mcgill.ecse321.projectgroup13.dto;

import ca.mcgill.ecse321.projectgroup13.model.ShipmentStatus;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.sql.Date;
import java.sql.Time;


public class ShipmentDto {

    private int shipmentID;
    private ShipmentStatus shipmentInfo;
    private Date estimatedDateOfArrival;
    private Time estimatedTimeOfArrival;
    private OrderDto order;
    private AddressDto address;
    private boolean shipmentMethodIsDelivery;



    public ShipmentDto(){

    }

    public ShipmentDto(int shipmentID, ShipmentStatus shipmentInfo, Date estimatedDateOfArrival, Time estimatedTimeOfArrival, OrderDto order, AddressDto address, boolean shipmentMethodIsDelivery){
        this.shipmentID = shipmentID;
        this.shipmentInfo = shipmentInfo;
        this.estimatedDateOfArrival = estimatedDateOfArrival;
        this.estimatedTimeOfArrival = estimatedTimeOfArrival;
        this.order = order;
        this.address = address;
        this.shipmentMethodIsDelivery = shipmentMethodIsDelivery;
    }


    public void setShipmentInfo(ShipmentStatus value) {
        this.shipmentInfo = value;
    }

    public ShipmentStatus getShipmentInfo() {
        return this.shipmentInfo;
    }

    public void setShipmentMethodIsDelivery(boolean value) {
        this.shipmentMethodIsDelivery = value;
    }

    public boolean isShipmentMethodIsDelivery() {
        return this.shipmentMethodIsDelivery;
    }

    public Integer getShipmentID() {
        return this.shipmentID;
    }

    public OrderDto getOrder() {
        return this.order;
    }

    public void setOrder(OrderDto order) {
        this.order = order;
    }

    public AddressDto getAddress() {
        return this.address;
    }

    public void setAddress(AddressDto address) {
        this.address = address;
    }


    public void setEstimatedDateOfArrival(Date estimatedDateOfArrival) {
        this.estimatedDateOfArrival = estimatedDateOfArrival;
    }

    public void setEstimatedTimeOfArrival(Time estimatedTimeOfArrival) {
        this.estimatedTimeOfArrival = estimatedTimeOfArrival;
    }

    public Date getEstimatedDateOfArrival() {
        return estimatedDateOfArrival;
    }

    public Time getEstimatedTimeOfArrival() {
        return estimatedTimeOfArrival;
    }
}