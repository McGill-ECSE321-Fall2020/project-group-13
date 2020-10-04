import javax.persistence.Entity;
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
private String profilePictureURL;

public void setProfilePictureURL(String value) {
this.profilePictureURL = value;
    }
public String getProfilePictureURL() {
return this.profilePictureURL;
    }
private Set<UserRole> userRole;

@OneToMany(mappedBy="user1", cascade={CascadeType.ALL})
public Set<UserRole> getUserRole() {
   return this.userRole;
}

public void setUserRole(Set<UserRole> userRoles) {
   this.userRole = userRoles;
}

}
