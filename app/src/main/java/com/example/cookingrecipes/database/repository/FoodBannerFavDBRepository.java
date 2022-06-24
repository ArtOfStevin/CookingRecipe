package com.example.cookingrecipes.database.repository;

import android.app.Application;

import com.example.cookingrecipes.database.FoodBannerDatabase;
import com.example.cookingrecipes.database.FoodBannerFavDao;
import com.example.cookingrecipes.database.FoodBannerFavDatabase;
import com.example.cookingrecipes.database.entity.FoodBannerFavorite;

import java.util.List;

public class FoodBannerFavDBRepository {

    private FoodBannerFavDao foodBannerFavDao;

    public FoodBannerFavDBRepository(Application application){
        FoodBannerFavDatabase database = FoodBannerFavDatabase.getDatabase(application);
        this.foodBannerFavDao = database.foodBannerFavDao();
    }

    public List<FoodBannerFavorite> getAll(){
        return this.foodBannerFavDao.getAllFavorite();
    }

    // Takutnya masukin data lebih dari 5 detik karena DB penuh
    public void insert(FoodBannerFavorite foodBannerFavorite){
        FoodBannerDatabase.databaseWorker.execute(new Runnable() {
            @Override
            public void run() {
                foodBannerFavDao.insert(foodBannerFavorite);
            }
        });
    }

    public void update(FoodBannerFavorite foodBannerFavorite){
        FoodBannerDatabase.databaseWorker.execute(new Runnable() {
            @Override
            public void run() {
                foodBannerFavDao.update(foodBannerFavorite);
            }
        });
    }

    public void delete(FoodBannerFavorite foodBannerFavorite){
        FoodBannerDatabase.databaseWorker.execute(new Runnable() {
            @Override
            public void run() {
                foodBannerFavDao.delete(foodBannerFavorite);
            }
        });
    }

    public void insertAll(List<FoodBannerFavorite> foodBannerFavList){

        FoodBannerDatabase.databaseWorker.execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < foodBannerFavList.size(); i++) {
                    foodBannerFavDao.insert(foodBannerFavList.get(i));
                }
            }
        });
    }

    public boolean isExist(String key, String username){

        FoodBannerFavorite foodBannerFavorite = this.foodBannerFavDao.findByKeyAndUsername(key, username);
        if(null == foodBannerFavorite){
            return false;
        }
        else{
            return true;
        }
    }

    public void clearTable(){
        this.foodBannerFavDao.nukeTable();
    }
}
