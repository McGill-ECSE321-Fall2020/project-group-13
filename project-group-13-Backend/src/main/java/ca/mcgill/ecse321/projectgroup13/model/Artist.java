import javax.persistence.OneToMany;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Artist extends UserRole{
private String bio;
   
   public void setBio(String value) {
this.bio = value;
    }
public String getBio() {
return this.bio;
    }
private Set<Artwork> artwork;

@OneToMany(mappedBy="artist")
public Set<Artwork> getArtwork() {
   return this.artwork;
}

public void setArtwork(Set<Artwork> artworks) {
   this.artwork = artworks;
}


}
