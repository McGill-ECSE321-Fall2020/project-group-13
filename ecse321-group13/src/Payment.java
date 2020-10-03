import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Payment{
private Order order;

@OneToOne(optional=false)
public Order getOrder() {
   return this.order;
}

public void setOrder(Order order) {
   this.order = order;
}

}
