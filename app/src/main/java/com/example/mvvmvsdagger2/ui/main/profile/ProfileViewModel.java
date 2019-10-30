package com.example.mvvmvsdagger2.ui.main.profile;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.mvvmvsdagger2.SessionManager;
import com.example.mvvmvsdagger2.di.model.User;
import com.example.mvvmvsdagger2.ui.auth.AuthResource;

import javax.inject.Inject;

public class ProfileViewModel extends ViewModel {

    private SessionManager sessionManager;

    @Inject
    public ProfileViewModel(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public LiveData<AuthResource<User>> getAuthenticateUser(){
        return sessionManager.getCacheUser();
    }
}
