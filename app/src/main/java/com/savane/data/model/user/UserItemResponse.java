package com.savane.data.model.user;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserItemResponse {
    @SerializedName("error")
    private boolean error;
      @SerializedName("userAd")
    private List<UserAdverisementItems> userAd;

      public boolean isError(){
          return error;
      }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<UserAdverisementItems> getUserAd() {
        return userAd;
    }

    public void setUserAd(List<UserAdverisementItems> userAd) {
        this.userAd = userAd;
    }
}
