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
import android.widget.Button;
import android.widget.EditText;

import com.example.cookingrecipes.R;
import com.example.cookingrecipes.database.entity.FoodBanner;
import com.example.cookingrecipes.recycler_view.RVAdapterFoodBanner;
import com.example.cookingrecipes.view_model.VMFoodBannerRepositoryBridge;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    private List<FoodBanner> foodBannerList = new ArrayList<>();
    private RVAdapterFoodBanner rvAdapterFoodBanner;
    private RecyclerView rvHolderSearch;

    private Button btnSearch;
    private EditText etSearch;
    private VMFoodBannerRepositoryBridge vmFoodBannerRepositoryBridge;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.vmFoodBannerRepositoryBridge = new ViewModelProvider(requireActivity()).get(VMFoodBannerRepositoryBridge.class);
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
        this.rvAdapterFoodBanner = new RVAdapterFoodBanner(this.foodBannerList, requireContext());

        this.rvHolderSearch = view.findViewById(R.id.rv_search_holder);
        this.rvHolderSearch.setAdapter(this.rvAdapterFoodBanner);
        this.rvHolderSearch.setLayoutManager(new LinearLayoutManager(requireActivity()));

        this.vmFoodBannerRepositoryBridge.getLiveDataAllFoodBanner().observe(getViewLifecycleOwner(), getList -> {
            foodBannerList.clear();
            foodBannerList.addAll(getList);
            rvAdapterFoodBanner.notifyDataSetChanged();
        });

        initOnClick();
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
        if(query.equals("")){
            resetData();
        }
        else{
            resetData();
            query = query.toLowerCase();
            searchingProcess(query);
        }
    }

    private void resetData(){
        this.foodBannerList.clear();
        this.foodBannerList.addAll(FoodBanner.generateFoodBannerList());
        this.rvAdapterFoodBanner.notifyDataSetChanged();
    }

    private void searchingProcess(String query){
        List<FoodBanner> resultFound = doSearchItems(query);

        this.foodBannerList.clear();
        this.foodBannerList.addAll(resultFound);
        this.rvAdapterFoodBanner.notifyDataSetChanged();
    }

    // First Found
    private List<FoodBanner> doSearchItems(String query){
        List<FoodBanner> resultFound = new ArrayList<>();

        for (FoodBanner foodBanner:this.foodBannerList) {
            if( foodBanner.getTitle().toLowerCase().contains(query) ){
                resultFound.add(foodBanner);
            }
        }
        return resultFound;
    }
}