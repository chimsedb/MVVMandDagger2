package com.example.mvvmvsdagger2.network.auth;

import com.example.mvvmvsdagger2.di.model.User;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface AuthApi {
    @GET("/users/{id}")
    Flowable<User> getUser(@Path("id") int id);
}
