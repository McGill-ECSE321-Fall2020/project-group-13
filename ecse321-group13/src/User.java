import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

@Entity
public class User{
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
private OnlineArtViewingSystem onlineArtViewingSystem;

@ManyToOne(optional=false)
public OnlineArtViewingSystem getOnlineArtViewingSystem() {
   return this.onlineArtViewingSystem;
}

public void setOnlineArtViewingSystem(OnlineArtViewingSystem onlineArtViewingSystem) {
   this.onlineArtViewingSystem = onlineArtViewingSystem;
}

private Set<UserRole> userRole;

@OneToMany(mappedBy="user", cascade={CascadeType.ALL})
public Set<UserRole> getUserRole() {
   return this.userRole;
}

public void setUserRole(Set<UserRole> userRoles) {
   this.userRole = userRoles;
}

}
