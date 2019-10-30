package com.example.mvvmvsdagger2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.example.mvvmvsdagger2.ui.auth.AuthActivity;
import com.example.mvvmvsdagger2.ui.auth.AuthResource;
import com.example.mvvmvsdagger2.di.model.User;
import com.example.mvvmvsdagger2.ui.main.MainActivity;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity extends DaggerAppCompatActivity {
    @Inject
    public SessionManager sessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("123123","123123");
        subcribeObservers();
    }

    private void subcribeObservers(){
        sessionManager.getCacheUser().observe(this, new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> userAuthResource) {
                if(userAuthResource!=null){
                    switch (userAuthResource.status){
                        case LOADING:{
                            break;
                        }
                        case AUTHENTICATED:{
                            break;
                        }
                        case ERROR:{
                            Log.d("123123",userAuthResource.message);
                            break;
                        }
                        case NOT_AUTHENTICATED:{
                            naviloginScreen();
                            break;
                        }
                    }
                }
            }
        });
    }


    private void naviloginScreen(){
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
        finish();
    }
}
