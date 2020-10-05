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
private OrderStatus status;
   
   public void setStatus(OrderStatus value) {
this.status = value;
    }
public OrderStatus getStatus() {
return this.status;
    }
private Set<Artwork> artwork1;

@OneToMany(mappedBy="order")
public Set<Artwork> getArtwork1() {
   return this.artwork1;
}

public void setArtwork1(Set<Artwork> artwork1s) {
   this.artwork1 = artwork1s;
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

private Set<Artwork> artwork;

@ManyToMany
public Set<Artwork> getArtwork() {
   return this.artwork;
}

public void setArtwork(Set<Artwork> artworks) {
   this.artwork = artworks;
}

}
