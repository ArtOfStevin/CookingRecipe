package com.example.cookingrecipes.model;

import com.google.gson.annotations.SerializedName;

public class FoodDetailNeedItem {

    @SerializedName("item_name")
    private String itemName;

    @SerializedName("thumb_item")
    private String thumbItem;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getThumbItem() {
        return thumbItem;
    }

    public void setThumbItem(String thumbItem) {
        this.thumbItem = thumbItem;
    }
}
