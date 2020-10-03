import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Admin extends UserRole{
private ArtGallery artGallery;

@ManyToOne(optional=false)
public ArtGallery getArtGallery() {
   return this.artGallery;
}

public void setArtGallery(ArtGallery artGallery) {
   this.artGallery = artGallery;
}

private ArtGallery business;

@ManyToOne(optional=false)
public ArtGallery getBusiness() {
   return this.business;
}

public void setBusiness(ArtGallery business) {
   this.business = business;
}

}
