package com.example.cookingrecipes.view_model;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.cookingrecipes.database.entity.FoodBanner;
import com.example.cookingrecipes.database.repository.FoodBannerDBRepository;
import com.example.cookingrecipes.model.FoodBannerFromApi;
import com.example.cookingrecipes.model.ResponseFromFoodApi;
import com.example.cookingrecipes.retrofit.FoodApi;
import com.example.cookingrecipes.retrofit.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VMFoodBannerRepositoryBridge extends AndroidViewModel {

    private RetrofitInstance retrofitInstance;
    private FoodBannerDBRepository foodBannerDBRepository;
    private FoodApi foodApi;
    public static final ExecutorService threadWorker = Executors.newFixedThreadPool(1);

    public VMFoodBannerRepositoryBridge(@NonNull Application application){
        super(application);

        this.retrofitInstance = new RetrofitInstance();
        this.foodApi = retrofitInstance.getFoodApi();
        this.foodBannerDBRepository = new FoodBannerDBRepository(application);
    }

    public void injectApiDataToDB(){
        this.foodApi.getNewRecipes().enqueue(new Callback<ResponseFromFoodApi>() {
            @Override
            public void onResponse(Call<ResponseFromFoodApi> call, Response<ResponseFromFoodApi> response) {
                Log.d("foodApi.getNewRecipes.onResponse","Berhasil");
                ResponseFromFoodApi responseFromFoodApi = response.body();
                List<FoodBannerFromApi> foodBannerlist = responseFromFoodApi.getFoodBannerlist();

                foodBannerDBRepository.insertAll(convertToEntityFromApi(foodBannerlist));
            }

            @Override
            public void onFailure(Call<ResponseFromFoodApi> call, Throwable t) {
                Log.d("foodApi.getNewRecipes.onFailure","Gagal");
            }
        });
    }

    public List<FoodBanner> convertToEntityFromApi(List<FoodBannerFromApi> foodBannerlistApi){
        List<FoodBanner> result = new ArrayList<>();

        for (int i = 0; i < foodBannerlistApi.size(); i++) {
            FoodBanner temp = new FoodBanner();
            FoodBannerFromApi tempApi = foodBannerlistApi.get(i);
            temp.setKey(tempApi.getKey());
            temp.setTitle(tempApi.getTitle());
            temp.setImageUrl(tempApi.getImageUrl());
            temp.setSummary("Summary");
            temp.setFavorite(false);
            temp.setTimeNeeded(tempApi.getTimeNeeded());
            temp.setServePortion(tempApi.getServePortion());
            temp.setDifficulty(tempApi.getDificulty());

            result.add(temp);
        }

        return result;
    }

    public List<FoodBanner> getAllFoodBanner(){
        return this.foodBannerDBRepository.getAllFoodBanner().getValue();
    }

    public LiveData<List<FoodBanner>> getLiveDataAllFoodBanner(){
        return this.foodBannerDBRepository.getAllFoodBanner();
    }

    public void clearTable(){
        this.threadWorker.execute(new Runnable() {
            @Override
            public void run() {
                foodBannerDBRepository.clearTable();
            }
        });
    }

    public LiveData<List<FoodBanner>> search(String query){
        return this.foodBannerDBRepository.search(query);
    }
}
