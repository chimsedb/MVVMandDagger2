package com.example.mvvmvsdagger2.di.main;

import com.example.mvvmvsdagger2.ui.main.post.PostFragment;
import com.example.mvvmvsdagger2.ui.main.profile.ProfileFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainFragmentBuilderModule {
    @ContributesAndroidInjector
    abstract ProfileFragment contributeProfileFragment();

    @ContributesAndroidInjector
    abstract PostFragment contributePostFragment();
}
