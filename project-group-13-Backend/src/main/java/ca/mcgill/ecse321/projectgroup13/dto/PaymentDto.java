package ca.mcgill.ecse321.projectgroup13.dto;

import java.sql.Date;
import java.sql.Time;

public class PaymentDto {
	private int paymentID;
	private double totalAmount;
	private Date paymentDate;
	private Time paymentTime;
	private double cardNumber;
	private Date expirationDate;
	private String nameOnCard;
	private int cvv;
	private OrderDto order;
	
	public PaymentDto() {
		
	}
	public PaymentDto(int paymentID, double totalAmount, double cardNumber, Date expirationDate, String nameOnCard, int cvv, Date paymentDate, Time paymentTime, OrderDto order) {
		this.paymentID = paymentID;
		this.totalAmount = totalAmount;
		this.cardNumber = cardNumber;
		this.expirationDate = expirationDate;
		this.nameOnCard = nameOnCard;
		this.cvv = cvv;
		this.paymentDate = paymentDate;
		this.paymentTime = paymentTime;
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
	

//	public void setPaymentID(String value) {
//	this.paymentID = value;
//	    }

	
	public int getPaymentID() {
	return this.paymentID;
	    }
	

	
	public OrderDto getOrder() {
	   return this.order;
	}

	public void setOrder(OrderDto order) {
	   this.order = order;
	}
}
