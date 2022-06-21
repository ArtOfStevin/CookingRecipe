package com.example.cookingrecipes.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.cookingrecipes.R;
import com.example.cookingrecipes.fragment.FavoriteFragment;
import com.example.cookingrecipes.fragment.HomeFragment;
import com.example.cookingrecipes.fragment.SearchFragment;
import com.example.cookingrecipes.fragment.UserDetailFragment;
import com.example.cookingrecipes.logic.SessionManagementUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.flFragmentHolder, new HomeFragment())
                    .commitNow();
        }

        this.bottomNavigationView = findViewById(R.id.bottomNavViewHolder);
        initBottomNavigationView();
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

    private void initBottomNavigationView(){
        this.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_home:
                        changeFragment(new HomeFragment());
                        return true;
                    case R.id.navigation_search:
                        changeFragment(new SearchFragment());
                        return true;
                    case R.id.navigation_favorite:
                        changeFragment(new FavoriteFragment());
                        return true;
                    case R.id.navigation_user:
                        changeFragment(new UserDetailFragment());
                        return true;
                }
                return false;
            }
        });
    }

    private void changeFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                    .replace(R.id.flFragmentHolder, fragment)
                    .commitNow();
    }
}