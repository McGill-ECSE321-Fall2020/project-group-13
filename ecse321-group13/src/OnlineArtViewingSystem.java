import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class OnlineArtViewingSystem{
private Set<User> user;

@OneToMany(mappedBy="onlineArtViewingSystem", cascade={CascadeType.ALL})
public Set<User> getUser() {
   return this.user;
}

public void setUser(Set<User> users) {
   this.user = users;
}

private ArtGallery artGallery;

@OneToOne(mappedBy="onlineArtViewingSystem", cascade={CascadeType.ALL})
public ArtGallery getArtGallery() {
   return this.artGallery;
}

public void setArtGallery(ArtGallery artGallery) {
   this.artGallery = artGallery;
}

}
