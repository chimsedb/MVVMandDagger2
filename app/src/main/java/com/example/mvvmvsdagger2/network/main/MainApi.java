package com.example.mvvmvsdagger2.network.main;

import com.example.mvvmvsdagger2.di.model.Post;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface MainApi {

    @GET("posts")
    Flowable<List<Post>> getPostFromUser(@Query("userId") int id);

}
