import javax.persistence.Entity;

@Entity
public enum OrderStatus{
	InCart, PaymentPending, Placed, Shipped, Delivered
}
