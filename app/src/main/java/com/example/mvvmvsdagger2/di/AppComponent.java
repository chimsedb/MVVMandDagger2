package com.example.mvvmvsdagger2.di;

import android.app.Application;

import com.example.mvvmvsdagger2.BaseApp;
import com.example.mvvmvsdagger2.SessionManager;
import com.example.mvvmvsdagger2.di.auth.AuthViewModelModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class
        , ActivityBuilderModule.class
        , AppModule.class
        , ViewModelFactoryModule.class
})
public interface AppComponent extends AndroidInjector<BaseApp> {

//    SessionManager sessionManager();

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        @BindsInstance
        Builder count(int count);
        AppComponent build();
    }
}
