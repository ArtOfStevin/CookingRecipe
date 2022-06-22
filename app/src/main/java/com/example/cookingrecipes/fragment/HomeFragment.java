package com.example.cookingrecipes.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cookingrecipes.R;
import com.example.cookingrecipes.database.entity.FoodBanner;
import com.example.cookingrecipes.view_model.VMFoodBannerRepositoryBridge;
import com.example.cookingrecipes.recycler_view.RVAdapterFoodBanner;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private TextView tvTest;
    private List<FoodBanner> foodBannerList = new ArrayList<>();
    private RVAdapterFoodBanner rvAdapterFoodBanner;
    private RecyclerView rvHolderHome;
    private VMFoodBannerRepositoryBridge vmFoodBannerRepositoryBridge;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.vmFoodBannerRepositoryBridge = new ViewModelProvider(requireActivity()).get(VMFoodBannerRepositoryBridge.class);
//        this.vmFoodBannerRepositoryBridge.clearTable();
//        this.vmFoodBannerRepositoryBridge.injectApiDataToDB();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_home, container, false);

        tvTest = fragmentView.findViewById(R.id.tvTest);
        rvHolderHome = fragmentView.findViewById(R.id.rv_home_holder);
        String intent = requireActivity().getIntent().getStringExtra("loginUser");
        tvTest.setText(intent);

        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Untuk set adapternya beserta datanya
        rvAdapterFoodBanner = new RVAdapterFoodBanner(this.foodBannerList, requireContext());

        rvHolderHome.setAdapter(rvAdapterFoodBanner);
        rvHolderHome.setLayoutManager(new LinearLayoutManager(requireActivity()));

        this.vmFoodBannerRepositoryBridge.getLiveDataAllFoodBanner().observe(getViewLifecycleOwner(), getList -> {
            foodBannerList.clear();
            foodBannerList.addAll(getList);
            rvAdapterFoodBanner.notifyDataSetChanged();
        });
    }

}