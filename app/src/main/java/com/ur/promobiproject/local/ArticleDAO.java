package com.ur.promobiproject.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

/**
 * Created by Ishan on 07-07-2018.
 */

@Dao
public interface ArticleDAO {

    @Query("SELECT * FROM ArticleEntity")
    Flowable<List<ArticleEntity>> getArticles();

    @Query("SELECT * FROM ArticleEntity ")
    LiveData<List<ArticleEntity>> getLiveArticles();

    @Query("SELECT * FROM ArticleEntity WHERE mongoId = :news_desk ")
    Maybe<ArticleEntity> getArticleByNewsDesk(String news_desk);

    @Query("SELECT * FROM ArticleEntity LIMIT 1")
    Single<ArticleEntity> getOneArticle();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertArticle(ArticleEntity article);

    @Query("UPDATE ArticleEntity SET image = :image WHERE mongoId = :mongoId ")
    void updateImageInDb(String mongoId, byte[] image);

    @Query("DELETE FROM ArticleEntity")
    void deleteAllArticles();
}
