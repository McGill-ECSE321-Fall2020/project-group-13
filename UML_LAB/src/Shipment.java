import java.sql.Date;
import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Shipment{
private ShipmentStatus shipmentStatus;
   
   public void setShipmentStatus(ShipmentStatus value) {
this.shipmentStatus = value;
    }
public ShipmentStatus getShipmentStatus() {
return this.shipmentStatus;
    }
private Time estimatedTimeOfArrival;

public void setEstimatedTimeOfArrival(Time value) {
this.estimatedTimeOfArrival = value;
    }
public Time getEstimatedTimeOfArrival() {
return this.estimatedTimeOfArrival;
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
public String getShipmentID() {
return this.shipmentID;
    }
public Date estimatedDateOfArrival;
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

}
