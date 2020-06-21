package com.savane.data.model;

public class UserResponse {
    private boolean error;
    private String  image;

    public UserResponse(boolean error, String image) {
        this.error = error;
        this.image = image;
    }

    public boolean isError() {
        return error;
    }
    public void setError(boolean error){
        this.error=error;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
