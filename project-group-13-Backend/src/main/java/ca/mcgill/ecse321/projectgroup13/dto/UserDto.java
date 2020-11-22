package ca.mcgill.ecse321.projectgroup13.dto;

public class UserDto{

    private String username;
    //private String password;
    private String email;
    private String bio;
    private String profilePictureURL;

    public UserDto() {

    }

    public UserDto(String username, String email, String bio, String profilePictureURL) {
        //this.address = address;
        //this.order = order;
        this.username = username;
        this.email = email;
        this.bio = bio;
        this.profilePictureURL = profilePictureURL;
    }


    public void setBio(String value) {
        this.bio = value;
    }
    public String getBio() {
        return this.bio;
    }

    public void setUsername(String value) {
        this.username = value;
   }
    public String getUsername() {
        return this.username;
    }

//    public void setPassword(String value) {
//        this.password = value;
//    }
//    public String getPassword() {
//        return this.password;
//    }

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
