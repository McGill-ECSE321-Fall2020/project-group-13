import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Set;
import javax.persistence.ManyToMany;

@Entity
public class Artwork{
private Set<User> artist;

@ManyToMany
public Set<User> getArtist() {
   return this.artist;
}

public void setArtist(Set<User> artists) {
   this.artist = artists;
}

private String artworkID;

public void setArtworkID(String value) {
this.artworkID = value;
    }
@Id
public String getArtworkID() {
return this.artworkID;
    }
private Order order;

@ManyToOne
public Order getOrder() {
   return this.order;
}

public void setOrder(Order order) {
   this.order = order;
}

private boolean artworkSold;
   
   public void setArtworkSold(boolean value) {
this.artworkSold = value;
    }
public boolean isArtworkSold() {
return this.artworkSold;
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

private Set<Order> cart;

@ManyToMany(mappedBy="artwork")
public Set<Order> getCart() {
   return this.cart;
}

public void setCart(Set<Order> orders) {
   this.cart = orders;
}
   }
