package com.example.cookingrecipes.logic;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceManager {

    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final String PREFERENCE_MANAGER = "com.example.cookingrecipes.logic.SHARED_PREFERENCE_MANAGER";

    public SharedPreferenceManager(Context context){
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(PREFERENCE_MANAGER, Context.MODE_PRIVATE);
        this.editor = sharedPreferences.edit();
    }

    public SharedPreferences.Editor getEditor(){
        return this.editor;
    }

    public void writeString(String key, String value){
        editor.putString(key, value);
        editor.apply();
    }

    public String readString(String key){
        String result = this.context.getSharedPreferences(PREFERENCE_MANAGER, Context.MODE_PRIVATE)
                .getString(key, "");

        return result;
    }

    public boolean readBoolean(String key){
        boolean result = this.context.getSharedPreferences(PREFERENCE_MANAGER, Context.MODE_PRIVATE)
                .getBoolean(key, false);

        return result;
    }
}
