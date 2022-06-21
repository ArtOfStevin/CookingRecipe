package com.example.cookingrecipes.retrofit;

import com.example.cookingrecipes.model.UserFromApi;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FoodApi {

    public String BASE_URL = "https://masak-apa.tomorisakura.vercel.app";

    @GET("/api/recipes")
    Call<UserFromApi> getNewRecipes();
}
