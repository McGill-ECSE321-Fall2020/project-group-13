import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Set;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

@Entity
public class Cart{
private String cartID;
   
   public void setCartID(String value) {
this.cartID = value;
    }
@Id
public String getCartID() {
return this.cartID;
    }
private double totalCost;

public void setTotalCost(double value) {
this.totalCost = value;
    }
public double getTotalCost() {
return this.totalCost;
    }
private Set<Artwork> artwork;

@ManyToMany
public Set<Artwork> getArtwork() {
   return this.artwork;
}

public void setArtwork(Set<Artwork> artworks) {
   this.artwork = artworks;
}

private User user;

@OneToOne(optional=false)
public User getUser() {
   return this.user;
}

public void setUser(User user) {
   this.user = user;
}

}
