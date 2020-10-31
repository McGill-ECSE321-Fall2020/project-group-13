package ca.mcgill.ecse321.projectgroup13.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.CascadeType;
import javax.persistence.OneToOne;
import java.util.Set;
import javax.persistence.OneToMany;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Table(name="orders")
public class Order{
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

private Shipment shipment;
@OneToOne(mappedBy="order", cascade={CascadeType.ALL})
public Shipment getShipment() {
   return this.shipment;
}

public void setShipment(Shipment shipment) {
   this.shipment = shipment;
}

private Integer orderID;

public void setOrderID(Integer value) {
this.orderID = value;
    }
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
public Integer getOrderID() {
return this.orderID;
    }
private Set<Artwork> artwork;

@OneToMany(mappedBy="order")
public Set<Artwork> getArtwork() {
   return this.artwork;
}

public void setArtwork(Set<Artwork> artworks) {
   this.artwork = artworks;
}

private User user;

@ManyToOne(optional=false)
public User getUser() {
   return this.user;
}

public void setUser(User user) {
   this.user = user;
}

private OrderStatus orderStatus;

public void setOrderStatus(OrderStatus value) {
this.orderStatus = value;
    }
public OrderStatus getOrderStatus() {
return this.orderStatus;
    }
@Override
public int hashCode ()
{
final int prime = 31;
   	int result = 1;
   	result = prime * result + ((artwork == null) ? 0 : artwork.hashCode());
   	result = prime * result + ((orderID == null) ? 0 : orderID.hashCode());
   	result = prime * result + ((orderStatus == null) ? 0 : orderStatus.hashCode());
   	result = prime * result + ((payment == null) ? 0 : payment.hashCode());
   	long temp;
   	temp = Double.doubleToLongBits(totalAmount);
   	result = prime * result + (int) (temp ^ (temp >>> 32));
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
   	Order other = (Order) obj;
   	if (artwork == null) {
   		if (other.artwork != null)
   			return false;
   	} else if (!artwork.equals(other.artwork))
   		return false;
   	if (orderID == null) {
   		if (other.orderID != null)
   			return false;
   	} else if (!orderID.equals(other.orderID))
   		return false;
   	if (orderStatus != other.orderStatus)
   		return false;
   	if (payment == null) {
   		if (other.payment != null)
   			return false;
   	} else if (!payment.equals(other.payment))
   		return false;
   	if (Double.doubleToLongBits(totalAmount) != Double.doubleToLongBits(other.totalAmount))
   		return false;
   	return true;
   }
}
