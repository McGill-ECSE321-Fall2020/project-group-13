package ca.mcgill.ecse321.projectgroup13.model;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Shipment{
private ShipmentStatus shipmentInfo;
   
   public void setShipmentInfo(ShipmentStatus value) {
this.shipmentInfo = value;
    }
public ShipmentStatus getShipmentInfo() {
return this.shipmentInfo;
    }
private boolean shipmentMethodIsDelivery;

public void setShipmentMethodIsDelivery(boolean value) {
this.shipmentMethodIsDelivery = value;
    }
public boolean isShipmentMethodIsDelivery() {
return this.shipmentMethodIsDelivery;
    }

private String shipmentID;

public void setShipmentID(String value) {
this.shipmentID = value;
    }
@Id
public String getShipmentID() {
return this.shipmentID;
    }
  private Date estimatedDateOfArrival;
  private Time estimatedTimeOfArrival;
private Order order;

@ManyToOne(optional=false)
public Order getOrder() {
   return this.order;
}

public void setOrder(Order order) {
   this.order = order;
}

private Address address;

@ManyToOne(optional=false)
public Address getAddress() {
   return this.address;
}

public void setAddress(Address address) {
   this.address = address;
}
@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((address == null) ? 0 : address.hashCode());
	result = prime * result + ((estimatedDateOfArrival == null) ? 0 : estimatedDateOfArrival.hashCode());
	result = prime * result + ((estimatedTimeOfArrival == null) ? 0 : estimatedTimeOfArrival.hashCode());
	result = prime * result + ((order == null) ? 0 : order.hashCode());
	result = prime * result + ((shipmentID == null) ? 0 : shipmentID.hashCode());
	result = prime * result + ((shipmentInfo == null) ? 0 : shipmentInfo.hashCode());
	result = prime * result + (shipmentMethodIsDelivery ? 1231 : 1237);
	return result;
}
@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	Shipment other = (Shipment) obj;
	if (address == null) {
		if (other.address != null)
			return false;
	} else if (!address.equals(other.address))
		return false;
	if (estimatedDateOfArrival == null) {
		if (other.estimatedDateOfArrival != null)
			return false;
	} else if (!estimatedDateOfArrival.equals(other.estimatedDateOfArrival))
		return false;
	if (estimatedTimeOfArrival == null) {
		if (other.estimatedTimeOfArrival != null)
			return false;
	} else if (!estimatedTimeOfArrival.equals(other.estimatedTimeOfArrival))
		return false;
	if (order == null) {
		if (other.order != null)
			return false;
	} else if (!order.equals(other.order))
		return false;
	if (shipmentID == null) {
		if (other.shipmentID != null)
			return false;
	} else if (!shipmentID.equals(other.shipmentID))
		return false;
	if (shipmentInfo != other.shipmentInfo)
		return false;
	if (shipmentMethodIsDelivery != other.shipmentMethodIsDelivery)
		return false;
	return true;
}



}
