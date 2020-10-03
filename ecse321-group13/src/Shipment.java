import java.sql.Date;
import java.sql.Time;

import javax.persistence.Entity;
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
private ShipmentMethod shipmentMethod;

public void setShipmentMethod(ShipmentMethod value) {
this.shipmentMethod = value;
    }
public ShipmentMethod getShipmentMethod() {
return this.shipmentMethod;
    }
private String shipmentID;

public void setShipmentID(String value) {
this.shipmentID = value;
    }
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

@OneToOne(optional=false)
public Address getAddress() {
   return this.address;
}

public void setAddress(Address address) {
   this.address = address;
}

}
