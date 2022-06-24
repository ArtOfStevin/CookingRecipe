package com.example.cookingrecipes.recycler_view;

import android.view.View;

import com.example.cookingrecipes.database.entity.FoodBanner;

public interface BtnClickableCallback {

    void onClick(View view, FoodBanner foodBanner, int position, boolean isButton);
}
