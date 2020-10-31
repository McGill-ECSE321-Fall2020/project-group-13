package ca.mcgill.ecse321.projectgroup13.dto;

import javax.persistence.*;
import java.util.Set;


public class UserDto{
    private CartDto cart;
    private Set<ArtworkDto> artwork;
    private String bio;
    private Set<AddressDto> address;
    private Set<OrderDto> order;
    private String username;
    private String password;
    private String email;
    private String profilePictureURL;

    public UserDto() {

    }

    public UserDto(CartDto cart, Set<ArtworkDto> artwork, String bio, Set<AddressDto> address, Set<OrderDto> order, String username, String email, String profilePictureURL) {
        this.cart = cart;
        this.artwork = artwork;
        this.bio = bio;
        this.address = address;
        this.order = order;
        this.username = username;
        this.email = email;
        this.profilePictureURL = profilePictureURL;
    }


    public CartDto getCart() {
        return this.cart;
    }

//    public void setCart(CartDto cart) {
//        this.cart = cart;
//    }

    public void setBio(String value) {
        this.bio = value;
    }
    public String getBio() {
        return this.bio;
    }

    public Set<ArtworkDto> getArtwork() {
        return this.artwork;
    }

    public void setArtwork(Set<ArtworkDto> artworks) {
        this.artwork = artworks;
    }

    public Set<AddressDto> getAddress() {
        return this.address;
    }

    public void setAddress(Set<AddressDto> addresss) {
        this.address = addresss;
    }

    public Set<OrderDto> getOrder() {
        return this.order;
    }

    public void setOrder(Set<OrderDto> order1s) {
        this.order = order1s;
    }


    public void setUsername(String value) {
        this.username = value;
   }

    public String getUsername() {
        return this.username;
    }

    public void setPassword(String value) {
        this.password = value;
    }

    public String getPassword() {
        return this.password;
    }

    public void setEmail(String value) {
        this.email = value;
    }
    public String getEmail() {
        return this.email;
    }

    public void setProfilePictureURL(String value) {
        this.profilePictureURL = value;
    }
    public String getProfilePictureURL() {
        return this.profilePictureURL;
    }
    
	private String apiToken;

	public void setApiToken(String value) {
		this.apiToken = value;
	}

	public String getApiToken() {
		return this.apiToken;
	}

}
