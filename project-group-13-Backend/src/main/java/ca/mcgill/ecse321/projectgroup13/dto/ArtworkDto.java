package ca.mcgill.ecse321.projectgroup13.dto;

import ca.mcgill.ecse321.projectgroup13.model.Order;
import ca.mcgill.ecse321.projectgroup13.model.User;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Set;
import javax.persistence.ManyToMany;

public class ArtworkDto {
    private boolean isOnPremise;
    private Set<User> artist;
    private String artworkID;
    private Order order;
    private double worth;
    private boolean artworkSold;
    private String description;
    private String title;
    private String creationDate;
    private String dimensions;
    private String medium;
    private String collection;
    private String imageUrl;

    public ArtworkDto() {
    }


    public ArtworkDto(boolean isOnPremise, Set<User> artist, String artworkID, Order order, double worth, boolean artworkSold, String description, String title, String creationDate, String dimensions, String medium, String collection, String imageUrl) {
        this.isOnPremise = isOnPremise;
        this.artist = artist;
        this.artworkID = artworkID;
        this.order = order;
        this.worth = worth;
        this.artworkSold = artworkSold;
        this.description = description;
        this.title = title;
        this.creationDate = creationDate;
        this.dimensions = dimensions;
        this.medium = medium;
        this.collection = collection;
        this.imageUrl = imageUrl;
    }



    public void setIsOnPremise(boolean value) {
        this.isOnPremise = value;
    }

    public boolean isIsOnPremise() {
        return this.isOnPremise;
    }


    public Set<User> getArtist() {
        return this.artist;
    }

//    public void setArtist(Set<User> artists) {
//        this.artist = artists;
//    }


//    public void setArtworkID(String value) {
//        this.artworkID = value;
//    }

    public String getArtworkID() {
        return this.artworkID;
    }


    public Order getOrder() {
        return this.order;
    }

//    public void setOrder(Order order) {
//        this.order = order;
//    }


    public void setWorth(double value) {
        this.worth = value;
    }

    public double getWorth() {
        return this.worth;
    }


//    public void setArtworkSold(boolean value) {
//        this.artworkSold = value;
//    }

    public boolean isArtworkSold() {
        return this.artworkSold;
    }


    public void setDescription(String value) {
        this.description = value;
    }

    public String getDescription() {
        return this.description;
    }


    public void setTitle(String value) {
        this.title = value;
    }

    public String getTitle() {
        return this.title;
    }


    public void setCreationDate(String value) {
        this.creationDate = value;
    }

    public String getCreationDate() {
        return this.creationDate;
    }


    public void setDimensions(String value) {
        this.dimensions = value;
    }

    public String getDimensions() {
        return this.dimensions;
    }


    public void setMedium(String value) {
        this.medium = value;
    }

    public String getMedium() {
        return this.medium;
    }

    public void setCollection(String value) {
        this.collection = value;
    }

    public String getCollection() {
        return this.collection;
    }


    public void setImageUrl(String value) {
        this.imageUrl = value;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }
}