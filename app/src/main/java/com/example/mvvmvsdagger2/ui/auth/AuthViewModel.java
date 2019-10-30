package com.example.mvvmvsdagger2.ui.auth;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;


import com.example.mvvmvsdagger2.SessionManager;
import com.example.mvvmvsdagger2.di.model.User;
import com.example.mvvmvsdagger2.network.auth.AuthApi;


import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class AuthViewModel extends ViewModel {
    private final AuthApi authApi;
    private final CompositeDisposable compositeDisposable;

    private SessionManager sessionManager;

    @Inject
    public AuthViewModel(AuthApi authApi, CompositeDisposable compositeDisposable,SessionManager sessionManager) {
        this.authApi = authApi;
        this.compositeDisposable = compositeDisposable;
        this.sessionManager = sessionManager;
    }

    public void authenticateUser(int id) {
        Log.d("123123","attempt authenticate ..." );
        sessionManager.authenticateWithID(queryUserID(id));
    }

    private LiveData<AuthResource<User>> queryUserID(int id){
        return LiveDataReactiveStreams.fromPublisher(
                authApi.getUser(id)
                        .onErrorReturn(new Function<Throwable, User>() {
                            @Override
                            public User apply(Throwable throwable) throws Exception {
                                User errUser = new User();
                                errUser.setId(-1);
                                return errUser;
                            }
                        })
                        .map(new Function<User, AuthResource<User>>() {
                            @Override
                            public AuthResource<User> apply(User user) throws Exception {
                                if(user.getId()==-1){
                                    return AuthResource.error("Could not authenticate",null);
                                }
                                return AuthResource.authenticated(user);
                            }
                        })
                        .subscribeOn(Schedulers.io())
        );
    }

    public MediatorLiveData<AuthResource<User>> observeAuthState() {
        return sessionManager.getCacheUser();
    }
}
