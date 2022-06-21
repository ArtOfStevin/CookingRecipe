package com.example.cookingrecipes.logic;


import android.content.Context;
import android.content.SharedPreferences;

import java.util.Calendar;
import java.util.Date;

public class SessionManagementUtil {

    public static final String SESSION_PREFERENCE = "com.example.cookingrecipes.logic.SessionManagementUtil.SESSION_PREFERENCE";
    public static final String SESSION_TOKEN = "com.example.cookingrecipes.logic.SessionManagementUtil.SESSION_TOKEN";
    public static final String SESSION_EXPIRY_TIME = "com.example.cookingrecipes.logic.SessionManagementUtil.SESSION_EXPIRY_TIME";

    private static SessionManagementUtil INSTANCE;
    public static SessionManagementUtil getInstance(){
        if(INSTANCE == null){
            INSTANCE = new SessionManagementUtil();
        }
        return INSTANCE;
    }

    public void startUserSession(Context context, int expiredIn){
        Calendar calendar = Calendar.getInstance();
        Date userLoggedTime = calendar.getTime();
        calendar.setTime(userLoggedTime);
        calendar.add(Calendar.SECOND, expiredIn);
        Date expiryTime = calendar.getTime();

        // Simpan expire date session ke share preference
        SharedPreferences sharedPreferences = context.getSharedPreferences(SESSION_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(SESSION_EXPIRY_TIME, expiryTime.getTime());
        editor.apply();
    }

    public boolean isSessionActive(Context context, Date currentTime){
        long longExpiryTime = context.getSharedPreferences(SESSION_PREFERENCE, Context.MODE_PRIVATE)
                .getLong(SESSION_EXPIRY_TIME, 0);
        Date expiryTime = new Date(longExpiryTime);

        return !currentTime.after(expiryTime);
    }

    public void clearStoredData(Context context){
        SharedPreferences.Editor editor = context.getSharedPreferences(SESSION_PREFERENCE, Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
    }

    public void storeUserToken(Context context, String token){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SESSION_PREFERENCE, Context.MODE_PRIVATE);
        // Singleton, kalau dipanggil method edit terus, dia buat instance terus
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SESSION_TOKEN, token);
        editor.apply();
    }

    private String getUserToken(Context context){
        return context.getSharedPreferences(SESSION_PREFERENCE, Context.MODE_PRIVATE).getString(SESSION_TOKEN, null);
    }

}
