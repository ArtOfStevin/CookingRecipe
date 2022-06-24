package com.example.cookingrecipes.logic;

import com.example.cookingrecipes.model.FoodDetailNeedItem;

import java.util.List;

public class DetailFragmentHelper {

    public String prepareForItemNeeded(List<FoodDetailNeedItem> listNeedItem){
        String result = "Item Needed:\n\n";

        for (int i = 0; i < listNeedItem.size(); i++) {
            if(i==listNeedItem.size()-1) result += "- " + listNeedItem.get(i).getItemName();
            else result += "- " + listNeedItem.get(i).getItemName() + "\n";
        }

        return result;
    }

    public String prepareForIngredient(List<String> listIngredient){
        String result = "Ingredient:\n\n";

        for (int i = 0; i < listIngredient.size(); i++) {
            if(i==listIngredient.size()-1) result += "- " + listIngredient.get(i);
            else result += "- " + listIngredient.get(i) + "\n";
        }

        return result;
    }

    public String prepareForStep(List<String> listStep){
        String result = "\nStep:\n\n";

        for (int i = 0; i < listStep.size(); i++) {
            if(i==listStep.size()-1) result += "- " + listStep.get(i);
            else result += "- " + listStep.get(i) + "\n";
        }

        return result;
    }
}
