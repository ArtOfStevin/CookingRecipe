package com.example.cookingrecipes.recycler_view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookingrecipes.R;
import com.example.cookingrecipes.model.FoodBanner;

import java.util.List;

public class RVAdapterFoodBanner extends RecyclerView.Adapter<RVAdapterFoodBanner.ViewHolder>{

    private List<FoodBanner> foodBannerList;
    private Context context;

    public RVAdapterFoodBanner(List<FoodBanner> foodBannerList, Context context){
        this.foodBannerList = foodBannerList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_unit_food_banner, parent, false);
//        View view = LayoutInflater.from(this.context).inflate(R.layout.rv_unit_food_banner, parent, false);
        return new RVAdapterFoodBanner.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FoodBanner foodBanner = foodBannerList.get(position);
        holder.tvTitle.setText(foodBanner.getTitle());
        holder.tvSummary.setText(foodBanner.getSummary());

        holder.tvTimeNeeded.setText(foodBanner.getTimeNeeded());
        holder.tvServePortion.setText(foodBanner.getServePortion());
        holder.tvDifficulty.setText(foodBanner.getDifficulty());
        holder.ivFoodPicture.setImageResource(R.drawable.ribbon);
    }

    @Override
    public int getItemCount() {
        return foodBannerList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvTitle;
        TextView tvSummary;

        TextView tvTimeNeeded;
        TextView tvServePortion;
        TextView tvDifficulty;

        ImageView ivFoodPicture;
        Button btnFavorite;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvSummary = itemView.findViewById(R.id.tvSummary);

            tvTimeNeeded = itemView.findViewById(R.id.tvTimeNeeded);
            tvServePortion = itemView.findViewById(R.id.tvServePortion);
            tvDifficulty = itemView.findViewById(R.id.tvDifficulty);

            btnFavorite = itemView.findViewById(R.id.btnFavorite);
            ivFoodPicture = itemView.findViewById(R.id.ivFoodPicture);
        }
    }
}
