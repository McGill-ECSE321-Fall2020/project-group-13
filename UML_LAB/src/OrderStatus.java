import javax.persistence.Entity;

@Entity
public enum OrderStatus{
	PaymentPending, Placed, Shipped, Delivered
}
