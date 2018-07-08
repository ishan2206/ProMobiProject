package com.ur.promobiproject.Repository;

import android.arch.lifecycle.LiveData;

import com.ur.promobiproject.Model.Article;
import com.ur.promobiproject.local.ArticleEntity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

/**
 * Created by Ishan on 07-07-2018.
 */


public interface LocalRepository {
     Flowable<List<ArticleEntity>> getArticles();
     Maybe<ArticleEntity> getArticleByNewsDesk(String news_desk);
     Single<ArticleEntity> getOneArticle();
     void insertArticle(ArticleEntity article);
     void deleteAllArticles();
     void updateImageInDb(String mongoId, byte[] image);
     LiveData<List<ArticleEntity>> getLocalLiveArticles(String searchquery);
}
