package com.example.cookingrecipes.recycler_view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookingrecipes.R;
import com.example.cookingrecipes.model.FoodDetailAdapterData;

import java.util.List;

public class RVAdapterFoodDetail extends RecyclerView.Adapter<RVAdapterFoodDetail.ViewHolder>{

    private List<FoodDetailAdapterData> foodDetailList;

    public RVAdapterFoodDetail(List<FoodDetailAdapterData> foodDetailList){
        this.foodDetailList = foodDetailList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_unit_food_detail, parent, false);

        return new RVAdapterFoodDetail.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FoodDetailAdapterData foodDetail = foodDetailList.get(position);

        holder.tvDetailSummary.setText(foodDetail.getSummary());
        holder.tvDetailItemNeeded.setText(foodDetail.getItemNeeded());
        holder.tvDetailIngredient.setText(foodDetail.getIngredient());
        holder.tvDetailStep.setText(foodDetail.getStep());
    }

    @Override
    public int getItemCount() {
        return foodDetailList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvDetailSummary;
        TextView tvDetailItemNeeded;
        TextView tvDetailIngredient;
        TextView tvDetailStep;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.tvDetailSummary = itemView.findViewById(R.id.tvDetailSummary);
            this.tvDetailItemNeeded = itemView.findViewById(R.id.tvDetailItemNeeded);
            this.tvDetailIngredient = itemView.findViewById(R.id.tvDetailIngredient);
            this.tvDetailStep = itemView.findViewById(R.id.tvDetailStep);
        }

    }
}
