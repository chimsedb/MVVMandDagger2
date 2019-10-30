package com.example.mvvmvsdagger2.di.main;

import com.example.mvvmvsdagger2.network.main.MainApi;
import com.example.mvvmvsdagger2.ui.main.post.PostRecyclerAdapter;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MainModule {
    @Provides
    public static PostRecyclerAdapter provideRecyclerAdapter(){
        return new PostRecyclerAdapter();
    }

    @Provides
    public static MainApi provideRetrofit(Retrofit retrofit){
        return retrofit.create(MainApi.class);
    }
}
