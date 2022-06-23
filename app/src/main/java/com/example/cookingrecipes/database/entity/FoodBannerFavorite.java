package com.example.cookingrecipes.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FoodBannerFavorite {

    @PrimaryKey
    @ColumnInfo(name = "key")
    @NonNull
    private String key;

    @ColumnInfo(name = "username")
    private String username;

//    @ColumnInfo(name = "title")
//    private String title;
//
//    @ColumnInfo(name = "image_url")
//    private String imageUrl;
//
//    @ColumnInfo(name = "time_needed")
//    private String timeNeeded;
//
//    @ColumnInfo(name = "serve_portion")
//    private String servePortion;
//
//    @ColumnInfo(name = "difficulty")
//    private String difficulty;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getImageUrl() {
//        return imageUrl;
//    }
//
//    public void setImageUrl(String imageUrl) {
//        this.imageUrl = imageUrl;
//    }
//
//    public String getTimeNeeded() {
//        return timeNeeded;
//    }
//
//    public void setTimeNeeded(String timeNeeded) {
//        this.timeNeeded = timeNeeded;
//    }
//
//    public String getServePortion() {
//        return servePortion;
//    }
//
//    public void setServePortion(String servePortion) {
//        this.servePortion = servePortion;
//    }
//
//    public String getDifficulty() {
//        return difficulty;
//    }
//
//    public void setDifficulty(String difficulty) {
//        this.difficulty = difficulty;
//    }
}
