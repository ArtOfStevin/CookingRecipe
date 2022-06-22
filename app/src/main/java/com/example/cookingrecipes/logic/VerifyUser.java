package com.example.cookingrecipes.logic;

public class VerifyUser {

    public boolean isValid(String username, String password){
        boolean result = false;

        if(username.equals("user") && password.equals("pass") ){
            result = true;
        }

//        return true;
        return result;
    }
}
