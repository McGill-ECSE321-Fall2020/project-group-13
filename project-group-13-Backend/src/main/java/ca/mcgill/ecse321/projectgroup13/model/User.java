package ca.mcgill.ecse321.projectgroup13.model;

import javax.persistence.*;
import java.util.Set;

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

}
