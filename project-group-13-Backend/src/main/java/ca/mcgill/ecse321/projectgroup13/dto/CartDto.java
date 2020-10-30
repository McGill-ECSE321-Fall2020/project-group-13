package ca.mcgill.ecse321.projectgroup13.dto;
import ca.mcgill.ecse321.projectgroup13.model.Artwork;
import ca.mcgill.ecse321.projectgroup13.model.User;


import java.util.Set;



public class CartDto {

    private int cartID;
    private double totalCost;
    private Set<ArtworkDto> artwork;
    private UserDto user;


    public CartDto(){

    }

    public CartDto(int cartID, double totalCost, Set<ArtworkDto> artwork, UserDto user){
        this.cartID= cartID;
        this.totalCost = totalCost;
        this.artwork = artwork;
        this.user = user;
    }


//    public void setCartID(String value) {
//        this.cartID = value;
//    }


    public int getCartID() {
        return this.cartID;
    }

//    public void setTotalCost(double value) {
//        this.totalCost = value;
//    }

    public double getTotalCost() {
        return this.totalCost;
    }



     public Set<ArtworkDto> getArtwork() {
        return this.artwork;
    }

    public void setArtwork(Set<ArtworkDto> artworks) {
        this.artwork = artworks;
    }


    public UserDto getUser() {
        return this.user;
    }

//    public void setUser(User user) {
//        this.user = user;
//    }

}