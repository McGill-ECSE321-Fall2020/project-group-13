package ca.mcgill.ecse321.projectgroup13.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Set;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

@Entity
public class Cart{
private Integer cartID;
   
   public void setCartID(Integer value) {
this.cartID = value;
    }
@Id
public Integer getCartID() {
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

@Override
public int hashCode ()
{
final int prime = 31;
   	int result = 1;
   	result = prime * result + ((artwork == null) ? 0 : artwork.hashCode());
   	result = prime * result + ((cartID == null) ? 0 : cartID.hashCode());
   	long temp;
   	temp = Double.doubleToLongBits(totalCost);
   	result = prime * result + (int) (temp ^ (temp >>> 32));
   	return result;
   }
@Override
public boolean equals (Object obj)
{
if (this == obj)
   		return true;
   	if (obj == null)
   		return false;
   	if (getClass() != obj.getClass())
   		return false;
   	Cart other = (Cart) obj;
   	if (artwork == null) {
   		if (other.artwork != null)
   			return false;
   	} else if (!artwork.equals(other.artwork))
   		return false;
   	if (cartID == null) {
   		if (other.cartID != null)
   			return false;
   	} else if (!cartID.equals(other.cartID))
   		return false;
   	if (Double.doubleToLongBits(totalCost) != Double.doubleToLongBits(other.totalCost))
   		return false;
   	return true;
   }
}
