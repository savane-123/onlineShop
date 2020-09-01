package com.savane.data.model.item;

public class ItemModel {
    private String itemBrand;
    private String ItemModel;
    private int ItemQuantity;
    private String itemPrice;
    private String itemYear;
    private String additionalInfo;

    public ItemModel() {
    }

    public ItemModel(String itemBrand, String itemModel, int itemQuantity, String itemPrice, String itemYear, String additionalInfo) {
        this.itemBrand = itemBrand;
        ItemModel = itemModel;
        ItemQuantity = itemQuantity;
        this.itemPrice = itemPrice;
        this.itemYear = itemYear;
        this.additionalInfo = additionalInfo;
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

    @Override
    public String toString() {
        return "ItemModel{" +
                "itemBrand='" + itemBrand + '\'' +
                ", ItemModel='" + ItemModel + '\'' +
                ", ItemQuantity=" + ItemQuantity +
                ", itemPrice='" + itemPrice + '\'' +
                ", itemYear='" + itemYear + '\'' +
                ", additionalInfo='" + additionalInfo + '\'' +
                '}';
    }
}
