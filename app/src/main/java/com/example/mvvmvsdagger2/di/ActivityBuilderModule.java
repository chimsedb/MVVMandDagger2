package com.example.mvvmvsdagger2.di;

import com.example.mvvmvsdagger2.di.main.MainFragmentBuilderModule;
import com.example.mvvmvsdagger2.di.main.MainModule;
import com.example.mvvmvsdagger2.di.main.MainViewModelModule;
import com.example.mvvmvsdagger2.network.main.MainApi;
import com.example.mvvmvsdagger2.ui.auth.AuthActivity;
import com.example.mvvmvsdagger2.di.auth.AuthModule;
import com.example.mvvmvsdagger2.di.auth.AuthViewModelModule;
import com.example.mvvmvsdagger2.test.TestActivity;
import com.example.mvvmvsdagger2.ui.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilderModule {
    @ContributesAndroidInjector
    abstract TestActivity contributeTestActivity();

    @ContributesAndroidInjector(
            modules = {AuthViewModelModule.class, AuthModule.class}
    )
    abstract AuthActivity contributeAuthActivity();

    @ContributesAndroidInjector(
            modules = {MainFragmentBuilderModule.class, MainViewModelModule.class, MainModule.class}
    )
    abstract MainActivity contributeMainActivity();

}
