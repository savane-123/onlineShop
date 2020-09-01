package com.savane.activities.items;

import com.google.gson.annotations.SerializedName;

public class ItemImageresonse {
@SerializedName("error")
    private boolean error;
    @SerializedName("itemImages")
    private Image image;
    public boolean isError(){
        return error;
    }
    public void setError(){
        this.error=error;
    }
    public Image getImage(){
        return image;
    }
    public void setImage(){
        this.image=image;
    }
}
