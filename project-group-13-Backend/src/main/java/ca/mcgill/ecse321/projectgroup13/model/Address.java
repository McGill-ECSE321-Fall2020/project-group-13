package ca.mcgill.ecse321.projectgroup13.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Address{
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
private Integer addressID;

public void setAddressID(Integer value) {
this.addressID = value;
    }
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
public Integer getAddressID() {
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

@Override
public int hashCode ()
{
final int prime = 31;
   	int result = 1;
   	result = prime * result + ((addressID == null) ? 0 : addressID.hashCode());
   	result = prime * result + ((city == null) ? 0 : city.hashCode());
   	result = prime * result + ((country == null) ? 0 : country.hashCode());
   	result = prime * result + ((postalCode == null) ? 0 : postalCode.hashCode());
   	result = prime * result + ((province == null) ? 0 : province.hashCode());
   	result = prime * result + ((streetAddress1 == null) ? 0 : streetAddress1.hashCode());
   	result = prime * result + ((streetAddress2 == null) ? 0 : streetAddress2.hashCode());
   	result = prime * result + ((user == null) ? 0 : user.hashCode());
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
   	Address other = (Address) obj;
   	if (addressID == null) {
   		if (other.addressID != null)
   			return false;
   	} else if (!addressID.equals(other.addressID))
   		return false;
   	if (city == null) {
   		if (other.city != null)
   			return false;
   	} else if (!city.equals(other.city))
   		return false;
   	if (country == null) {
   		if (other.country != null)
   			return false;
   	} else if (!country.equals(other.country))
   		return false;
   	if (postalCode == null) {
   		if (other.postalCode != null)
   			return false;
   	} else if (!postalCode.equals(other.postalCode))
   		return false;
   	if (province == null) {
   		if (other.province != null)
   			return false;
   	} else if (!province.equals(other.province))
   		return false;
   	if (streetAddress1 == null) {
   		if (other.streetAddress1 != null)
   			return false;
   	} else if (!streetAddress1.equals(other.streetAddress1))
   		return false;
   	if (streetAddress2 == null) {
   		if (other.streetAddress2 != null)
   			return false;
   	} else if (!streetAddress2.equals(other.streetAddress2))
   		return false;
   	if (user == null) {
   		if (other.user != null)
   			return false;
   	} else if (!user.equals(other.user))
   		return false;
   	return true;
   }
}
