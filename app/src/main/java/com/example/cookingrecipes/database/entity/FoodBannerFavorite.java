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

}
