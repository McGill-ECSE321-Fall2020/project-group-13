import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;
import javax.persistence.OneToOne;
import java.util.Set;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;

@Entity
public class Order{
private OrderStatus orderStatus;
   
   public void setOrderStatus(OrderStatus value) {
this.orderStatus = value;
    }
public OrderStatus getOrderStatus() {
return this.orderStatus;
    }
private String orderID;

public void setOrderID(String value) {
this.orderID = value;
    }
public String getOrderID() {
return this.orderID;
    }

private double totalAmount;

public void setTotalAmount(double value) {
this.totalAmount = value;
    }
public double getTotalAmount() {
return this.totalAmount;
    }
private Customer customer1;

@ManyToOne(optional=false)
public Customer getCustomer1() {
   return this.customer1;
}

public void setCustomer1(Customer customer) {
   this.customer1 = customer;
}

private Payment payment;

@OneToOne(mappedBy="order", cascade={CascadeType.ALL})
public Payment getPayment() {
   return this.payment;
}

public void setPayment(Payment payment) {
   this.payment = payment;
}

private Set<Shipment> shipment;

@OneToMany(mappedBy="order", cascade={CascadeType.ALL})
public Set<Shipment> getShipment() {
   return this.shipment;
}

public void setShipment(Set<Shipment> shipments) {
   this.shipment = shipments;
}

private Set<Artwork> artwork;

@ManyToMany
public Set<Artwork> getArtwork() {
   return this.artwork;
}

public void setArtwork(Set<Artwork> artworks) {
   this.artwork = artworks;
}

}
