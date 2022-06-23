package com.example.cookingrecipes.fragment;

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
import android.widget.Button;
import android.widget.ImageButton;

import com.example.cookingrecipes.R;
import com.example.cookingrecipes.database.entity.FoodBanner;
import com.example.cookingrecipes.recycler_view.BtnClickableCallback;
import com.example.cookingrecipes.recycler_view.RVAdapterFoodBanner;
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
    private RVAdapterFoodBanner rvAdapterFoodBanner;
    private RecyclerView rvHolderFavorite;
    private VMFoodBannerRepositoryBridge vmFoodBannerRepositoryBridge;

    // Untuk Favorite
    String loginUserName="";
    private VMFoodBannerFavoriteRepository vmFoodBannerFavoriteRepository;
    private static final ExecutorService threadWorker = Executors.newFixedThreadPool(1);
    private Handler mainThread;

    BtnClickableCallback btnClickableCallback = new BtnClickableCallback() {
        @Override
        public void onClick(View view, FoodBanner foodBanner, int position, boolean isButton) {
            String key = foodBanner.getKey();

            threadWorker.execute(new Runnable() {
                @Override
                public void run() {
                    boolean isExist = vmFoodBannerFavoriteRepository.isExist(key, loginUserName);

                    if(isExist){
                        vmFoodBannerFavoriteRepository.deleteFavorite(key, loginUserName);
                    }
                    else{
                        vmFoodBannerFavoriteRepository.insertFavorite(key, loginUserName);
                    }
                    changeFavoriteButton(isExist, position);
                }
            });

        }
    };

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
                rvAdapterFoodBanner.notifyDataSetChanged();
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
        this.loginUserName = requireActivity().getIntent().getStringExtra("login_username");

        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Untuk set adapternya beserta datanya
        rvAdapterFoodBanner = new RVAdapterFoodBanner(this.foodBannerList, btnClickableCallback, this.loginUserName);

        rvHolderFavorite = view.findViewById(R.id.rv_favorite_holder);
        rvHolderFavorite.setAdapter(rvAdapterFoodBanner);
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
                for (int i = 0; i < list.size(); i++) {
                    FoodBanner each = list.get(i);
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
                        rvAdapterFoodBanner.notifyDataSetChanged();
                    }
                });
            }
        });
    }
}