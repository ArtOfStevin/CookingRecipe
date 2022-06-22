package com.example.cookingrecipes.model;

import com.google.gson.annotations.SerializedName;

public class ResponseFromUserApi {

    @SerializedName("data")
    private UserFromApi userData;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private boolean status;

    @SerializedName("token")
    private String token;

    public void setData(UserFromApi userData){
        this.userData = userData;
    }

    public UserFromApi getData(){
        return userData;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

    public void setStatus(boolean status){
        this.status = status;
    }

    public boolean isStatus(){
        return status;
    }

    public void setToken(String token){
        this.token = token;
    }

    public String getToken(){
        return token;
    }
}
