package com.ur.promobiproject.Components;


import com.ur.promobiproject.Modules.AppModule;
import com.ur.promobiproject.Modules.ArticleModule;

import javax.inject.Singleton;

import dagger.Component;


//@Component(modules={AppModule.class, NetModule.class, ArticleModule.class})
@Singleton
@Component(modules={AppModule.class})
public interface AppComponent {

    ArticleComponent getArticleComponent(ArticleModule articleModule);

}