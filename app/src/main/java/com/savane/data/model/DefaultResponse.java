package com.savane.data.model;

import com.google.gson.annotations.SerializedName;

public class DefaultResponse {
    @SerializedName("error")
    private boolean error;
    @SerializedName("message")
    private String msg;
    private User user;
    private Address add;
    public DefaultResponse(boolean error, String msg,User user,Address add) {
        this.error = error;
        this.msg = msg;
        this.user=user;
        this.add=add;
    }

    public boolean isError() {
        return error;
    }
    public String getMsg() {
        return msg;
    }
    public User getUser(){return user;}
    public Address getAddress(){return add;}
}
