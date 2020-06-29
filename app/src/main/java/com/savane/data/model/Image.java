package com.savane.data.model;

public class Image {
    private Integer imageId;
    private String profile;
    private Integer userId;

    public Image(Integer imageId, String profile, Integer userId) {
        this.imageId = imageId;
        this.profile = profile;
        this.userId = userId;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
