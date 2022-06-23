package com.example.cookingrecipes.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.cookingrecipes.database.FoodBannerDao;
import com.example.cookingrecipes.database.FoodBannerDatabase;
import com.example.cookingrecipes.database.entity.FoodBanner;

import java.util.List;

public class FoodBannerDBRepository {

    private FoodBannerDao foodBannerDao;
    private LiveData<List<FoodBanner>> foodBannerList;

    public FoodBannerDBRepository(Application application){
        FoodBannerDatabase database = FoodBannerDatabase.getDatabase(application);
        this.foodBannerDao = database.foodBannerDao();
        this.foodBannerList = this.foodBannerDao.getAll();
    }

    public LiveData<List<FoodBanner>> getAllFoodBanner(){
        // Untuk get data ga perlu pake thread worker karena udah di handle LiveData
        return this.foodBannerList;
    }

    public LiveData<List<FoodBanner>> search(String query){
        // Untuk get data ga perlu pake thread worker karena udah di handle LiveData
        return this.foodBannerDao.search(query);
    }

    // Takutnya masukin data lebih dari 5 detik karena DB penuh
    public void insert(FoodBanner foodBanner){
        FoodBannerDatabase.databaseWorker.execute(new Runnable() {
            @Override
            public void run() {
                foodBannerDao.insert(foodBanner);
            }
        });
    }

    public void update(FoodBanner foodBanner){
        FoodBannerDatabase.databaseWorker.execute(new Runnable() {
            @Override
            public void run() {
                foodBannerDao.update(foodBanner);
            }
        });
    }

    public void delete(FoodBanner foodBanner){
        FoodBannerDatabase.databaseWorker.execute(new Runnable() {
            @Override
            public void run() {
                foodBannerDao.delete(foodBanner);
            }
        });
    }

    public void insertAll(List<FoodBanner> foodBannerList){

        FoodBannerDatabase.databaseWorker.execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < foodBannerList.size(); i++) {
                    foodBannerDao.insert(foodBannerList.get(i));
                }
            }
        });
    }

    public void clearTable(){
        this.foodBannerDao.nukeTable();
    }
}
