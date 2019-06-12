package com.example.sanjayprajapati.experteye;

/**
 * Created by sanjayprajapati on 6/7/18.
 */

public class Services {
    String title;
    int price;
    String imgURL;

    public Services(String title, int price, String imgURL) {
        this.title = title;
        this.price = price;
        this.imgURL = imgURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }
}
