package com.ur.promobiproject.Interface;

import android.arch.lifecycle.LiveData;

import com.ur.promobiproject.Model.Article;


import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Ishan on 08-07-2018.
 */

public interface ApiInterface {

    @GET("svc/search/v2/articlesearch.json")
    Observable<Article> getArticles(@Query("q") String q, @Query("api-key") String apiKey, @Query("sort") String sort, @Query("page") int page);

    @GET("http://api.nytimes.com/svc/search/v2/articlesearch.json")
    Call<Article> getLiveArticles(@Query("q") String q, @Query("api-key") String apiKey, @Query("sort") String sort, @Query("page") int page);
}
