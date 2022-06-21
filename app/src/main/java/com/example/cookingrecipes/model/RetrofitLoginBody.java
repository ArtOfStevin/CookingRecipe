package com.example.cookingrecipes.model;

import com.google.gson.annotations.SerializedName;

public class RetrofitLoginBody {

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
