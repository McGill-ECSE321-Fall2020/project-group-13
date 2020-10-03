import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Customer extends UserRole{
private Set<Order> order;

@OneToMany(mappedBy="customer", cascade={CascadeType.ALL})
public Set<Order> getOrder() {
   return this.order;
}

public void setOrder(Set<Order> orders) {
   this.order = orders;
}

private Set<Address> address;

@OneToMany(mappedBy="customer", cascade={CascadeType.ALL})
public Set<Address> getAddress() {
   return this.address;
}

public void setAddress(Set<Address> addresss) {
   this.address = addresss;
}

private Order order1;

@OneToOne(optional=false)
public Order getOrder1() {
   return this.order1;
}

public void setOrder1(Order order1) {
   this.order1 = order1;
}

}
