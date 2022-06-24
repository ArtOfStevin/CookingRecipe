package com.example.cookingrecipes.retrofit;

import com.example.cookingrecipes.model.ResponseFromFoodApi;
import com.example.cookingrecipes.model.ResponseFromFoodDetailApi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FoodApi {

    public String BASE_URL = "https://masak-apa.tomorisakura.vercel.app";

    @GET("/api/recipes")
    Call<ResponseFromFoodApi> getNewRecipes();

    @GET("/api/recipe/{key}")
    Call<ResponseFromFoodDetailApi> getDetailRecipes(@Path("key") String key);
}
