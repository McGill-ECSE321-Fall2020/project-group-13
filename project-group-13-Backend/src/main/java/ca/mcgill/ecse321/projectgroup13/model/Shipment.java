package ca.mcgill.ecse321.projectgroup13.model;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Shipment{
private Order order;

@OneToOne(optional=false)
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

/**
 * <pre>
 *           1..1     1..1
 * Shipment ------------------------> Date
 *           &lt;       estimatedDateOfArrival
 * </pre>
 */
private Date estimatedDateOfArrival;

public void setEstimatedDateOfArrival(Date value) {
   this.estimatedDateOfArrival = value;
}

public Date getEstimatedDateOfArrival() {
   return this.estimatedDateOfArrival;
}

private Integer shipmentID;

public void setShipmentID(Integer value) {
this.shipmentID = value;
    }
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
public Integer getShipmentID() {
return this.shipmentID;
    }

/**
 * <pre>
 *           1..1     1..1
 * Shipment ------------------------> Time
 *           &lt;       estimatedTimeOfArrival
 * </pre>
 */
private Time estimatedTimeOfArrival;

public void setEstimatedTimeOfArrival(Time value) {
   this.estimatedTimeOfArrival = value;
}

public Time getEstimatedTimeOfArrival() {
   return this.estimatedTimeOfArrival;
}

private ShipmentStatus shipmentInfo;

public void setShipmentInfo(ShipmentStatus value) {
this.shipmentInfo = value;
    }
public ShipmentStatus getShipmentInfo() {
return this.shipmentInfo;
    }
@Override
public int hashCode ()
{
final int prime = 31;
   	int result = 1;
   	result = prime * result + ((estimatedDateOfArrival == null) ? 0 : estimatedDateOfArrival.hashCode());
   	result = prime * result + ((estimatedTimeOfArrival == null) ? 0 : estimatedTimeOfArrival.hashCode());
   	result = prime * result + ((order == null) ? 0 : order.hashCode());
   	result = prime * result + ((shipmentID == null) ? 0 : shipmentID.hashCode());
   	result = prime * result + ((shipmentInfo == null) ? 0 : shipmentInfo.hashCode());
   	return result;
   }
@Override
public boolean equals (Object obj)
{
if (this == obj)
   		return true;
   	if (obj == null)
   		return false;
   	if (getClass() != obj.getClass())
   		return false;
   	Shipment other = (Shipment) obj;
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
   	return true;
   }
}
