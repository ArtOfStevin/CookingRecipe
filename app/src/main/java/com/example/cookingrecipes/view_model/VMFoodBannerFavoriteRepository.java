package com.example.cookingrecipes.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.cookingrecipes.database.entity.FoodBannerFavorite;
import com.example.cookingrecipes.database.repository.FoodBannerFavDBRepository;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class VMFoodBannerFavoriteRepository extends AndroidViewModel {

    private FoodBannerFavDBRepository foodBannerFavDBRepository;
    private static final ExecutorService threadWorker = Executors.newFixedThreadPool(1);

    public VMFoodBannerFavoriteRepository(@NonNull Application application){
        super(application);

        this.foodBannerFavDBRepository = new FoodBannerFavDBRepository(application);
    }

    public void insertFavorite(String key, String username){
        FoodBannerFavorite foodBannerFavorite = new FoodBannerFavorite();
        foodBannerFavorite.setKey(key);
        foodBannerFavorite.setUsername(username);

        this.foodBannerFavDBRepository.insert(foodBannerFavorite);
    }

    public void deleteFavorite(String key, String username){
        FoodBannerFavorite foodBannerFavorite = new FoodBannerFavorite();
        foodBannerFavorite.setKey(key);
        foodBannerFavorite.setUsername(username);

        this.foodBannerFavDBRepository.delete(foodBannerFavorite);
    }

    public boolean isExist(String key, String username){
        return this.foodBannerFavDBRepository.isExist(key, username);
    }

    public List<FoodBannerFavorite> getAllByUser(String username){
        return this.foodBannerFavDBRepository.getAllByUser(username);
    }

}
