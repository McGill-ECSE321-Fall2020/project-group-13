package ca.mcgill.ecse321.projectgroup13.dto;

import ca.mcgill.ecse321.projectgroup13.model.User;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;


public class AddressDto {
    private Integer addressID;
    private String streetAddress1;
    private String streetAddress2;
    private String city;
    private String province;
    private String country;
    private String postalCode;
    private UserDto user;

    public AddressDto() {
    }

    public AddressDto(Integer addressID, String streetAddress1, String streetAddress2, String city, String province, String country, String postalCode, UserDto user) {
        this.addressID = addressID;
        this.streetAddress1 = streetAddress1;
        this.streetAddress2 = streetAddress2;
        this.city = city;
        this.province = province;
        this.country = country;
        this.postalCode = postalCode;
        this.user = user;
    }

    public int getAddressID() {
        return addressID;
    }

    public UserDto getUser() {
        return user;
    }

    public void setStreetAddress1(String value) {
        this.streetAddress1 = value;
    }

    public String getStreetAddress1() {
        return streetAddress1;
    }

    public void setCity(String value) {
        this.city = value;
    }

    public String getCity() {
        return city;
    }

    public void setProvince(String value) {
        this.province = value;
    }

    public String getProvince() {
        return province;
    }

    public void setCountry(String value) {
        this.country = value;
    }

    public String getCountry() {
        return country;
    }

    public void setPostalCode(String value) {
        this.postalCode = value;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setStreetAddress2(String value) {
        this.streetAddress2 = value;
    }

    public String getStreetAddress2() {
        return streetAddress2;
    }
}