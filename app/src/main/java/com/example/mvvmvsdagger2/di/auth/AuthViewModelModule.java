package com.example.mvvmvsdagger2.di.auth;

import androidx.lifecycle.ViewModel;

import com.example.mvvmvsdagger2.ui.auth.AuthViewModel;
import com.example.mvvmvsdagger2.di.ViewModelKey;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class AuthViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel.class)
    public abstract ViewModel bindAuthViewModel(AuthViewModel viewModel);

//    if mutil module
//    @Binds
//    @IntoMap
//    @ViewModelKey(MainViewModel.class)
//    public abstract ViewModel bindMainViewModel(MainViewModel viewModel);
}
