package ca.mcgill.ecse321.projectgroup13.dto;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;


public class AddressDto {
    private String addressID;
    private String streetAddress1;
    private String streetAddress2;
    private String city;
    private String province;
    private String country;
    private String postalCode;

    public String getAddressID() {
        return this.addressID;
    }

    public void setStreetAddress1(String value) {
        this.streetAddress1 = value;
    }

    public String getStreetAddress1() {
        return this.streetAddress1;
    }

    public void setCity(String value) {
        this.city = value;
    }

    public String getCity() {
        return this.city;
    }

    public void setProvince(String value) {
        this.province = value;
    }

    public String getProvince() {
        return this.province;
    }

    public void setCountry(String value) {
        this.country = value;
    }

    public String getCountry() {
        return this.country;
    }

    public void setPostalCode(String value) {
        this.postalCode = value;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public void setStreetAddress2(String value) {
        this.streetAddress2 = value;
    }

    public String getStreetAddress2() {
        return this.streetAddress2;
    }
}