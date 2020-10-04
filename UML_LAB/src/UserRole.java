import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class UserRole{
private User user1;

@ManyToOne(optional=false)
public User getUser1() {
   return this.user1;
}

public void setUser1(User user1) {
   this.user1 = user1;
}

private String/*No type specified!*/ user;

public void setUser(String/*No type specified!*/ value) {
this.user = value;
    }
public String/*No type specified!*/ getUser() {
return this.user;
       }
   
}
