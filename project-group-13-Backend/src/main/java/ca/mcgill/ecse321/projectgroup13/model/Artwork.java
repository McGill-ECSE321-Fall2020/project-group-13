package ca.mcgill.ecse321.projectgroup13.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Artwork{
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
private boolean artworkSold;

public void setArtworkSold(boolean value) {
this.artworkSold = value;
    }
public boolean isArtworkSold() {
return this.artworkSold;
    }
private Set<User> artist;

@ManyToMany
public Set<User> getArtist() {
   return this.artist;
}

public void setArtist(Set<User> artists) {
   this.artist = artists;
}

private Integer artworkID;

public void setArtworkID(Integer value) {
this.artworkID = value;
    }
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
public Integer getArtworkID() {
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

private boolean isOnPremise;

public void setIsOnPremise(boolean value) {
this.isOnPremise = value;
    }
public boolean isIsOnPremise() {
return this.isOnPremise;
    }
private Double worth;

public void setWorth(Double value) {
this.worth = value;
    }
public Double getWorth() {
return this.worth;
    }
@Override
public int hashCode ()
{
final int prime = 31;
   	int result = 1;
   	result = prime * result + ((artworkID == null) ? 0 : artworkID.hashCode());
   	result = prime * result + (artworkSold ? 1231 : 1237);
   	result = prime * result + ((collection == null) ? 0 : collection.hashCode());
   	result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
   	result = prime * result + ((description == null) ? 0 : description.hashCode());
   	result = prime * result + ((dimensions == null) ? 0 : dimensions.hashCode());
   	result = prime * result + ((imageUrl == null) ? 0 : imageUrl.hashCode());
   	result = prime * result + (isOnPremise ? 1231 : 1237);
   	result = prime * result + ((medium == null) ? 0 : medium.hashCode());
   	result = prime * result + ((title == null) ? 0 : title.hashCode());
   	long temp;
   	temp = Double.doubleToLongBits(worth);
   	result = prime * result + (int) (temp ^ (temp >>> 32));
   	return result;
   }
@Override
public boolean equals (Object obj)
{
if (this == obj)
   		return true;
   	if (obj == null)
   		return false;
   	if (getClass() != obj.getClass())
   		return false;
   	Artwork other = (Artwork) obj;
   	if (artworkID == null) {
   		if (other.artworkID != null)
   			return false;
   	} else if (!artworkID.equals(other.artworkID))
   		return false;
   	if (artworkSold != other.artworkSold)
   		return false;
   	if (collection == null) {
   		if (other.collection != null)
   			return false;
   	} else if (!collection.equals(other.collection))
   		return false;
   	if (creationDate == null) {
   		if (other.creationDate != null)
   			return false;
   	} else if (!creationDate.equals(other.creationDate))
   		return false;
   	if (description == null) {
   		if (other.description != null)
   			return false;
   	} else if (!description.equals(other.description))
   		return false;
   	if (dimensions == null) {
   		if (other.dimensions != null)
   			return false;
   	} else if (!dimensions.equals(other.dimensions))
   		return false;
   	if (imageUrl == null) {
   		if (other.imageUrl != null)
   			return false;
   	} else if (!imageUrl.equals(other.imageUrl))
   		return false;
   	if (isOnPremise != other.isOnPremise)
   		return false;
   	if (medium == null) {
   		if (other.medium != null)
   			return false;
   	} else if (!medium.equals(other.medium))
   		return false;
   	if (title == null) {
   		if (other.title != null)
   			return false;
   	} else if (!title.equals(other.title))
   		return false;
   	if (Double.doubleToLongBits(worth) != Double.doubleToLongBits(other.worth))
   		return false;
   	return true;
   }
}
