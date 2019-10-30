package com.example.mvvmvsdagger2;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.example.mvvmvsdagger2.ui.auth.AuthResource;
import com.example.mvvmvsdagger2.di.model.User;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SessionManager {
    private MediatorLiveData<AuthResource<User>> cacheUser = new MediatorLiveData<>();

    @Inject
    public SessionManager() {
    }

    public void authenticateWithID(final LiveData<AuthResource<User>> src){
        if(cacheUser!=null){
            cacheUser.setValue(AuthResource.loading((User)null));
            cacheUser.addSource(src, new Observer<AuthResource<User>>() {
                @Override
                public void onChanged(AuthResource<User> userAuthResource) {
                    cacheUser.setValue(userAuthResource);
                    cacheUser.removeSource(src);
                }
            });
        }
    }

    public void logOut(){
        Log.d("logOut","logOut : ...");
        cacheUser.setValue(AuthResource.<User>logout());
    }

    public MediatorLiveData<AuthResource<User>> getCacheUser() {
        return cacheUser;
    }
}
