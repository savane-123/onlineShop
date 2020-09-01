package com.savane.data.model.image;

import com.google.gson.annotations.SerializedName;

public class ResponseUpdateProfileImage {
    @SerializedName("error")
    private boolean error;
    @SerializedName("imagePath")
    private String imagePath;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
