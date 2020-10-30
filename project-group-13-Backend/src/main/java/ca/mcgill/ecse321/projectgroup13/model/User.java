package ca.mcgill.ecse321.projectgroup13.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import java.util.Set;
import javax.persistence.ManyToMany;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
@Table(name="users")
public class User{
private String username;
   
   public void setUsername(String value) {
this.username = value;
    }
@Id
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

public void setOrder(Set<Order> orders) {
   this.order = orders;
}

private Cart cart;

@OneToOne(mappedBy="user", cascade={CascadeType.ALL})
public Cart getCart() {
   return this.cart;
}

public void setCart(Cart cart) {
   this.cart = cart;
}

private String bio;

public void setBio(String value) {
this.bio = value;
    }
public String getBio() {
return this.bio;
    }
private boolean isAdmin;

public void setIsAdmin(boolean value) {
this.isAdmin = value;
    }
public boolean isIsAdmin() {
return this.isAdmin;
    }
@Override
public int hashCode ()
{
final int prime = 31;
   	int result = 1;
   	result = prime * result + ((artwork == null) ? 0 : artwork.hashCode());
   	result = prime * result + ((bio == null) ? 0 : bio.hashCode());
   	result = prime * result + ((cart == null) ? 0 : cart.hashCode());
   	result = prime * result + ((email == null) ? 0 : email.hashCode());
   	result = prime * result + (isAdmin ? 1231 : 1237);
   	result = prime * result + ((order == null) ? 0 : order.hashCode());
   	result = prime * result + ((password == null) ? 0 : password.hashCode());
   	result = prime * result + ((profilePictureURL == null) ? 0 : profilePictureURL.hashCode());
   	result = prime * result + ((username == null) ? 0 : username.hashCode());
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
   	User other = (User) obj;
   	if (artwork == null) {
   		if (other.artwork != null)
   			return false;
   	} else if (!artwork.equals(other.artwork))
   		return false;
   	if (bio == null) {
   		if (other.bio != null)
   			return false;
   	} else if (!bio.equals(other.bio))
   		return false;
   	if (cart == null) {
   		if (other.cart != null)
   			return false;
   	} else if (!cart.equals(other.cart))
   		return false;
   	if (email == null) {
   		if (other.email != null)
   			return false;
   	} else if (!email.equals(other.email))
   		return false;
   	if (isAdmin != other.isAdmin)
   		return false;
   	if (order == null) {
   		if (other.order != null)
   			return false;
   	} else if (!order.equals(other.order))
   		return false;
   	if (password == null) {
   		if (other.password != null)
   			return false;
   	} else if (!password.equals(other.password))
   		return false;
   	if (profilePictureURL == null) {
   		if (other.profilePictureURL != null)
   			return false;
   	} else if (!profilePictureURL.equals(other.profilePictureURL))
   		return false;
   	if (username == null) {
   		if (other.username != null)
   			return false;
   	} else if (!username.equals(other.username))
   		return false;
   	return true;
   }
@Override
public String toString() {
	return "User [username=" + username + ", password=" + password + ", email=" + email + ", profilePictureURL="
			+ profilePictureURL + ", artwork=" + artwork + ", address=" + address + ", order=" + order + ", cart="
			+ cart + ", bio=" + bio + ", isAdmin=" + isAdmin + "]";
}

	
}
