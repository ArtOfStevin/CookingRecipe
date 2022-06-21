package com.example.cookingrecipes.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cookingrecipes.R;
import com.example.cookingrecipes.activity.MainActivity;
import com.example.cookingrecipes.logic.SessionManagementUtil;
import com.example.cookingrecipes.logic.VerifyUser;
import com.example.cookingrecipes.view_model.VMFoodBannerRepositoryBridge;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    private EditText etUsername;
    private EditText etPassword;
    private Button btnSignUp;
    private Button btnLogin;
    private ImageView ivLoginPicture;
    private TextView tvInvalidUser;

    private VerifyUser verifyUser;
    private VMFoodBannerRepositoryBridge vmFoodBannerRepositoryBridge;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.vmFoodBannerRepositoryBridge = new ViewModelProvider(requireActivity()).get(VMFoodBannerRepositoryBridge.class);
        this.verifyUser = new VerifyUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate/Draw the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_login, container, false);

        // Init UI
        this.etUsername = fragmentView.findViewById(R.id.etUsername);
        this.etPassword = fragmentView.findViewById(R.id.etPassword);
        this.btnSignUp = fragmentView.findViewById(R.id.btnSignUp);
        this.btnLogin = fragmentView.findViewById(R.id.btnLogin);
        this.ivLoginPicture = fragmentView.findViewById(R.id.ivLoginPicture);
        this.tvInvalidUser = fragmentView.findViewById(R.id.tvInvalidUser);

        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.ivLoginPicture.setImageResource(R.drawable.ribbon);
        this.initOnClick();
    }

    public void initOnClick(){

        this.btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(verifyUser.isValid(etUsername.getText().toString(), etPassword.getText().toString())) {
                    SessionManagementUtil.getInstance().startUserSession(requireContext(), 5);
                    clearFoodTable();
                    changeToHomeActivity();
                }
                else{
                    tvInvalidUser.setText("Invalid User ID");
                    tvInvalidUser.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    public void changeToHomeActivity(){
        Intent intent = new Intent(this.requireContext(), MainActivity.class);
        intent.putExtra("loginUser", this.etUsername.getText().toString());
        startActivity(intent);
    }

    private void clearFoodTable(){
        this.vmFoodBannerRepositoryBridge.clearTable();
    }
}