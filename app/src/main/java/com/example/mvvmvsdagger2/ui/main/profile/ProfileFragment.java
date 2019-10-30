package com.example.mvvmvsdagger2.ui.main.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.mvvmvsdagger2.R;
import com.example.mvvmvsdagger2.di.model.User;
import com.example.mvvmvsdagger2.ui.auth.AuthResource;
import com.example.mvvmvsdagger2.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class ProfileFragment extends DaggerFragment {

    private ProfileViewModel viewModel;
    private TextView email,username,website;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Toast.makeText(getContext(), "ProfileFragment", Toast.LENGTH_LONG).show();
        return inflater.inflate(R.layout.fragment_profile,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        username = view.findViewById(R.id.username);
        email = view.findViewById(R.id.email);
        website = view.findViewById(R.id.website);
        viewModel = ViewModelProviders.of(this,providerFactory).get(ProfileViewModel.class);
        subcribeObservers();
    }

    private void subcribeObservers(){
        viewModel.getAuthenticateUser().removeObservers(getViewLifecycleOwner());
        viewModel.getAuthenticateUser().observe(getViewLifecycleOwner(), new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> userAuthResource) {
                if(userAuthResource!=null){
                    switch (userAuthResource.status){
                        case AUTHENTICATED:
                            setUserDetail(userAuthResource.data);
                            break;
                        case ERROR:
                            setErrorDetail(userAuthResource.message);
                            break;
                    }
                }
            }
        });
    }

    private void setErrorDetail(String message) {
        email.setText(message);
        username.setText(message);
        website.setText(message);
    }

    private void setUserDetail(User data) {
        email.setText(data.getEmail());
        username.setText(data.getUsername());
        website.setText(data.getWebsite());
    }


}
