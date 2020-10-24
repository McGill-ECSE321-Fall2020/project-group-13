package ca.mcgill.ecse321.projectgroup13.dto;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Id;
import javax.persistence.OneToOne;

import ca.mcgill.ecse321.projectgroup13.model.Order;

public class PaymentDto {
	private double totalAmount;
	private Date paymentDate;
	private Time paymentTime;
	private double cardNumber;
	private Date expirationDate;
	private String nameOnCard;
	private int cvv;
	private String paymentID;
	private Order order;
	public PaymentDto() {
		
	}
	public PaymentDto(double totalAmount, Date paymentDate,Time paymentTime,double cardNumber, Date expirationDate, String nameOnCard, int cvv,
			String paymentID, Order order) {
		this.totalAmount = totalAmount;
		this.paymentDate = paymentDate;
		this.paymentTime = paymentTime;
		this.cardNumber = cardNumber;
		this.expirationDate = expirationDate;
		this.nameOnCard = nameOnCard;
		this.cvv = cvv;
		this.paymentID = paymentID;
		this.order = order;
		
	}
	public void setTotalAmount(double value) {
	this.totalAmount = value;
	    }
	public double getTotalAmount() {
	return this.totalAmount;
	    }
	

	public void setPaymentDate(Date value) {
	this.paymentDate = value;
	    }
	public Date getPaymentDate() {
	return this.paymentDate;
	    }
	

	public void setPaymentTime(Time value) {
	this.paymentTime = value;
	    }
	public Time getPaymentTime() {
	return this.paymentTime;
	    }
	

	public void setCardNumber(double value) {
	this.cardNumber = value;
	    }
	public double getCardNumber() {
	return this.cardNumber;
	    }
	

	public void setExpirationDate(Date value) {
	this.expirationDate = value;
	    }
	public Date getExpirationDate() {
	return this.expirationDate;
	    }
	

	public void setNameOnCard(String value) {
	this.nameOnCard = value;
	    }
	public String getNameOnCard() {
	return this.nameOnCard;
	    }
	

	public void setCvv(int value) {
	this.cvv = value;
	    }
	public int getCvv() {
	return this.cvv;
	    }
	

	public void setPaymentID(String value) {
	this.paymentID = value;
	    }

	
	public String getPaymentID() {
	return this.paymentID;
	    }
	

	
	public Order getOrder() {
	   return this.order;
	}

	public void setOrder(Order order) {
	   this.order = order;
	}
}
