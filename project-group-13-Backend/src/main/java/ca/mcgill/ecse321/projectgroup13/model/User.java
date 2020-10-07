package ca.mcgill.ecse321.projectgroup13.model;

import javax.persistence.ManyToMany;
import javax.persistence.Entity;
import javax.persistence.Id;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User{
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

private Set<Order> order1;

@OneToMany(mappedBy="user", cascade={CascadeType.ALL})
public Set<Order> getOrder1() {
   return this.order1;
}

public void setOrder1(Set<Order> order1s) {
   this.order1 = order1s;
}

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

@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((address == null) ? 0 : address.hashCode());
	result = prime * result + ((artwork == null) ? 0 : artwork.hashCode());
	result = prime * result + ((email == null) ? 0 : email.hashCode());
	result = prime * result + ((order1 == null) ? 0 : order1.hashCode());
	result = prime * result + ((password == null) ? 0 : password.hashCode());
	result = prime * result + ((profilePictureURL == null) ? 0 : profilePictureURL.hashCode());
	result = prime * result + ((username == null) ? 0 : username.hashCode());
	return result;
}

@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	User other = (User) obj;
	if (address == null) {
		if (other.address != null)
			return false;
	} else if (!address.equals(other.address))
		return false;
	if (artwork == null) {
		if (other.artwork != null)
			return false;
	} else if (!artwork.equals(other.artwork))
		return false;
	if (email == null) {
		if (other.email != null)
			return false;
	} else if (!email.equals(other.email))
		return false;
	if (order1 == null) {
		if (other.order1 != null)
			return false;
	} else if (!order1.equals(other.order1))
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


}
