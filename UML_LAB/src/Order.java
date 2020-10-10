import javax.persistence.Id;
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
private Set<Artwork> artwork;

@OneToMany(mappedBy="order")
public Set<Artwork> getArtwork() {
   return this.artwork;
}

public void setArtwork(Set<Artwork> artwork1s) {
   this.artwork = artwork1s;
}

private User user;

@ManyToOne(optional=false)
public User getUser() {
   return this.user;
}

public void setUser(User user) {
   this.user = user;
}


private String orderID;

public void setOrderID(String value) {
this.orderID = value;
    }
@Id
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

}
