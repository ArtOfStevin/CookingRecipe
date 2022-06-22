package com.example.cookingrecipes.model;

import com.google.gson.annotations.SerializedName;

public class FoodBannerFromApi {

    @SerializedName("title")
    private String title;

    @SerializedName("thumb")
    private String imageUrl;

    @SerializedName("key")
    private String key;

    @SerializedName("times")
    private String timeNeeded;

    @SerializedName("portion")
    private String servePortion;

    @SerializedName("dificulty")
    private String dificulty;

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getKey() {
        return key;
    }

    public String getTimeNeeded() {
        return timeNeeded;
    }

    public String getServePortion() {
        return servePortion;
    }

    public String getDificulty() {
        return dificulty;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setTimeNeeded(String timeNeeded) {
        this.timeNeeded = timeNeeded;
    }

    public void setServePortion(String servePortion) {
        this.servePortion = servePortion;
    }

    public void setDificulty(String dificulty) {
        this.dificulty = dificulty;
    }
}
