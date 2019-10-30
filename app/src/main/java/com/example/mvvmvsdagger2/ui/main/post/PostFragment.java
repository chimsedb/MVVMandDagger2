package com.example.mvvmvsdagger2.ui.main.post;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvmvsdagger2.R;
import com.example.mvvmvsdagger2.di.ViewModelFactoryModule;
import com.example.mvvmvsdagger2.di.model.Post;
import com.example.mvvmvsdagger2.ui.main.Resource;
import com.example.mvvmvsdagger2.util.VerticalSpaceItemDecoration;
import com.example.mvvmvsdagger2.viewmodels.ViewModelProviderFactory;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class PostFragment extends DaggerFragment {

    private PostViewModel viewModel;
    private RecyclerView recyclerView;

    @Inject
    PostRecyclerAdapter adapter;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Toast.makeText(getContext(), "PostFragment", Toast.LENGTH_SHORT).show();
        viewModel = ViewModelProviders.of(this,providerFactory).get(PostViewModel.class);
        return inflater.inflate(R.layout.fragment_post,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_view);
        subcribeObservers();
        initRecyclerView();
    }

    private void subcribeObservers(){
        viewModel.observePosts().removeSource(getViewLifecycleOwnerLiveData());
        viewModel.observePosts().observe(getViewLifecycleOwner(), new Observer<Resource<List<Post>>>() {
            @Override
            public void onChanged(Resource<List<Post>> listResource) {
                if (listResource!=null){
                    switch (listResource.status){
                        case LOADING:
                            Log.d("onChanged","onChanged : LOADING");
                            break;
                        case SUCCESS:
                            Log.d("onChanged","onChanged : SUCCESS");
                            adapter.setPosts(listResource.data);
                            break;
                        case ERROR:
                            Log.d("onChanged","onChanged : ERROR");
                            break;
                    }
                }
            }
        });
    }

    private void initRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        VerticalSpaceItemDecoration itemDecoration = new VerticalSpaceItemDecoration(15);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(adapter);
    }
}
