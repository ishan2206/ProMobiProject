package com.ur.promobiproject.Modules;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.ur.promobiproject.Interface.ApiInterface;
import com.ur.promobiproject.Repository.LocalRepository;
import com.ur.promobiproject.Repository.LocalRepositoryImpl;
import com.ur.promobiproject.Repository.RemoteRepository;
import com.ur.promobiproject.Repository.RemoteRepositoryImpl;
import com.ur.promobiproject.local.ArticleDAO;
import com.ur.promobiproject.local.ArticleDatabase;

import java.util.concurrent.Executor;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Ishan on 08-07-2018.
 */

@Module
public class ArticleModule {

    private Context context;

    public ArticleModule(Context ctx) {
        context = ctx;
    }

    @ArticleScope
    @Provides
    ArticleDAO getArticleDAO(ArticleDatabase articleDatabase) {
        return articleDatabase.articleDAO();
    }

    @ArticleScope
    @Provides
    ArticleDatabase getArticleDatabase() {
        return Room.databaseBuilder(context.getApplicationContext(),
                ArticleDatabase.class, "articles.db")
                .build();
    }

    @ArticleScope
//
    @Provides
    LocalRepository getLocalRepo(ArticleDAO articleDAO, Executor exec) {
        return new LocalRepositoryImpl(articleDAO, exec);
    }

    @ArticleScope
    @Provides
    @Named("activity")
    CompositeDisposable getCompositeDisposable() {
        return new CompositeDisposable();
    }

    @ArticleScope
    @Provides
    @Named("vm")
    CompositeDisposable getVMCompositeDisposable() {
        return new CompositeDisposable();
    }

    @ArticleScope
    @Provides
    RemoteRepository getRemoteRepo(ApiInterface apiClient) {
        return new RemoteRepositoryImpl(apiClient);
    }
}
