package com.savane.data.model.bike;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BikeModelResponse {

    @SerializedName("error")
    private boolean error;
    @SerializedName("bikesCompanies")
    List<BikeCompany> bikesCompanies;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<BikeCompany> getBikesCompanies() {
        return bikesCompanies;
    }

    public void setBikesCompanies(List<BikeCompany> bikesCompanies) {
        this.bikesCompanies = bikesCompanies;
    }
}
