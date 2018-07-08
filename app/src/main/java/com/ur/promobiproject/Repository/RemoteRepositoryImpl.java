package com.ur.promobiproject.Repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.ur.promobiproject.Interface.ApiInterface;
import com.ur.promobiproject.Model.Article;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ishan on 07-07-2018.
 */

public class RemoteRepositoryImpl implements RemoteRepository {

    private ApiInterface apiInterface;

    public RemoteRepositoryImpl(ApiInterface apiInterface){

        this.apiInterface = apiInterface;
    }

    @Override
    public Observable<Article> getArticles(String q, String apiKey, String sort, int page) {
        return apiInterface.getArticles(q, apiKey, sort, page);
    }

    @Override
    public LiveData<Article> getLiveArticles(String q, String apiKey, String sort, int page) {

        final MutableLiveData<Article> liveArticles = new MutableLiveData<>();

        apiInterface.getLiveArticles(q, apiKey, sort, page).enqueue(new Callback<Article>() {
            @Override
            public void onResponse(Call<Article> call, Response<Article> response) {
                Article article = response.body();
                liveArticles.setValue(article);
            }

            @Override
            public void onFailure(Call<Article> call, Throwable t) {
                Log.e("", "Error getting top articles: "+ t.getMessage() );
            }
        });
        return liveArticles;
    }

}
