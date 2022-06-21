package com.example.cookingrecipes.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cookingrecipes.R;
import com.example.cookingrecipes.model.FoodBanner;
import com.example.cookingrecipes.recycler_view.RVAdapterFoodBanner;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoriteFragment#} factory method to
 * create an instance of this fragment.
 */
public class FavoriteFragment extends Fragment {

    List<FoodBanner> foodBannerList = new ArrayList<>();
    RVAdapterFoodBanner rvAdapterFoodBanner;
    RecyclerView rvHolderFavorite;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_favorite, container, false);

        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Untuk set adapternya beserta datanya
        rvAdapterFoodBanner = new RVAdapterFoodBanner(FoodBanner.generateFoodBannerList(), requireContext());

        rvHolderFavorite = view.findViewById(R.id.rv_favorite_holder);
        rvHolderFavorite.setAdapter(rvAdapterFoodBanner);
        rvHolderFavorite.setLayoutManager(new LinearLayoutManager(requireActivity()));
    }
}