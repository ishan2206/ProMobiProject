package com.ur.promobiproject.Repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.ur.promobiproject.Model.Article;
import com.ur.promobiproject.local.ArticleDAO;
import com.ur.promobiproject.local.ArticleEntity;

import java.util.List;
import java.util.concurrent.Executor;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

/**
 * Created by Ishan on 07-07-2018.
 */

public class LocalRepositoryImpl implements LocalRepository {

    private ArticleDAO articleDAO;
    private Executor executor;

    public LocalRepositoryImpl(ArticleDAO articleDAO, Executor executor){

        this.articleDAO = articleDAO;
        this.executor = executor;
    }

    @Override
    public Flowable<List<ArticleEntity>> getArticles() {
        return articleDAO.getArticles();
    }

    @Override
    public Maybe<ArticleEntity> getArticleByNewsDesk(String news_desk) {
        return articleDAO.getArticleByNewsDesk(news_desk);
    }

    @Override
    public Single<ArticleEntity> getOneArticle() {
        return articleDAO.getOneArticle();
    }

    @Override
    public void insertArticle(final ArticleEntity article) {

        executor.execute(new Runnable() {
            @Override
            public void run() {
                articleDAO.insertArticle(article);
            }
        });
    }

    @Override
    public void deleteAllArticles() {

        executor.execute(new Runnable() {
            @Override
            public void run() {
                articleDAO.deleteAllArticles();
            }
        });
    }

    @Override
    public void updateImageInDb(final String mongoId, final byte[] image) {

        executor.execute(new Runnable() {
            @Override
            public void run() {
               articleDAO.updateImageInDb(mongoId, image);
            }
        });
    }

    @Override
    public LiveData<List<ArticleEntity>> getLocalLiveArticles(String searchQuery) {

        return articleDAO.getLiveArticles();
    }
}
