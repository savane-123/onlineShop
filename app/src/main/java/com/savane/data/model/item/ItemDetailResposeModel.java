package com.savane.data.model.item;

import com.google.gson.annotations.SerializedName;

public class ItemDetailResposeModel {
    @SerializedName("error")
    private boolean error;
    @SerializedName("itemsDetails")
    ItemDetailsModel itemDetails;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public ItemDetailsModel getItemDetails() {
        return itemDetails;
    }

    public void setItemDetails(ItemDetailsModel itemDetails) {
        this.itemDetails = itemDetails;
    }
}
