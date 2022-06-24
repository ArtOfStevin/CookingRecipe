package com.example.cookingrecipes.logic;

import com.example.cookingrecipes.database.entity.FoodBanner;
import com.example.cookingrecipes.database.entity.FoodBannerFavorite;

import java.util.List;

public class FoodBannerHelper {

    public List<FoodBanner> mergeWithFav(List<FoodBanner> foodBannerList, List<FoodBannerFavorite> foodBannerFavoriteList){
        FoodBanner temp;
        FoodBannerFavorite tempFav;

        for (int i = 0; i < foodBannerList.size(); i++) {
            temp = foodBannerList.get(i);
            for (int j = 0; j < foodBannerFavoriteList.size(); j++) {
                tempFav = foodBannerFavoriteList.get(j);
                temp.setFavorite(false);
                if(temp.getKey().equalsIgnoreCase(tempFav.getKey())){
                    temp.setFavorite(true);
                    break;
                }
            }
            foodBannerList.set(i, temp);
        }

        return foodBannerList;
    }
}
