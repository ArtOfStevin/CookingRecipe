package com.example.cookingrecipes.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import com.example.cookingrecipes.database.entity.FoodBannerFavorite;

import java.util.List;

@Dao
public interface FoodBannerFavDao {

    @Query("SELECT * FROM FoodBannerFavorite")
    List<FoodBannerFavorite> getAllFavorite();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FoodBannerFavorite foodBannerFavorite);

    @Update
    void update(FoodBannerFavorite foodBannerFavorite);

    @Delete
    void delete(FoodBannerFavorite foodBannerFavorite);

    @Query("DELETE FROM FoodBannerFavorite")
    void nukeTable();

    @Query("SELECT * FROM FoodBannerFavorite WHERE `key` LIKE :key AND username LIKE :username")
    FoodBannerFavorite findByKeyAndUsername(String key, String username);
}
