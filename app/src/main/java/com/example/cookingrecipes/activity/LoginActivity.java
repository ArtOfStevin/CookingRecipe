package com.example.cookingrecipes.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.cookingrecipes.R;
import com.example.cookingrecipes.fragment.LoginFragment;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activityLogin, new LoginFragment())
                    .commitNow();
        }
    }
}