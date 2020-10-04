import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Customer extends UserRole{
private Set<Order> order1;

@OneToMany
public Set<Order> getOrder1() {
   return this.order1;
}

public void setOrder1(Set<Order> order1s) {
   this.order1 = order1s;
}

private Set<Order> order2;

@OneToMany(mappedBy="customer1", cascade={CascadeType.ALL})
public Set<Order> getOrder2() {
   return this.order2;
}

public void setOrder2(Set<Order> orders) {
   this.order2 = orders;
}

private Set<Address> address;

@OneToMany(mappedBy="customer", cascade={CascadeType.ALL})
public Set<Address> getAddress() {
   return this.address;
}

public void setAddress(Set<Address> addresss) {
   this.address = addresss;
}

private Order shoppingCart;

@OneToOne(optional=false)
public Order getShoppingCart() {
   return this.shoppingCart;
}

public void setShoppingCart(Order order1) {
   this.shoppingCart = order1;
}

}
