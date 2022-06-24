package com.example.cookingrecipes.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cookingrecipes.R;
import com.example.cookingrecipes.activity.LoginActivity;
import com.example.cookingrecipes.logic.SessionManagementUtil;
import com.example.cookingrecipes.logic.SharedPreferenceManager;
import com.example.cookingrecipes.view_model.VMFoodBannerRepositoryBridge;

import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserDetailFragment#} factory method to
 * create an instance of this fragment.
 */
public class UserDetailFragment extends Fragment {

    private TextView tvUsernameCenter;
    private TextView tvUsername;
    private TextView tvEmail;

    private Button btnLogout;
    private ImageView ivPhotoProfile;
    private VMFoodBannerRepositoryBridge vmFoodBannerRepositoryBridge;

    private static final ExecutorService threadWorker = Executors.newFixedThreadPool(1);
    private Handler mainThread;

    private SharedPreferenceManager sharedPreferenceManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.vmFoodBannerRepositoryBridge = new ViewModelProvider(requireActivity()).get(VMFoodBannerRepositoryBridge.class);
        this.mainThread = new Handler(Looper.getMainLooper());
        this.sharedPreferenceManager = new SharedPreferenceManager(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_user_detail, container, false);
        this.tvUsernameCenter = fragmentView.findViewById(R.id.tvUserDetailUsernameCenter);
        this.tvUsername = fragmentView.findViewById(R.id.tvUserDetailUsername);
        this.tvEmail = fragmentView.findViewById(R.id.tvUserDetailEmail);
        this.btnLogout = fragmentView.findViewById(R.id.btnUserDetailLogOut);
        this.ivPhotoProfile = fragmentView.findViewById(R.id.ivUserDetailPhoto);

        setBtnLogout();

        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String loginUserName = this.sharedPreferenceManager.readString("login_username");
        String loginFullName = this.sharedPreferenceManager.readString("login_fullname");
        String loginEmail = this.sharedPreferenceManager.readString("login_email");
        String loginAvatar = this.sharedPreferenceManager.readString("login_avatar");

        this.tvUsernameCenter.setText(loginFullName);
        this.tvUsername.setText(loginUserName);
        this.tvEmail.setText(loginEmail);
        this.ivPhotoProfile.setImageResource(R.drawable.ribbon);

        // Thread Worker untuk narik gambar (Stream) dari internetnya
        threadWorker.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    String url = loginAvatar;
                    url = "https://images.unsplash.com/photo-1633332755192-727a05c4013d?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1480&q=80";
                    InputStream inputStream = new URL(url).openStream();
                    Bitmap bm = BitmapFactory.decodeStream(inputStream);

                    // Untuk ubah UI aja
                    mainThread.post(new Runnable() {
                        @Override
                        public void run() {
                            ivPhotoProfile.setImageBitmap(bm);
                        }
                    });
                }
                catch(Exception e){
                    Log.wtf("IMAGELOG", "onBindViewHolder: " + e);
                }
            }
        });
    }

    private void setBtnLogout(){
        this.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManagementUtil.getInstance().clearStoredData(requireContext());
                vmFoodBannerRepositoryBridge.clearTable();
                toLoginScreen();
            }
        });
    }

    private void toLoginScreen(){
        Intent intent = new Intent(this.requireContext(), LoginActivity.class);
        startActivity(intent);
    }

}