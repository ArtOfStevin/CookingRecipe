package com.example.cookingrecipes.retrofit;

import com.example.cookingrecipes.model.ResponseFromUserApi;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface LoginApi {

    // Trailing slash is needed
    String BASE_URL = "https://talentpool.oneindonesia.id";

    @Headers({"X-API-Key: 454041184B0240FBA3AACD15A1F7ABBB", "Content-type: application/x-www-form-urlencoded"})
    @POST ("/api/user/login")
    @FormUrlEncoded
    Call<ResponseFromUserApi> getUserDetail(@Field("username") String username,
                                            @Field("password") String password);
}
