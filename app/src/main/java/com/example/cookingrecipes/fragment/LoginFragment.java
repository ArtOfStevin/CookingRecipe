package com.example.cookingrecipes.fragment;

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
import com.example.cookingrecipes.logic.VerifyUser;
import com.example.cookingrecipes.view_model.VMFoodBannerRepositoryBridge;
import com.example.cookingrecipes.view_model.VMUserLoginRepository;

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
    private VMUserLoginRepository vmUserLoginRepository;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.vmFoodBannerRepositoryBridge = new ViewModelProvider(requireActivity()).get(VMFoodBannerRepositoryBridge.class);
        this.vmUserLoginRepository = new ViewModelProvider(requireActivity()).get(VMUserLoginRepository.class);
        this.vmUserLoginRepository.setContext(requireContext());

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

        vmFoodBannerRepositoryBridge.clearTable();
        this.initOnClick();
    }

    public void initOnClick(){

        this.btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                vmUserLoginRepository.loginProcess(username,password);
            }
        });

    }

    private void clearFoodTable(){
        this.vmFoodBannerRepositoryBridge.clearTable();
    }
}