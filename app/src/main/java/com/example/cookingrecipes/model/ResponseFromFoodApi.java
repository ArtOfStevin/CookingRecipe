package com.example.cookingrecipes.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseFromFoodApi {

    @SerializedName("method")
    private String method;

    @SerializedName("status")
    private String status;

    @SerializedName("results")
    private List<FoodBannerFromApi> foodBannerlist;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<FoodBannerFromApi> getFoodBannerlist() {
        return foodBannerlist;
    }

    public void setFoodBannerlist(List<FoodBannerFromApi> foodBannerlist) {
        this.foodBannerlist = foodBannerlist;
    }
}
