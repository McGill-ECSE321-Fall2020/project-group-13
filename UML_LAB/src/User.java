import javax.persistence.OneToOne;
import javax.persistence.ManyToMany;
import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

@Entity
public class User{
private Cart cart;

@OneToOne(mappedBy="user", optional=false)
public Cart getCart() {
   return this.cart;
}

public void setCart(Cart cart) {
   this.cart = cart;
}

private Set<Artwork> artwork;

@ManyToMany(mappedBy="artist")
public Set<Artwork> getArtwork() {
   return this.artwork;
}

public void setArtwork(Set<Artwork> artworks) {
   this.artwork = artworks;
}

private Set<Address> address;

@OneToMany(mappedBy="user", cascade={CascadeType.ALL})
public Set<Address> getAddress() {
   return this.address;
}

public void setAddress(Set<Address> addresss) {
   this.address = addresss;
}

private Set<Order> order;

@OneToMany(mappedBy="user", cascade={CascadeType.ALL})
public Set<Order> getOrder() {
   return this.order;
}

public void setOrder(Set<Order> order1s) {
   this.order = order1s;
}

private String username;
   
   public void setUsername(String value) {
this.username = value;
    }
public String getUsername() {
return this.username;
    }
private String password;

public void setPassword(String value) {
this.password = value;
    }
public String getPassword() {
return this.password;
    }
private String email;

public void setEmail(String value) {
this.email = value;
    }
public String getEmail() {
return this.email;
    }
private String profilePictureURL;

public void setProfilePictureURL(String value) {
this.profilePictureURL = value;
    }
public String getProfilePictureURL() {
return this.profilePictureURL;
    }

}
