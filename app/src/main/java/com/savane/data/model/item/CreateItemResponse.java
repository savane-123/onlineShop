package com.savane.data.model.item;

import com.google.gson.annotations.SerializedName;

public class CreateItemResponse {
    @SerializedName("error")
    private boolean error;
    @SerializedName("message")
    private String message;
    private int itemId;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
}
