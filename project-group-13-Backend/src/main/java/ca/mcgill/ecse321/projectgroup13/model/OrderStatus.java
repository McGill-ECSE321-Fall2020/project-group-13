package ca.mcgill.ecse321.projectgroup13.model;

import javax.persistence.Entity;

@Entity
public enum OrderStatus {
	PaymentPending, Placed, Shipped, Delivered
}
