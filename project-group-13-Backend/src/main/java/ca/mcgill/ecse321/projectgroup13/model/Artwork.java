import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Set;
import javax.persistence.ManyToMany;

@Entity
public class Artwork{
private boolean artworkSold;
   
   public void setArtworkSold(boolean value) {
this.artworkSold = value;
    }
public boolean isArtworkSold() {
return this.artworkSold;
    }
private Artist artist;

@ManyToOne(optional=false)
public Artist getArtist() {
   return this.artist;
}

public void setArtist(Artist artist) {
   this.artist = artist;
}

private String description;
   
   public void setDescription(String value) {
this.description = value;
    }
public String getDescription() {
return this.description;
    }
private String title;

public void setTitle(String value) {
this.title = value;
    }
public String getTitle() {
return this.title;
    }
private String creationDate;

public void setCreationDate(String value) {
this.creationDate = value;
    }
public String getCreationDate() {
return this.creationDate;
    }
private String dimensions;

public void setDimensions(String value) {
this.dimensions = value;
    }
public String getDimensions() {
return this.dimensions;
    }
private String medium;

public void setMedium(String value) {
this.medium = value;
    }
public String getMedium() {
return this.medium;
    }
private String collection;

public void setCollection(String value) {
this.collection = value;
    }
public String getCollection() {
return this.collection;
    }
private String imageUrl;

public void setImageUrl(String value) {
this.imageUrl = value;
    }
public String getImageUrl() {
return this.imageUrl;
    }

private Set<Order> order;

@ManyToMany(mappedBy="artwork")
public Set<Order> getOrder() {
   return this.order;
}

public void setOrder(Set<Order> orders) {
   this.order = orders;
}
   }
