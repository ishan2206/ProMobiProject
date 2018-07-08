package com.ur.promobiproject.Application;

import android.app.Application;

import com.ur.promobiproject.Components.AppComponent;
import com.ur.promobiproject.Components.DaggerAppComponent;


/**
 * Created by Ishan on 08-07-2018.
 */

public class MyApp extends Application {


    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppComponent = DaggerAppComponent.builder().build();
//                .appModule(new AppModule(this))
//                .netModule(new NetModule("http://www.nytimes.com/"))
//                .articleModule(new ArticleModule(this))
//                .build();
    }

    public AppComponent getNetComponent() {
        return mAppComponent;
        //return ((MyApp)context.getApplicationContext()).mAppComponent;
    }
}
