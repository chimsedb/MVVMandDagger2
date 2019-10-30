package com.example.mvvmvsdagger2.di.main;

import androidx.lifecycle.ViewModel;

import com.example.mvvmvsdagger2.di.ViewModelKey;
import com.example.mvvmvsdagger2.ui.main.post.PostViewModel;
import com.example.mvvmvsdagger2.ui.main.profile.ProfileFragment;
import com.example.mvvmvsdagger2.ui.main.profile.ProfileViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class MainViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel.class)
    public abstract ViewModel bindProfileViewModel(ProfileViewModel profileViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(PostViewModel.class)
    public abstract ViewModel bindPostViewModel(PostViewModel postViewModel);
}
