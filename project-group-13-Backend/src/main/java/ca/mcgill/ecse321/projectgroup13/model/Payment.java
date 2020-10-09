package ca.mcgill.ecse321.projectgroup13.model;

import java.sql.Time;
import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Payment{
private double totalAmount;
   
   public void setTotalAmount(double value) {
this.totalAmount = value;
    }
public double getTotalAmount() {
return this.totalAmount;
    }
private Date paymentDate;

public void setPaymentDate(Date value) {
this.paymentDate = value;
    }
public Date getPaymentDate() {
return this.paymentDate;
    }
private Time paymentTime;

public void setPaymentTime(Time value) {
this.paymentTime = value;
    }
public Time getPaymentTime() {
return this.paymentTime;
    }
private double cardNumber;

public void setCardNumber(double value) {
this.cardNumber = value;
    }
public double getCardNumber() {
return this.cardNumber;
    }
private Date expirationDate;

public void setExpirationDate(Date value) {
this.expirationDate = value;
    }
public Date getExpirationDate() {
return this.expirationDate;
    }
private String nameOnCard;

public void setNameOnCard(String value) {
this.nameOnCard = value;
    }
public String getNameOnCard() {
return this.nameOnCard;
    }
private int cvv;

public void setCvv(int value) {
this.cvv = value;
    }
public int getCvv() {
return this.cvv;
    }
private String paymentID;

public void setPaymentID(String value) {
this.paymentID = value;
    }

@Id
public String getPaymentID() {
return this.paymentID;
    }
private Order order;

@OneToOne(optional=false)
public Order getOrder() {
   return this.order;
}

public void setOrder(Order order) {
   this.order = order;
}
@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	long temp;
	temp = Double.doubleToLongBits(cardNumber);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	result = prime * result + cvv;
	result = prime * result + ((expirationDate == null) ? 0 : expirationDate.hashCode());
	result = prime * result + ((nameOnCard == null) ? 0 : nameOnCard.hashCode());
	//result = prime * result + ((order == null) ? 0 : order.hashCode());
	result = prime * result + ((paymentDate == null) ? 0 : paymentDate.hashCode());
	result = prime * result + ((paymentID == null) ? 0 : paymentID.hashCode());
	result = prime * result + ((paymentTime == null) ? 0 : paymentTime.hashCode());
	temp = Double.doubleToLongBits(totalAmount);
	result = prime * result + (int) (temp ^ (temp >>> 32));
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
	Payment other = (Payment) obj;
	if (Double.doubleToLongBits(cardNumber) != Double.doubleToLongBits(other.cardNumber))
		return false;
	if (cvv != other.cvv)
		return false;
	if (expirationDate == null) {
		if (other.expirationDate != null)
			return false;
	} else if (!expirationDate.equals(other.expirationDate))
		return false;
	if (nameOnCard == null) {
		if (other.nameOnCard != null)
			return false;
	} else if (!nameOnCard.equals(other.nameOnCard))
		return false;
//	if (order == null) {
//		if (other.order != null)
//			return false;
//	} else if (!order.equals(other.order))
//		return false;
	if (paymentDate == null) {
		if (other.paymentDate != null)
			return false;
	} else if (!paymentDate.equals(other.paymentDate))
		return false;
	if (paymentID == null) {
		if (other.paymentID != null)
			return false;
	} else if (!paymentID.equals(other.paymentID))
		return false;
	if (paymentTime == null) {
		if (other.paymentTime != null)
			return false;
	} else if (!paymentTime.equals(other.paymentTime))
		return false;
	if (Double.doubleToLongBits(totalAmount) != Double.doubleToLongBits(other.totalAmount))
		return false;
	return true;
}


}
