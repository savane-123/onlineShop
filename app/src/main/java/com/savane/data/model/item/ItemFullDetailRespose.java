package com.savane.data.model.item;

import com.google.gson.annotations.SerializedName;

public class ItemFullDetailRespose {
    @SerializedName("error")
    private boolean error;
    @SerializedName("items")
    private ItemFullInformation items;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public ItemFullInformation getItems() {
        return items;
    }

    public void setItems(ItemFullInformation items) {
        this.items = items;
    }
}
