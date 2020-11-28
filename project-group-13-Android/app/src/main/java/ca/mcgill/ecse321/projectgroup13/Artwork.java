package ca.mcgill.ecse321.projectgroup13;

/**
 * This class encapsulates information about an artwork. It is used by listView of artworks.
 */
public class Artwork {
    private String title;
    private int price;
    private int artworkID;
    private String imgURL;
    public Artwork(String title,int price, String imgURL, int artworkID){
        this.title=title;
        this.price=price;
        this.imgURL=imgURL;
        this.artworkID = artworkID;
    }
    public String getTitle() {
        return title;
    }

    public int getPrice() {
        return price;
    }
    public String getImgURL() {
        return imgURL;
    }
    public int getArtworkID() {
        return artworkID;
    }
}
