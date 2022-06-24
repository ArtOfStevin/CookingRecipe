package com.example.cookingrecipes.model;

import com.google.gson.annotations.SerializedName;

public class ResponseFromFoodDetailApi {

    @SerializedName("method")
    private String method;

    @SerializedName("status")
    private String status;

    @SerializedName("results")
    private FoodDetailFromApi foodDetailFromApi;

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

    public FoodDetailFromApi getFoodDetailFromApi() {
        return foodDetailFromApi;
    }

    public void setFoodDetailFromApi(FoodDetailFromApi foodDetailFromApi) {
        this.foodDetailFromApi = foodDetailFromApi;
    }
}
