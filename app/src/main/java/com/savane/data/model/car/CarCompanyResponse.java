package com.savane.data.model.car;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CarCompanyResponse {
    @SerializedName("error")
    private boolean error;
    @SerializedName("companies")
    private List<CarCompany> companies;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<CarCompany> getCompanies() {
        return companies;
    }

    public void setCompanies(List<CarCompany> companies) {
        this.companies = companies;
    }
}
