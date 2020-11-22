package ca.mcgill.ecse321.projectgroup13.dto;

import java.util.Set;


public class ArtworkDto {
    private int artworkID;
    private String title;
    private boolean isOnPremise;
    private double worth;
    private boolean artworkSold;
    private String description;
    private String creationDate;
    private String dimensions;
    private String medium;
    private String collection;
    private String imageUrl;
    private Set<UserDto> artists;

    public ArtworkDto() {
    }


    public ArtworkDto( int artworkID, boolean isOnPremise, double worth, boolean artworkSold, String description, String title, String creationDate, String dimensions, String medium, String collection, String imageUrl, Set<UserDto> artists) {
        this.isOnPremise = isOnPremise;
        this.artists = artists;
        this.artworkID = artworkID;
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
        return isOnPremise;
    }

    public Set<UserDto> getArtist() {
        return artists;
    }


    public void setArtist(Set<UserDto> artists) {
        this.artists = artists;
    }

//    public void setArtworkID(String value) {
//        this.artworkID = value;
//    }

    public int getArtworkID() {
        return artworkID;
    }


//    public OrderDto getOrder() {
//        return order;
//    }

//    public void setOrder(Order order) {
//        this.order = order;
//    }


    public void setWorth(double value) {
        this.worth = value;
    }

    public double getWorth() {
        return worth;
    }

//    public void setArtworkSold(boolean value) {
//        this.artworkSold = value;
//    }

    public boolean isArtworkSold() {
        return artworkSold;
    }


    public void setDescription(String value) {
        this.description = value;
    }

    public String getDescription() {
        return description;
    }


    public void setTitle(String value) {
        this.title = value;
    }

    public String getTitle() {
        return title;
    }


    public void setCreationDate(String value) {
        this.creationDate = value;
    }

    public String getCreationDate() {
        return creationDate;
    }


    public void setDimensions(String value) {
        this.dimensions = value;
    }

    public String getDimensions() {
        return dimensions;
    }


    public void setMedium(String value) {
        this.medium = value;
    }

    public String getMedium() {
        return medium;
    }

    public void setCollection(String value) {
        this.collection = value;
    }

    public String getCollection() {
        return collection;
    }


    public void setImageUrl(String value) {
        this.imageUrl = value;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}