package com.example.cookingrecipes.recycler_view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookingrecipes.R;
import com.example.cookingrecipes.database.entity.FoodBanner;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RVAdapterFoodBanner extends RecyclerView.Adapter<RVAdapterFoodBanner.ViewHolder>{

    private List<FoodBanner> foodBannerList;
    private Context context;
    public static final ExecutorService threadWorker = Executors.newFixedThreadPool(1);
    private Handler mainThread;

    public RVAdapterFoodBanner(List<FoodBanner> foodBannerList, Context context){
        this.foodBannerList = foodBannerList;
        this.context = context;
        this.mainThread = new Handler(Looper.getMainLooper());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_unit_food_banner, parent, false);
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

        // Thread Worker untuk narik gambar (Stream) dari internetnya
        threadWorker.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    String url = foodBanner.getImageUrl();
                    InputStream inputStream = new URL(url).openStream();
                    Bitmap bm = BitmapFactory.decodeStream(inputStream);

                    // Untuk ubah UI aja
                    mainThread.post(new Runnable() {
                        @Override
                        public void run() {
                            holder.ivFoodPicture.setImageBitmap(bm);
                        }
                    });
                }
                catch(Exception e){
                    Log.wtf("IMAGELOG", "onBindViewHolder: " + e);
                }
            }
        });

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
