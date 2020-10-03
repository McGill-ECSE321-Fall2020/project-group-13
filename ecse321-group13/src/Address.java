import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Address{
private Customer customer;

@ManyToOne(optional=false)
public Customer getCustomer() {
   return this.customer;
}

public void setCustomer(Customer customer) {
   this.customer = customer;
}

}
