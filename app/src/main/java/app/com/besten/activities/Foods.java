package app.com.besten.activities;

public class Foods {


    private String name;

    private String desc;

    private int price;

    private String photo;


    public Foods() {
    }

    public Foods(String name, String desc, int price, String photo) {
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }



}
