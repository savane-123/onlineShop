package com.savane.data.model.image;

public class ImageSliderItem {

    private int Id;
    private String imageName;
    private String imageDescription;

    public ImageSliderItem() {
    }

    public ImageSliderItem(int id, String imageName, String imageDescription) {
        Id = id;
        this.imageName = imageName;
        this.imageDescription = imageDescription;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageDescription() {
        return imageDescription;
    }

    public void setImageDescription(String imageDescription) {
        this.imageDescription = imageDescription;
    }
}
