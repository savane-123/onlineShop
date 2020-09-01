package com.savane.data.model.item;

public class ItemFullInformation {

    private int itemId;
    private String itemBrand;
    private String ItemModel;
    private int ItemQuantity;
    private  String itemPrice;
    private String itemYear;
    private String additionalInfo;
    private  int userId;
    private int subCategoryId;
    private String FuelType;
    private String KilometerCovered;


    public int getId() {
        return itemId;
    }

    public void setId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemBrand() {
        return itemBrand;
    }

    public void setItemBrand(String itemBrand) {
        this.itemBrand = itemBrand;
    }

    public String getItemModel() {
        return ItemModel;
    }

    public void setItemModel(String itemModel) {
        ItemModel = itemModel;
    }

    public int getItemQuantity() {
        return ItemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        ItemQuantity = itemQuantity;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemYear() {
        return itemYear;
    }

    public void setItemYear(String itemYear) {
        this.itemYear = itemYear;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(int subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getFuelType() {
        return FuelType;
    }

    public void setFuelType(String fuelType) {
        FuelType = fuelType;
    }

    public String getKilometerCovered() {
        return KilometerCovered;
    }

    public void setKilometerCovered(String kilometerCovered) {
        KilometerCovered = kilometerCovered;
    }
}
