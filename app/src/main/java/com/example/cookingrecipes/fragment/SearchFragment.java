package com.example.cookingrecipes.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.cookingrecipes.R;
import com.example.cookingrecipes.activity.DetailActivity;
import com.example.cookingrecipes.database.entity.FoodBanner;
import com.example.cookingrecipes.logic.SharedPreferenceManager;
import com.example.cookingrecipes.recycler_view.BtnClickableCallback;
import com.example.cookingrecipes.recycler_view.RVAdapterFoodBannerHome;
import com.example.cookingrecipes.recycler_view.RVAdapterFoodBannerSearch;
import com.example.cookingrecipes.view_model.VMFoodBannerFavoriteRepository;
import com.example.cookingrecipes.view_model.VMFoodBannerRepositoryBridge;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    private List<FoodBanner> foodBannerList = new ArrayList<>();
    private RVAdapterFoodBannerSearch rvAdapterFoodBannerSearch;
    private RecyclerView rvHolderSearch;

    private Button btnSearch;
    private EditText etSearch;
    private VMFoodBannerRepositoryBridge vmFoodBannerRepositoryBridge;

    // Untuk Favorite
    String loginUserName="";
    private VMFoodBannerFavoriteRepository vmFoodBannerFavoriteRepository;
    private static final ExecutorService threadWorker = Executors.newFixedThreadPool(1);
    private Handler mainThread;

    private SharedPreferenceManager sharedPreferenceManager;
    public AlertDialog.Builder alertDialogBuilder;

    // ----------------------------------------------------------------- Favorite Click Button Feedback -----------------------------------------------
    BtnClickableCallback btnClickableCallback = new BtnClickableCallback() {
        @Override
        public void onClick(View view, FoodBanner foodBanner, int position, boolean isButton) {
            String key = foodBanner.getKey();

            if(isButton) {
                threadWorker.execute(new Runnable() {
                    @Override
                    public void run() {
                        boolean isFavorite = vmFoodBannerFavoriteRepository.isExist(key, loginUserName);

                        mainThread.post(new Runnable() {
                            @Override
                            public void run() {
                                if (isFavorite) {
                                    alertDialogBuilder.setTitle("Are you sure to unliked ?")
                                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    setFavoriteButton(false, position);
                                                    vmFoodBannerFavoriteRepository.deleteFavorite(key, loginUserName);
                                                }
                                            })
                                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                }
                                            }).show();
                                } else {
                                    setFavoriteButton(true, position);
                                    vmFoodBannerFavoriteRepository.insertFavorite(key, loginUserName);
                                }
                            }
                        });
                    }
                });
            }
            else{
                changeToDetailFragment(key, loginUserName);
            }

        }
    };

    public void setFavoriteButton(boolean isFavorite, int position){
        FoodBanner foodBanner = foodBannerList.get(position);
        foodBanner.setFavorite(isFavorite);
        foodBannerList.set(position, foodBanner);
        rvAdapterFoodBannerSearch.notifyDataSetChanged();
    }

    public void changeToDetailFragment(String key, String username){
        sharedPreferenceManager.getEditor().putString("login_username", username);
        sharedPreferenceManager.getEditor().putString("food_banner_key", key);
        sharedPreferenceManager.getEditor().apply();

        Intent intent = new Intent(requireContext(), DetailActivity.class);
        startActivity(intent);
    }
    // ----------------------------------------------------------------- Favorite Click Button Feedback -----------------------------------------------

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.vmFoodBannerRepositoryBridge = new ViewModelProvider(requireActivity()).get(VMFoodBannerRepositoryBridge.class);
        this.vmFoodBannerFavoriteRepository = new ViewModelProvider(requireActivity()).get(VMFoodBannerFavoriteRepository.class);
        this.mainThread = new Handler(Looper.getMainLooper());

        this.sharedPreferenceManager = new SharedPreferenceManager(requireContext());
        this.loginUserName = this.sharedPreferenceManager.readString("login_username");
        this.alertDialogBuilder = new AlertDialog.Builder(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_search, container, false);
        this.btnSearch = fragmentView.findViewById(R.id.btnSearch);
        this.etSearch = fragmentView.findViewById(R.id.etSearch);

        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Untuk set adapternya beserta datanya
        this.rvAdapterFoodBannerSearch = new RVAdapterFoodBannerSearch(this.foodBannerList, btnClickableCallback, this.loginUserName);
        this.rvHolderSearch = view.findViewById(R.id.rv_search_holder);
        this.rvHolderSearch.setAdapter(this.rvAdapterFoodBannerSearch);
        this.rvHolderSearch.setLayoutManager(new LinearLayoutManager(requireActivity()));

        initOnClick();
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
                FoodBanner each;
                for (int i = 0; i < list.size(); i++) {
                    Log.d("CHAIN WITH FAV LOOP", "run: ");
                    each = list.get(i);
                    boolean isExist = vmFoodBannerFavoriteRepository.isExist(each.getKey(), username);
                    if(isExist) {
                        each.setFavorite(true);
                    }
                    else{
                        each.setFavorite(false);
                    }
                    foodBannerList.set(i,each);
                }
                mainThread.post(new Runnable() {
                    @Override
                    public void run() {
                        rvAdapterFoodBannerSearch.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    private void initOnClick(){

        this.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = etSearch.getText().toString();
                initSearchManager(query);
            }
        });
    }

    private void initSearchManager(String query){
        if(!query.equals("")){
            query = query.toLowerCase();
            this.vmFoodBannerRepositoryBridge.search(query).observe(getViewLifecycleOwner(), getList -> {
                foodBannerList.clear();
                foodBannerList.addAll(getList);
                chainWithFavorite(foodBannerList, this.loginUserName);
            });
        }
        else{
            initLoadDB();
        }
    }
}