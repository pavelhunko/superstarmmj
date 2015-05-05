package com.businessappstation.superstarmmjdispensaries.model;

/**
 * Created by pavelhunko@gmail.com on 04/May/2015.
 */
public class Products {


    private String name;
    private String shortDescription;
    private String productUrl;
    private byte[] image;

    public Products() {
    }


    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }


    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }


}
