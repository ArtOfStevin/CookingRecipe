package com.example.cookingrecipes.recycler_view;

import android.view.View;
import android.widget.Button;

import com.example.cookingrecipes.database.entity.FoodBanner;

public interface BtnClickableCallback {

    void onClick(View view, FoodBanner foodBanner, int position, Button button);
}
