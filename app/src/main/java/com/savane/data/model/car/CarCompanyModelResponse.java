package com.savane.data.model.car;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CarCompanyModelResponse {
    @SerializedName("error")
    private boolean error;
    @SerializedName("models")
    private List<CarCompany> models;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<CarCompany> getModels() {
        return models;
    }

    public void setModels(List<CarCompany> models) {
        this.models = models;
    }
}
