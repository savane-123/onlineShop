package com.savane.activities.items;

public class Image {
    private int imageId;
    private String nameOne;
    private String  imgTwo;
    private String imgThree;
    private String imgFour;
    private int itemId;

    public Image(int imageId, String nameOne, String imgTwo, String imgThree, String imgFour, int itemId) {
        this.imageId = imageId;
        this.nameOne = nameOne;
        this.imgTwo = imgTwo;
        this.imgThree = imgThree;
        this.imgFour = imgFour;
        this.itemId = itemId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getNameOne() {
        return nameOne;
    }

    public void setNameOne(String nameOne) {
        this.nameOne = nameOne;
    }

    public String getImgTwo() {
        return imgTwo;
    }

    public void setImgTwo(String imgTwo) {
        this.imgTwo = imgTwo;
    }

    public String getImgThree() {
        return imgThree;
    }

    public void setImgThree(String imgThree) {
        this.imgThree = imgThree;
    }

    public String getImgFour() {
        return imgFour;
    }

    public void setImgFour(String imgFour) {
        this.imgFour = imgFour;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
}
