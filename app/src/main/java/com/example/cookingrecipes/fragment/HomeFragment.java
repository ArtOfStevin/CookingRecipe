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
import android.widget.TextView;

import com.example.cookingrecipes.R;
import com.example.cookingrecipes.model.FoodBanner;
import com.example.cookingrecipes.recycler_view.RVAdapterFoodBanner;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    TextView tvTest;
    List<FoodBanner> foodBannerList = new ArrayList<>();
    RVAdapterFoodBanner rvAdapterFoodBanner;
    RecyclerView rvHolderHome;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_home, container, false);

        tvTest = fragmentView.findViewById(R.id.tvTest);
        String intent = requireActivity().getIntent().getStringExtra("loginUser");
        tvTest.setText(intent);

        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Untuk set adapternya beserta datanya
        rvAdapterFoodBanner = new RVAdapterFoodBanner(FoodBanner.generateFoodBannerList());

        rvHolderHome = view.findViewById(R.id.rv_home_holder);
        rvHolderHome.setAdapter(rvAdapterFoodBanner);
        rvHolderHome.setLayoutManager(new LinearLayoutManager(requireActivity()));
    }

}