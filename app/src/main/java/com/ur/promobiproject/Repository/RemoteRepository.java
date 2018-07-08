package com.ur.promobiproject.Repository;

import android.arch.lifecycle.LiveData;

import com.ur.promobiproject.Model.Article;

import io.reactivex.Observable;
import retrofit2.Call;

/**
 * Created by Ishan on 07-07-2018.
 */

public interface RemoteRepository {

  Observable<Article> getArticles(String q, String apiKey, String sort, int page);

     LiveData<Article> getLiveArticles(String q, String apiKey, String sort, int page);


}
