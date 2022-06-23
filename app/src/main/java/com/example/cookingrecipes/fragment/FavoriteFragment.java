package com.example.cookingrecipes.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cookingrecipes.R;
import com.example.cookingrecipes.activity.DetailActivity;
import com.example.cookingrecipes.database.entity.FoodBanner;
import com.example.cookingrecipes.recycler_view.BtnClickableCallback;
import com.example.cookingrecipes.recycler_view.RVAdapterFoodBannerFavorite;
import com.example.cookingrecipes.recycler_view.RVAdapterFoodBannerHome;
import com.example.cookingrecipes.view_model.VMFoodBannerFavoriteRepository;
import com.example.cookingrecipes.view_model.VMFoodBannerRepositoryBridge;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoriteFragment#} factory method to
 * create an instance of this fragment.
 */
public class FavoriteFragment extends Fragment {

    private List<FoodBanner> foodBannerList = new ArrayList<>();
    private RVAdapterFoodBannerFavorite rvAdapterFoodBannerFavorite;
    private RecyclerView rvHolderFavorite;
    private VMFoodBannerRepositoryBridge vmFoodBannerRepositoryBridge;

    // Untuk Favorite
    String loginUserName="";
    private VMFoodBannerFavoriteRepository vmFoodBannerFavoriteRepository;
    private static final ExecutorService threadWorker = Executors.newFixedThreadPool(1);
    private Handler mainThread;

    public static final String LOGIN_PREFERENCE = "com.example.cookingrecipes.LOGIN_PREFERENCE";

    BtnClickableCallback btnClickableCallback = new BtnClickableCallback() {
        @Override
        public void onClick(View view, FoodBanner foodBanner, int position, boolean isButton) {
            String key = foodBanner.getKey();

            if(isButton) {
                threadWorker.execute(new Runnable() {
                    @Override
                    public void run() {
                        boolean isExist = vmFoodBannerFavoriteRepository.isExist(key, loginUserName);

                        if (isExist) {
                            vmFoodBannerFavoriteRepository.deleteFavorite(key, loginUserName);
                        } else {
                            vmFoodBannerFavoriteRepository.insertFavorite(key, loginUserName);
                        }
                        changeFavoriteButton(isExist, position);
                    }
                });
            }
            else{
                changeToDetailFragment(key, loginUserName);
            }

        }
    };

    public void changeToDetailFragment(String key, String username){
        Intent intent = new Intent(requireContext(), DetailActivity.class);
        intent.putExtra("login_username", username);
        intent.putExtra("food_banner_key", key);

        startActivity(intent);
    }

    public void changeFavoriteButton(boolean isExist, int position){
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                FoodBanner foodBanner = foodBannerList.get(position);
                if(isExist){
                    foodBanner.setFavorite(false);
                }
                else{
                    foodBanner.setFavorite(true);
                }
                foodBannerList.set(position, foodBanner);
                rvAdapterFoodBannerFavorite.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.vmFoodBannerRepositoryBridge = new ViewModelProvider(requireActivity()).get(VMFoodBannerRepositoryBridge.class);
        this.vmFoodBannerFavoriteRepository = new ViewModelProvider(requireActivity()).get(VMFoodBannerFavoriteRepository.class);
        this.mainThread = new Handler(Looper.getMainLooper());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_favorite, container, false);
        this.loginUserName = requireContext().getSharedPreferences(LOGIN_PREFERENCE, Context.MODE_PRIVATE)
                .getString("login_username", "");

        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Untuk set adapternya beserta datanya
        rvAdapterFoodBannerFavorite = new RVAdapterFoodBannerFavorite(this.foodBannerList, btnClickableCallback, this.loginUserName);

        rvHolderFavorite = view.findViewById(R.id.rv_favorite_holder);
        rvHolderFavorite.setAdapter(rvAdapterFoodBannerFavorite);
        rvHolderFavorite.setLayoutManager(new LinearLayoutManager(requireActivity()));

        initLoadDB();
    }

    public void initLoadDB(){
        this.vmFoodBannerRepositoryBridge.getLiveDataAllFoodBanner().observe(getViewLifecycleOwner(), getList -> {
            foodBannerList.clear();
            foodBannerList.addAll(getList);
            chainWithFavorite(foodBannerList, this.loginUserName);
        });
    }

    public void chainWithFavorite(List<FoodBanner> list, String username){

        threadWorker.execute(new Runnable() {
            @Override
            public void run() {
                List<FoodBanner> newFoodBannerList = new ArrayList<>();
                FoodBanner each;
                for (int i = 0; i < list.size(); i++) {
                    each = list.get(i);
                    boolean isExist = vmFoodBannerFavoriteRepository.isExist(each.getKey(), username);
                    if(isExist) {
                        each.setFavorite(true);
                        newFoodBannerList.add(each);
                    }
                }
                foodBannerList.clear();
                foodBannerList.addAll(newFoodBannerList);
                mainThread.post(new Runnable() {
                    @Override
                    public void run() {
                        rvAdapterFoodBannerFavorite.notifyDataSetChanged();
                    }
                });
            }
        });
    }
}