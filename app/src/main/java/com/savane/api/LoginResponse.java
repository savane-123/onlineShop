package com.savane.api;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("error")
    private boolean error;
    @SerializedName("message")
    private String msg;

    public LoginResponse(boolean error, String msg) {
        this.error = error;
        this.msg = msg;
    }

    public boolean isError() { return error; }

    public String getMsg() { return msg; }

}
