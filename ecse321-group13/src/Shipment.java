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
private ShipmentMethod shipmentMethod;

public void setShipmentMethod(ShipmentMethod value) {
this.shipmentMethod = value;
    }
public ShipmentMethod getShipmentMethod() {
return this.shipmentMethod;
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
