package com.example.cookingrecipes.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cookingrecipes.database.entity.FoodBanner;

import java.util.List;

@Dao
public interface FoodBannerDao {

    @Query("SELECT * FROM FoodBanner")
    LiveData<List<FoodBanner>> getAll();

    @Query("SELECT * FROM FoodBanner WHERE title LIKE '%' || :query|| '%'")
    LiveData<List<FoodBanner>> search(String query);

    // : itu artinya parameter dari methodnya
    @Query("SELECT * FROM FoodBanner WHERE is_favorite = :isFavorite")
    LiveData<List<FoodBanner>> loadAllByFavorite(boolean isFavorite);

    @Query("SELECT * FROM FoodBanner WHERE title LIKE :title")
    LiveData<List<FoodBanner>> findByTitle(String title);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FoodBanner foodBanner);

    @Update
    void update(FoodBanner foodBanner);

    @Delete
    void delete(FoodBanner foodBanner);

    @Query("DELETE FROM FoodBanner")
    void nukeTable();
}
