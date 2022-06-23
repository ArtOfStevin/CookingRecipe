package com.example.cookingrecipes.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity
public class FoodBanner {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "key")
    private String key;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "image_url")
    private String imageUrl;

    @ColumnInfo(name = "summary")
    private String summary;

    @ColumnInfo(name = "is_favorite")
    private boolean isFavorite;

    @ColumnInfo(name = "time_needed")
    private String timeNeeded;

    @ColumnInfo(name = "serve_portion")
    private String servePortion;

    @ColumnInfo(name = "difficulty")
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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
