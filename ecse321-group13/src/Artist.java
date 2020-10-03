import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Artist extends UserRole{
private Profile profile;

@OneToOne(optional=false)
public Profile getProfile() {
   return this.profile;
}

public void setProfile(Profile profile) {
   this.profile = profile;
}

}
