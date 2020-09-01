package com.savane.data.model.item;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BrandResponse {
    @SerializedName("error")
    private boolean error;
    @SerializedName("brands")
    private List<Brand> brands;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<Brand> getBrands() {
        return brands;
    }

    public void setBrands(List<Brand> brands) {
        this.brands = brands;
    }
}
