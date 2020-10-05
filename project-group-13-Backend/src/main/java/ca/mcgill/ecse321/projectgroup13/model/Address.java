import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Address{
private String addressID;
   
   public void setAddressID(String value) {
this.addressID = value;
    }
@Id
public String getAddressID() {
return this.addressID;
    }
private User user;

@ManyToOne(optional=false)
public User getUser() {
   return this.user;
}

public void setUser(User user) {
   this.user = user;
}

private String streetAddress1;
   
   public void setStreetAddress1(String value) {
this.streetAddress1 = value;
    }
public String getStreetAddress1() {
return this.streetAddress1;
    }
private String city;

public void setCity(String value) {
this.city = value;
    }
public String getCity() {
return this.city;
    }
private String province;

public void setProvince(String value) {
this.province = value;
    }
public String getProvince() {
return this.province;
    }
private String country;

public void setCountry(String value) {
this.country = value;
    }
public String getCountry() {
return this.country;
    }
private String postalCode;

public void setPostalCode(String value) {
this.postalCode = value;
    }
public String getPostalCode() {
return this.postalCode;
    }
private String streetAddress2;

public void setStreetAddress2(String value) {
this.streetAddress2 = value;
    }
public String getStreetAddress2() {
return this.streetAddress2;
    }

}
