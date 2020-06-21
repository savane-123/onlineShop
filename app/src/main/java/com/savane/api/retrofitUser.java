package com.savane.api;

public class retrofitUser {
    private String id;
    private String  image;
    private String  userId;

    public retrofitUser(String id, String image, String userId) {
        this.id = id;
        this.image = image;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public String getImage(){
        return image;
    }

    public String getUserId() {
        return userId;
    }
}
