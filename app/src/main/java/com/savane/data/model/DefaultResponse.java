package com.savane.data.model;

import com.google.gson.annotations.SerializedName;

public class DefaultResponse {
    @SerializedName("error")
    private boolean error;
    @SerializedName("message")
    private String msg;
    private User user;

    public DefaultResponse(boolean error, String msg,User user) {
        this.error = error;
        this.msg = msg;
        this.user=user;
    }

    public boolean isError() {
        return error;
    }

    public String getMsg() {
        return msg;
    }
    public User getUser(){return user;}
}
