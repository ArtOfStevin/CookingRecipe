package com.example.cookingrecipes.model;

import java.util.ArrayList;
import java.util.List;

public class FoodBanner {

    private String title;
    private String imageUrl;
    private String summary;
    private boolean isFavorite;

    private String timeNeeded;
    private String servePortion;
    private String difficulty;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public String getTimeNeeded() {
        return timeNeeded;
    }

    public void setTimeNeeded(String timeNeeded) {
        this.timeNeeded = timeNeeded;
    }

    public String getServePortion() {
        return servePortion;
    }

    public void setServePortion(String servePortion) {
        this.servePortion = servePortion;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public static List<FoodBanner> generateFoodBannerList(){
        List<FoodBanner> res = new ArrayList<>();

        FoodBanner foodBanner = new FoodBanner();
        FoodBanner foodBanner2 = new FoodBanner();
        FoodBanner foodBanner3 = new FoodBanner();

        foodBanner.setTitle("Ini Tittle");
        foodBanner.setSummary("Ini Summary");
        foodBanner.setTimeNeeded("50 MNT");
        foodBanner.setServePortion("3 People");
        foodBanner.setDifficulty("Easy");
        res.add(foodBanner);

        foodBanner2.setTitle("Ini Tittle 2");
        foodBanner2.setSummary("Ini Summary2");
        foodBanner2.setTimeNeeded("50 MNT");
        foodBanner2.setServePortion("3 People");
        foodBanner2.setDifficulty("Easy");
        res.add(foodBanner2);

        foodBanner3.setTitle("Ini Tittle 3");
        foodBanner3.setSummary("Ini Summary 3");
        foodBanner3.setTimeNeeded("50 MNT");
        foodBanner3.setServePortion("3 People");
        foodBanner3.setDifficulty("Easy");
        res.add(foodBanner3);

        return res;
    }
}
