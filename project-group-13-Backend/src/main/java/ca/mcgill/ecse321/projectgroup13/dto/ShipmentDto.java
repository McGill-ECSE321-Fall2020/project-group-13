package ca.mcgill.ecse321.projectgroup13.dto;

import ca.mcgill.ecse321.projectgroup13.model.ShipmentStatus;

import java.sql.Date;
import java.sql.Time;


public class ShipmentDto {

    private int shipmentID;
    private ShipmentStatus shipmentInfo;
    private Date estimatedDateOfArrival;
    private Time estimatedTimeOfArrival;
    private AddressDto address;


    public ShipmentDto(){

    }

    public ShipmentDto(int shipmentID, ShipmentStatus shipmentInfo, Date estimatedDateOfArrival, Time estimatedTimeOfArrival, AddressDto address){
        this.shipmentID = shipmentID;
        this.shipmentInfo = shipmentInfo;
        this.estimatedDateOfArrival = estimatedDateOfArrival;
        this.estimatedTimeOfArrival = estimatedTimeOfArrival;
        this.address = address;
    }


    public void setShipmentInfo(ShipmentStatus value) {
        this.shipmentInfo = value;
    }

    public ShipmentStatus getShipmentInfo() {
        return this.shipmentInfo;
    }


    public Integer getShipmentID() {
        return this.shipmentID;
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