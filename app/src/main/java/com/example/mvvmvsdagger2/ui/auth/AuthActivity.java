package com.example.mvvmvsdagger2.ui.auth;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.RequestManager;
import com.example.mvvmvsdagger2.R;
import com.example.mvvmvsdagger2.di.model.User;
import com.example.mvvmvsdagger2.ui.main.MainActivity;
import com.example.mvvmvsdagger2.viewmodels.ViewModelProviderFactory;


import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class AuthActivity extends DaggerAppCompatActivity {
    private AuthViewModel viewModel;
    private Button login;
    private EditText etID;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    Drawable logo;

    @Inject
    RequestManager requestManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        login = findViewById(R.id.submit);
        etID = findViewById(R.id.user_id_input);
        viewModel = ViewModelProviders.of(this,providerFactory).get(AuthViewModel.class);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setLogo();

        subcribeObservers();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
    }

    private void attemptLogin() {
        if(TextUtils.isEmpty(etID.getText().toString())){
            return;
        }

        viewModel.authenticateUser(Integer.parseInt(etID.getText().toString()));
    }

    public void setLogo(){
        requestManager
                .load(logo)
                .into((ImageView) findViewById(R.id.login_logo));
    }

    private void subcribeObservers(){
        viewModel.observeAuthState().observe(this, new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> userAuthResource) {
                if(userAuthResource!=null){
                    switch (userAuthResource.status){
                        case LOADING:{
                            break;
                        }
                        case AUTHENTICATED:{
                            Log.d("123123",userAuthResource.data.getEmail());
                            loginSuccessful();
                            break;
                        }
                        case ERROR:{
                            Log.d("123123",userAuthResource.message);
                            break;
                        }
                        case NOT_AUTHENTICATED:{
//                            naviloginScreen();
                            break;
                        }
                    }
                }
            }
        });
    }

    private void loginSuccessful(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}
