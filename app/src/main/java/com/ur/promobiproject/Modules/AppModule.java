package com.ur.promobiproject.Modules;

import android.app.Application;

import com.ur.promobiproject.Interface.ApiInterface;
import com.ur.promobiproject.Utils.Constants;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

//    Application mApplication;
//
//    public AppModule(Application application) {
//        mApplication = application;
//    }
//
//    @Provides
//    @Singleton
//    Application providesApplication() {
//        return mApplication;
//    }



    @Singleton
    @Provides
    Executor getExecutor(){
        return  Executors.newFixedThreadPool(2);
    }

    @Singleton
    @Provides
    Retrofit getRemoteClient(){
        return new Retrofit.Builder()
                .baseUrl(Constants.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
    @Singleton
    @Provides
    ApiInterface getCouponClient(Retrofit retrofit){
        return retrofit.create(ApiInterface.class);
    }
}