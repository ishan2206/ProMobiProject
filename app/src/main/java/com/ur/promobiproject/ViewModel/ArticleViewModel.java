package com.ur.promobiproject.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;
import android.text.format.Time;
import android.util.Base64;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ur.promobiproject.Model.Article;
import com.ur.promobiproject.R;
import com.ur.promobiproject.Repository.LocalRepository;
import com.ur.promobiproject.Repository.RemoteRepository;
import com.ur.promobiproject.Utils.Constants;
import com.ur.promobiproject.local.ArticleEntity;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ishan on 07-07-2018.
 */

public class ArticleViewModel extends ViewModel {

    private LocalRepository localRepository;

    private RemoteRepository remoteRepository;

    private CompositeDisposable compositeDisposable;

    private LiveData<Article> liveArticle;

    public ArticleViewModel(LocalRepository localRepository, RemoteRepository remoteRepository, CompositeDisposable compositeDisposable){
        this.localRepository = localRepository;
        this.remoteRepository = remoteRepository;
        this.compositeDisposable = compositeDisposable;
    }


    public Flowable<List<ArticleEntity>> getArticles(){
        return localRepository.getArticles();
    }

    public Maybe<ArticleEntity> getArticleByNewsDesk(String news_desk){
        return localRepository.getArticleByNewsDesk(news_desk);
    }


    public Single<ArticleEntity> getOneArticle(){
        return localRepository.getOneArticle();
    }

    public void insertCoupon(ArticleEntity article){
        localRepository.insertArticle(article);
    }

    public void deleteAllArticles(){
        localRepository.deleteAllArticles();
    }

    public void getArticlesFromService(final String q, final String apiKey, final String sort, final int page){

        compositeDisposable.add(io.reactivex.Observable.just(1)     // TODO: Need to Refactor...
                .subscribeOn(Schedulers.computation())
                .flatMap(new Function<Integer, ObservableSource<Article>>() {
                    @Override
                    public ObservableSource<Article> apply(Integer integer) throws Exception {
                        return remoteRepository.getArticles(q, apiKey, sort, page);
                    }
                }).subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Article>() {
                    @Override
                    public void accept(Article article) throws Exception {
                        for(Article.Docs docs : article.getResponse().getDocs()){

                            String imageUrl ="";
                            //database update
                            for(int i=0; i< docs.getMultimedia().size(); i++){
                                Article.Multimedia multimedia = docs.getMultimedia().get(i);
                                if(multimedia.getSubtype().equalsIgnoreCase("xlarge")){
                                     imageUrl = Constants.BASE_URL + multimedia.getUrl();
                                     break;
                            }
                            }

                            ArticleEntity articleEntity = new ArticleEntity();
                            articleEntity.setHeadline(docs.getHeadline().getMain());
                           articleEntity.setDate(DateFormat.getDateTimeInstance().format(new Date()));
                            //articleEntity.setDate(docs.getPub_date());
                            articleEntity.setImageUrl(imageUrl);
                            articleEntity.setSearchQuery(q);
                            articleEntity.setWebUrl(docs.getWeb_url());
                            articleEntity.setMongoId(docs.get_id());
                            localRepository.insertArticle(articleEntity);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("MainActivity", "exception getting articles" + throwable);
                    }
                }));

    }


public LiveData<Article> getLiveArticles(String q, String apiKey, String sort, int page){

    if(liveArticle == null){
        liveArticle = remoteRepository.getLiveArticles(q, apiKey, sort, page);
    }
    return liveArticle;

}

    public LiveData<List<ArticleEntity>> getLocalLiveArticles(String searchQuery){

       return localRepository.getLocalLiveArticles(searchQuery);
    }

    @Override
    public void onCleared(){
        //prevents memory leaks by disposing pending observable objects
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.clear();
        }
    }

}
