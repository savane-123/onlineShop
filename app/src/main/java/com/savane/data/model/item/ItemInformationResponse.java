package com.savane.data.model.item;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ItemInformationResponse {
    @SerializedName("error")
    private boolean error;
    @SerializedName("items")
    private List<ItemInformation> items;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<ItemInformation> getItems() {
        return items;
    }

    public void setItems(List<ItemInformation> items) {
        this.items = items;
    }
}
