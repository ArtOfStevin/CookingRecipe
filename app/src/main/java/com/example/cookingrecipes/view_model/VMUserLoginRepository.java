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
    public static final String LOGIN_PREFERENCE = "com.example.cookingrecipes.LOGIN_PREFERENCE";

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
//        intent.putExtra("login_id", id);
//        intent.putExtra("login_username", username);
//        intent.putExtra("login_fullname", fullname);
//        intent.putExtra("login_email", email);
//        intent.putExtra("login_avatar", avatar);

        SharedPreferences sharedPreferences = context.getSharedPreferences(LOGIN_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("login_id", id);
        editor.putString("login_username", username);
        editor.putString("login_fullname", fullname);
        editor.putString("login_email", email);
        editor.putString("login_avatar", avatar);
        editor.apply();

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
