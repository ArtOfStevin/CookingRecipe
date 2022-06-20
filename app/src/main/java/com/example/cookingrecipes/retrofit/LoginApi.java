package com.example.cookingrecipes.retrofit;

import com.example.cookingrecipes.model.RetrofitLoginBody;
import com.example.cookingrecipes.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface LoginApi {

    public String BASE_URL = "https://talentpool.oneindonesia.id/api";

//    @GET("/users")
//    Call<List<User>> getUsers();
//
//    @GET("/users/{id}")
//    Call<User> getUsers(@Path("id") String id);

    @FormUrlEncoded
    @POST("/user/login")
    Call<User> verifUser(
            @Header("X-API-KEY") String apiKey,
            @Body RetrofitLoginBody body
    );
}
