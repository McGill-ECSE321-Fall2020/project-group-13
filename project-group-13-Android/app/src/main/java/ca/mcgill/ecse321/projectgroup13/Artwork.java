package ca.mcgill.ecse321.projectgroup13;

public class Artwork {
    private String title;
    private int price;



    private String imgURL;
    public Artwork(String title,int price, String imgURL){
        this.title=title;
        this.price=price;
        this.imgURL=imgURL;
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
}
