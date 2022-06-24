package com.example.cookingrecipes.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cookingrecipes.R;
import com.example.cookingrecipes.logic.DetailFragmentHelper;
import com.example.cookingrecipes.logic.SharedPreferenceManager;
import com.example.cookingrecipes.model.FoodDetailAdapterData;
import com.example.cookingrecipes.model.FoodDetailFromApi;
import com.example.cookingrecipes.model.ResponseFromFoodDetailApi;
import com.example.cookingrecipes.recycler_view.RVAdapterFoodDetail;
import com.example.cookingrecipes.retrofit.FoodApi;
import com.example.cookingrecipes.retrofit.RetrofitInstance;
import com.example.cookingrecipes.view_model.VMFoodBannerFavoriteRepository;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailFragment} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {

    private TextView tvDetailTitle;

    private TextView tvDetailTimeNeeded;
    private TextView tvDetailServePortion;
    private TextView tvDetailDifficulty;

    private ImageView ivDetailFoodPicture;

    // Logo
    private ImageView ivDetailLogoTime;
    private ImageView ivDetailLogoPortion;
    private ImageView ivDetailLogoDifficulty;

    private ImageButton ibDetailFavorite;

    private String loginUserName="";
    private String foodBannerKey="";

    private RetrofitInstance retrofitInstance;
    private FoodApi foodApi;
    ResponseFromFoodDetailApi responseFromFoodDetailApi;
    FoodDetailFromApi foodDetailFromApi;
    private static final ExecutorService threadWorker = Executors.newFixedThreadPool(1);
    private Handler mainThread;

    private int nightModeFlags;
    private RVAdapterFoodDetail rvAdapterFoodDetail;
    private List<FoodDetailAdapterData> foodDetailAdapterDataList;
    private DetailFragmentHelper detailFragmentHelper;
    private RecyclerView rvHolderDetail;

    private SharedPreferenceManager sharedPreferenceManager;
    private VMFoodBannerFavoriteRepository vmFoodBannerFavoriteRepository;
    private AlertDialog.Builder alertDialogBuilder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.retrofitInstance = new RetrofitInstance();
        this.foodApi = retrofitInstance.getFoodApi();
        this.responseFromFoodDetailApi = new ResponseFromFoodDetailApi();
        foodDetailFromApi = new FoodDetailFromApi();
        this.mainThread = new Handler(Looper.getMainLooper());
        this.foodDetailAdapterDataList = new ArrayList<>();
        this.detailFragmentHelper = new DetailFragmentHelper();
        this.sharedPreferenceManager = new SharedPreferenceManager(requireContext());

        this.vmFoodBannerFavoriteRepository = new ViewModelProvider(requireActivity()).get(VMFoodBannerFavoriteRepository.class);
        this.alertDialogBuilder = new AlertDialog.Builder(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_detail, container, false);

        this.nightModeFlags =
                requireContext().getResources().getConfiguration().uiMode &
                        Configuration.UI_MODE_NIGHT_MASK;

        this.tvDetailTitle = fragmentView.findViewById(R.id.tvDetailTitle);

        this.tvDetailTimeNeeded = fragmentView.findViewById(R.id.tvDetailTimeNeeded);
        this.tvDetailServePortion = fragmentView.findViewById(R.id.tvDetailServePortion);
        this.tvDetailDifficulty = fragmentView.findViewById(R.id.tvDetailDifficulty);

        this.ivDetailFoodPicture = fragmentView.findViewById(R.id.ivDetailFoodPicture);
        this.ivDetailFoodPicture.setImageResource(R.drawable.ribbon);

        this.ivDetailLogoTime = fragmentView.findViewById(R.id.ivDetailLogoTime);
        this.ivDetailLogoPortion = fragmentView.findViewById(R.id.ivDetailLogoPortion);
        this.ivDetailLogoDifficulty = fragmentView.findViewById(R.id.ivDetailLogoDifficulty);
        this.ibDetailFavorite = fragmentView.findViewById(R.id.ibDetailFavorite);

        this.loginUserName = this.sharedPreferenceManager.readString("login_username");
        this.foodBannerKey = this.sharedPreferenceManager.readString("food_banner_key");

        this.rvHolderDetail = fragmentView.findViewById(R.id.rv_detail_holder);

        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.adjustWithTheme(nightModeFlags);
        rvAdapterFoodDetail = new RVAdapterFoodDetail(foodDetailAdapterDataList);
        rvHolderDetail.setAdapter(rvAdapterFoodDetail);
        rvHolderDetail.setLayoutManager(new LinearLayoutManager(requireActivity()));

        setOnClickImageButton();
        getDataFromApi(foodBannerKey, loginUserName);
    }

    public void adjustWithTheme(int nightModeFlags){
        initImageBtn();
        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                ivDetailLogoTime.setImageResource(R.drawable.ic_baseline_access_time_white);
                ivDetailLogoPortion.setImageResource(R.drawable.ic_baseline_fastfood_white);
                ivDetailLogoDifficulty.setImageResource(R.drawable.ic_baseline_psychology_white);
                break;

            case Configuration.UI_MODE_NIGHT_NO:
                ivDetailLogoTime.setImageResource(R.drawable.ic_baseline_access_time_black);
                ivDetailLogoPortion.setImageResource(R.drawable.ic_baseline_fastfood_black);
                ivDetailLogoDifficulty.setImageResource(R.drawable.ic_baseline_psychology_black);
                break;

            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                break;
        }
    }

    public void initImageBtn(){
        threadWorker.execute(new Runnable() {
            @Override
            public void run() {
                boolean isFav = vmFoodBannerFavoriteRepository.isExist(foodBannerKey, loginUserName);
                if (!isFav)
                    if(nightModeFlags == Configuration.UI_MODE_NIGHT_YES) ibDetailFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_white);
                    else ibDetailFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_black);
                else
                    ibDetailFavorite.setImageResource(R.drawable.ic_baseline_favorite_24);
            }
        });
    }

    public void setOnClickImageButton(){
        this.ibDetailFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                threadWorker.execute(new Runnable() {
                    @Override
                    public void run() {
                        boolean isFavorite = vmFoodBannerFavoriteRepository.isExist(foodBannerKey, loginUserName);

                        mainThread.post(new Runnable() {
                            @Override
                            public void run() {
                                if (isFavorite) {
                                    alertDialogBuilder.setTitle("Are you sure to unliked ?")
                                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    setFavoriteButton(false);
                                                    vmFoodBannerFavoriteRepository.deleteFavorite(foodBannerKey, loginUserName);
                                                }
                                            })
                                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                }
                                            }).show();
                                } else {
                                    setFavoriteButton(true);
                                    vmFoodBannerFavoriteRepository.insertFavorite(foodBannerKey, loginUserName);
                                }
                            }
                        });
                    }
                });
            }
        });
    }

    public void setFavoriteButton(boolean isFavorite){
        if (!isFavorite) {
            if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES)
                ibDetailFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_white);
            else
                ibDetailFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_black);
            vmFoodBannerFavoriteRepository.deleteFavorite(foodBannerKey, loginUserName);
        }
        else{
            ibDetailFavorite.setImageResource(R.drawable.ic_baseline_favorite_24);
            vmFoodBannerFavoriteRepository.insertFavorite(foodBannerKey, loginUserName);
        }
    }

    public void getDataFromApi(String key, String username){
        System.out.println("FOOD BANNER KEY: "+key);
        this.foodApi.getDetailRecipes(key).enqueue(new Callback<ResponseFromFoodDetailApi>() {
            @Override
            public void onResponse(Call<ResponseFromFoodDetailApi> call, Response<ResponseFromFoodDetailApi> response) {
                Log.d("foodApi.getDetailRecipe.onResponse","Berhasil");
                responseFromFoodDetailApi = response.body();
                foodDetailFromApi = responseFromFoodDetailApi.getFoodDetailFromApi();
                tvDetailTitle.setText(foodDetailFromApi.getTitle());
                tvDetailTimeNeeded.setText(foodDetailFromApi.getTimeNeeded());
                tvDetailServePortion.setText(foodDetailFromApi.getTimeNeeded());
                tvDetailDifficulty.setText(foodDetailFromApi.getDificulty());

                FoodDetailAdapterData foodDetailAdapterData = new FoodDetailAdapterData();
                foodDetailAdapterData.setSummary(foodDetailFromApi.getDescription());
                foodDetailAdapterData.setItemNeeded(detailFragmentHelper.prepareForItemNeeded(foodDetailFromApi.getNeedItem()));
                foodDetailAdapterData.setIngredient(detailFragmentHelper.prepareForIngredient(foodDetailFromApi.getIngredient()));
                foodDetailAdapterData.setStep(detailFragmentHelper.prepareForStep(foodDetailFromApi.getStep()));
                foodDetailAdapterDataList.add(foodDetailAdapterData);
                rvAdapterFoodDetail.notifyDataSetChanged();

                threadWorker.execute(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            String url = foodDetailFromApi.getImageUrl();
                            InputStream inputStream = new URL(url).openStream();
                            Bitmap bm = BitmapFactory.decodeStream(inputStream);

                            // Untuk ubah UI aja
                            mainThread.post(new Runnable() {
                                @Override
                                public void run() {
                                    ivDetailFoodPicture.setImageBitmap(bm);
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
            public void onFailure(Call<ResponseFromFoodDetailApi> call, Throwable t) {
                Log.d("foodApi.getDetailRecipe.onResponse","Gagal:");
                t.printStackTrace();
            }
        });
    }

}