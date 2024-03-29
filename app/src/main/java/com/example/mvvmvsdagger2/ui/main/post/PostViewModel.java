package com.example.mvvmvsdagger2.ui.main.post;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.mvvmvsdagger2.SessionManager;
import com.example.mvvmvsdagger2.di.model.Post;
import com.example.mvvmvsdagger2.network.main.MainApi;
import com.example.mvvmvsdagger2.ui.main.Resource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class PostViewModel extends ViewModel {

    private MainApi mainApi;
    private SessionManager sessionManager;

    private MediatorLiveData<Resource<List<Post>>> posts;

    @Inject
    public PostViewModel(MainApi mainApi, SessionManager sessionManager) {
        this.mainApi = mainApi;
        this.sessionManager = sessionManager;
        Log.d("PostViewModel","PostViewModel");
    }

    public MediatorLiveData<Resource<List<Post>>> observePosts(){
        if(posts == null){
            posts = new MediatorLiveData();
            posts.setValue(Resource.loading((List<Post>)null));

            final LiveData<Resource<List<Post>>> src = LiveDataReactiveStreams.fromPublisher(
                    mainApi.getPostFromUser(sessionManager.getCacheUser().getValue().data.getId())
                            .onErrorReturn(new Function<Throwable, List<Post>>() {
                                @Override
                                public List<Post> apply(Throwable throwable) throws Exception {
                                    Post post = new Post();
                                    post.setId(-1);
                                    ArrayList<Post> posts = new ArrayList<>();
                                    posts.add(post);
                                    return posts;
                                }
                            })
                            .map(new Function<List<Post>, Resource<List<Post>>>() {
                                @Override
                                public Resource<List<Post>> apply(List<Post> posts) throws Exception {
                                    if(posts.size()>0){
                                        if(posts.get(0).getId()==-1){
                                            return Resource.error("Something wont error",null);
                                        }
                                    }
                                    return Resource.success(posts);
                                }
                            })
                            .subscribeOn(Schedulers.io())
            );
            posts.addSource(src, new Observer<Resource<List<Post>>>() {
                @Override
                public void onChanged(Resource<List<Post>> listResource) {
                    posts.setValue(listResource);
                    posts.removeSource(src);
                }
            });

        }
        return posts;
    }
}
