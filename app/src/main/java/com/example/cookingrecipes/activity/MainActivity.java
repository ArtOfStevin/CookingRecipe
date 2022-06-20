package com.example.cookingrecipes.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.cookingrecipes.R;
import com.example.cookingrecipes.fragment.FavoriteFragment;
import com.example.cookingrecipes.fragment.HomeFragment;
import com.example.cookingrecipes.fragment.SearchFragment;
import com.example.cookingrecipes.fragment.UserDetailFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.flFragmentHolder, new HomeFragment())
                    .commitNow();
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavViewHolder);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
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