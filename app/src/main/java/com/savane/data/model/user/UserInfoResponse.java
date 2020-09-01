package com.savane.data.model.user;

import com.google.gson.annotations.SerializedName;

public class UserInfoResponse {
    @SerializedName("error")
    private boolean error;
    @SerializedName("userInfo")
    private UserItemInfo userInfo;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public UserItemInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserItemInfo userInfo) {
        this.userInfo = userInfo;
    }
}
