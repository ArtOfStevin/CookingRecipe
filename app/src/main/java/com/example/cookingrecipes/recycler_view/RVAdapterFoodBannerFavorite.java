package com.example.cookingrecipes.recycler_view;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookingrecipes.R;
import com.example.cookingrecipes.database.entity.FoodBanner;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RVAdapterFoodBannerFavorite extends RecyclerView.Adapter<RVAdapterFoodBannerFavorite.ViewHolder>{

    private List<FoodBanner> foodBannerList;
    private static final ExecutorService threadWorker = Executors.newFixedThreadPool(1);
    private Handler mainThread;

    private BtnClickableCallback btnClickableCallback;
    private int nightModeFlags;
    private String username;

    public RVAdapterFoodBannerFavorite(List<FoodBanner> foodBannerList, @NonNull BtnClickableCallback btnClickableCallback, String username){
        this.foodBannerList = foodBannerList;
        this.mainThread = new Handler(Looper.getMainLooper());
        this.btnClickableCallback = btnClickableCallback;
        this.username = username;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_unit_food_banner, parent, false);
        this.nightModeFlags =
                parent.getContext().getResources().getConfiguration().uiMode &
                        Configuration.UI_MODE_NIGHT_MASK;
        return new RVAdapterFoodBannerFavorite.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(foodBannerList.size() != 0) {
            FoodBanner foodBanner = foodBannerList.get(position);
            holder.tvTitle.setText(foodBanner.getTitle());

            holder.tvTimeNeeded.setText(foodBanner.getTimeNeeded());
            holder.tvServePortion.setText(foodBanner.getServePortion());
            holder.tvDifficulty.setText(foodBanner.getDifficulty());
            holder.ivFoodPicture.setImageResource(R.drawable.ribbon);
            boolean isFavorite = foodBanner.isFavorite();

            switch (nightModeFlags) {
                case Configuration.UI_MODE_NIGHT_YES:
                    if (isFavorite)
                        holder.ibFavorite.setImageResource(R.drawable.ic_baseline_favorite_24);
                    else
                        holder.ibFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_white);
                    holder.ivLogoTime.setImageResource(R.drawable.ic_baseline_access_time_white);
                    holder.ivLogoPortion.setImageResource(R.drawable.ic_baseline_fastfood_white);
                    holder.ivLogoDifficulty.setImageResource(R.drawable.ic_baseline_psychology_white);
                    break;

                case Configuration.UI_MODE_NIGHT_NO:
                    if (isFavorite)
                        holder.ibFavorite.setImageResource(R.drawable.ic_baseline_favorite_24);
                    else
                        holder.ibFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_black);
                    holder.ivLogoTime.setImageResource(R.drawable.ic_baseline_access_time_black);
                    holder.ivLogoPortion.setImageResource(R.drawable.ic_baseline_fastfood_black);
                    holder.ivLogoDifficulty.setImageResource(R.drawable.ic_baseline_psychology_black);
                    break;

                case Configuration.UI_MODE_NIGHT_UNDEFINED:
                    break;
            }

            // Thread Worker untuk narik gambar (Stream) dari internetnya
            threadWorker.execute(new Runnable() {
                @Override
                public void run() {
                    try {
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
                    } catch (Exception e) {
                        Log.wtf("IMAGELOG", "onBindViewHolder: " + e);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return foodBannerList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvTitle;

        TextView tvTimeNeeded;
        TextView tvServePortion;
        TextView tvDifficulty;

        ImageView ivFoodPicture;
        ImageButton ibFavorite;

        // Logo
        ImageView ivLogoTime;
        ImageView ivLogoPortion;
        ImageView ivLogoDifficulty;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);

            tvTimeNeeded = itemView.findViewById(R.id.tvTimeNeeded);
            tvServePortion = itemView.findViewById(R.id.tvServePortion);
            tvDifficulty = itemView.findViewById(R.id.tvDifficulty);

            ivFoodPicture = itemView.findViewById(R.id.ivFoodPicture);
            ibFavorite = itemView.findViewById(R.id.ibFavorite);

            ivLogoTime = itemView.findViewById(R.id.ivLogoTime);
            ivLogoPortion = itemView.findViewById(R.id.ivLogoPortion);
            ivLogoDifficulty = itemView.findViewById(R.id.ivLogoDifficulty);

            itemView.setOnClickListener(this);
            initBtnFavClick();
        }

        private void initBtnFavClick(){
            this.ibFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int postition = getAdapterPosition(); // gets item position

                    if(postition != RecyclerView.NO_POSITION) { // Check if on item was deleted
                        FoodBanner foodBanner = foodBannerList.get(postition);
                        btnClickableCallback.onClick(v, foodBanner, postition, true);
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {
            // Callback disini kalau mau on click ketika 1 foodbanner di click
            int postition = getAdapterPosition();

            if(postition != RecyclerView.NO_POSITION) { // Check if on item was deleted
                FoodBanner foodBanner = foodBannerList.get(postition);
                btnClickableCallback.onClick(v, foodBanner, postition, false);
            }
        }
    }
}
