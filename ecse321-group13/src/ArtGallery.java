import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

@Entity
public class ArtGallery{
private OnlineArtViewingSystem onlineArtViewingSystem;

@OneToOne(optional=false)
public OnlineArtViewingSystem getOnlineArtViewingSystem() {
   return this.onlineArtViewingSystem;
}

public void setOnlineArtViewingSystem(OnlineArtViewingSystem onlineArtViewingSystem) {
   this.onlineArtViewingSystem = onlineArtViewingSystem;
}

private Set<Artwork> artwork;

@OneToMany(mappedBy="artGallery", cascade={CascadeType.ALL})
public Set<Artwork> getArtwork() {
   return this.artwork;
}

public void setArtwork(Set<Artwork> artworks) {
   this.artwork = artworks;
}

private Set<Admin> admin;

@OneToMany(mappedBy="artGallery")
public Set<Admin> getAdmin() {
   return this.admin;
}

public void setAdmin(Set<Admin> admins) {
   this.admin = admins;
}

private Set<Admin> owner;

@OneToMany(mappedBy="business")
public Set<Admin> getOwner() {
   return this.owner;
}

public void setOwner(Set<Admin> owners) {
   this.owner = owners;
}

}
