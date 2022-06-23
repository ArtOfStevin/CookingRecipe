package com.example.cookingrecipes.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cookingrecipes.R;
import com.example.cookingrecipes.fragment.DetailFragment;
import com.example.cookingrecipes.logic.SessionManagementUtil;

import java.util.Calendar;

public class DetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.flDetailActivityFragmentHolder, new DetailFragment())
                    .commitNow();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        boolean isSessionAllowed =
                SessionManagementUtil.getInstance().isSessionActive(this, Calendar.getInstance().getTime());

        if(!isSessionAllowed){
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}