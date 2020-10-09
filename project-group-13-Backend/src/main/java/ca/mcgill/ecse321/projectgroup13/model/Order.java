package ca.mcgill.ecse321.projectgroup13.model;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import java.util.Set;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;

@Entity
@Table(name="orders")
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
@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((artwork == null) ? 0 : artwork.hashCode());
	result = prime * result + ((orderID == null) ? 0 : orderID.hashCode());
	result = prime * result + ((orderStatus == null) ? 0 : orderStatus.hashCode());
	result = prime * result + ((payment == null) ? 0 : payment.hashCode());
	result = prime * result + ((shipment == null) ? 0 : shipment.hashCode());
	long temp;
	temp = Double.doubleToLongBits(totalAmount);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	result = prime * result + ((user == null) ? 0 : user.hashCode());
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
	if (shipment == null) {
		if (other.shipment != null)
			return false;
	} else if (!shipment.equals(other.shipment))
		return false;
	if (Double.doubleToLongBits(totalAmount) != Double.doubleToLongBits(other.totalAmount))
		return false;
	if (user == null) {
		if (other.user != null)
			return false;
	} else if (!user.equals(other.user))
		return false;
	return true;
}

}
