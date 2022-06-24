package com.example.cookingrecipes.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FoodDetailFromApi {

    @SerializedName("title")
    private String title;

    @SerializedName("thumb")
    private String imageUrl;

    @SerializedName("times")
    private String timeNeeded;

    @SerializedName("servings")
    private String servePortion;

    @SerializedName("dificulty")
    private String dificulty;

    @SerializedName("desc")
    private String description;

    @SerializedName("author")
    private FoodDetailAuthor author;

    @SerializedName("needItem")
    private List<FoodDetailNeedItem> needItem;

    @SerializedName("ingredient")
    private List<String> ingredient;

    @SerializedName("step")
    private List<String> step;

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
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

    public void setTimeNeeded(String timeNeeded) {
        this.timeNeeded = timeNeeded;
    }

    public void setServePortion(String servePortion) {
        this.servePortion = servePortion;
    }

    public void setDificulty(String dificulty) {
        this.dificulty = dificulty;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public FoodDetailAuthor getAuthor() {
        return author;
    }

    public void setAuthor(FoodDetailAuthor author) {
        this.author = author;
    }

    public List<FoodDetailNeedItem> getNeedItem() {
        return needItem;
    }

    public void setNeedItem(List<FoodDetailNeedItem> needItem) {
        this.needItem = needItem;
    }

    public List<String> getIngredient() {
        return ingredient;
    }

    public void setIngredient(List<String> ingredient) {
        this.ingredient = ingredient;
    }

    public List<String> getStep() {
        return step;
    }

    public void setStep(List<String> step) {
        this.step = step;
    }
}
