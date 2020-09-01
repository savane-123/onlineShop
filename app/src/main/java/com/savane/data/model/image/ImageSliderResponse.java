package com.savane.data.model.image;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ImageSliderResponse {
    @SerializedName("error")
    private boolean error;
    @SerializedName("ImageSliderItem")
    private List<ImageSliderItem> ImageSliderItem;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<com.savane.data.model.image.ImageSliderItem> getImageSliderItem() {
        return ImageSliderItem;
    }

    public void setImageSliderItem(List<com.savane.data.model.image.ImageSliderItem> imageSliderItem) {
        ImageSliderItem = imageSliderItem;
    }
}
