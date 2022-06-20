package com.example.cookingrecipes.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.cookingrecipes.R;
import com.example.cookingrecipes.activity.MainActivity;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            private boolean isValid = true;

            @Override
            public void onClick(View v) {
                if(isValid) {
                    changeToHomeActivity();
                }
            }
        });

    }

    public void changeToHomeActivity(){
        Intent intent = new Intent(this.requireContext(), MainActivity.class);
        intent.putExtra("loginUser", this.etUsername.getText().toString());
        startActivity(intent);
    }
}