package com.example.cookingrecipes.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private LoginApi loginApi;
    private FoodApi foodApi;
    Gson gsonLoginApi;
    Gson gsonFoodApi;

    public LoginApi getLoginApi(){
        this.gsonLoginApi = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();

        //Retrofit, membuat instance dan set gsonnya ke retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(LoginApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gsonLoginApi))
                .build();

        // Create Retrofitnya (Ibarat Kelas yang sudah jalannya/ON, instance ibarat masih OFF)
        this.loginApi = retrofit.create(LoginApi.class);

        return this.loginApi;
    }

    public FoodApi getFoodApi(){
        this.gsonFoodApi = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();

        //Retrofit, membuat instance dan set gsonnya ke retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(FoodApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gsonFoodApi))
                .build();

        // Create Retrofitnya (Ibarat Kelas yang sudah jalannya/ON, instance ibarat masih OFF)
        this.foodApi = retrofit.create(FoodApi.class);

        return this.foodApi;
    }
}
