package ca.mcgill.ecse321.projectgroup13.model;

import java.sql.Time;
import java.sql.Date;
import javax.persistence.Entity;
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

}
