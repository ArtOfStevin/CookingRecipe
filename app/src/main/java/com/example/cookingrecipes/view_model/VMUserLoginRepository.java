package com.example.cookingrecipes.view_model;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.cookingrecipes.activity.MainActivity;
import com.example.cookingrecipes.logic.SessionManagementUtil;
import com.example.cookingrecipes.logic.SharedPreferenceManager;
import com.example.cookingrecipes.model.ResponseFromUserApi;
import com.example.cookingrecipes.model.UserFromApi;
import com.example.cookingrecipes.retrofit.LoginApi;
import com.example.cookingrecipes.retrofit.RetrofitInstance;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VMUserLoginRepository extends AndroidViewModel {

    private RetrofitInstance retrofitInstance;
    private LoginApi loginApi;
    private static final ExecutorService threadWorker = Executors.newFixedThreadPool(1);
    private Context context;
    private SharedPreferenceManager sharedPreferenceManager;

    public VMUserLoginRepository(@NonNull Application application) {
        super(application);
        this.retrofitInstance = new RetrofitInstance();
        this.loginApi = retrofitInstance.getLoginApi();
    }

    public void setContext(Context context){
        this.context = context;
    }

    public void loginProcess(String username, String password){

        this.loginApi.getUserDetail(username, password).enqueue(new Callback<ResponseFromUserApi>() {
            @Override
            public void onResponse(Call<ResponseFromUserApi> call, Response<ResponseFromUserApi> response) {
                ResponseFromUserApi responseFromUserApi = response.body();
                if(responseFromUserApi != null) {
                    Log.d("loginApi.getUserDetail.onResponse","Berhasil");
                    UserFromApi userFromApi = responseFromUserApi.getData();
                    changeToHomeActivity(userFromApi.getId(), userFromApi.getUsername(), userFromApi.getFullName(), userFromApi.getEmail(), userFromApi.getAvatar());
                }
                else{
                    Log.d("loginApi.getUserDetail.onResponse", "onResponse: Login with Invalid ID");
                    postToastInvalidLogin();
                }
            }

            @Override
            public void onFailure(Call<ResponseFromUserApi> call, Throwable t) {
                Log.d("loginApi.getUserDetail.onFailure","Koneksi Gagal");
            }
        });

    }

    public void changeToHomeActivity(String id, String username, String fullname, String email, String avatar){
        Intent intent = new Intent(this.context, MainActivity.class);

        sharedPreferenceManager = new SharedPreferenceManager(context);
        sharedPreferenceManager.getEditor().putString("login_id", id);
        sharedPreferenceManager.getEditor().putString("login_username", username);
        sharedPreferenceManager.getEditor().putString("login_fullname", fullname);
        sharedPreferenceManager.getEditor().putString("login_email", email);
        sharedPreferenceManager.getEditor().putString("login_avatar", avatar);
        sharedPreferenceManager.getEditor().apply();

        SessionManagementUtil.getInstance().startUserSession(this.context, 600); //5 menit
        this.context.startActivity(intent);
    }

    public void postToastInvalidLogin(){
        Handler mainThread = new Handler(Looper.getMainLooper());
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context,"Invalid User ID",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
