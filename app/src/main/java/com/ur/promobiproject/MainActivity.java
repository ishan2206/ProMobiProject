package com.ur.promobiproject;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.ur.promobiproject.Adapters.ArticlesRecyclerAdapter;
import com.ur.promobiproject.Application.MyApp;
import com.ur.promobiproject.Events.NetworkEvent;
import com.ur.promobiproject.Modules.ArticleModule;
import com.ur.promobiproject.Receivers.NetworkReceiver;
import com.ur.promobiproject.Utils.Constants;
import com.ur.promobiproject.Utils.EndlessRecyclerOnScrollListener;
import com.ur.promobiproject.ViewModel.ArticleViewModel;
import com.ur.promobiproject.ViewModel.ArticleViewModelFactory;
import com.ur.promobiproject.local.ArticleEntity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    @Inject
    Retrofit retrofit;
    ArrayList<ArticleEntity> articles;

    @Inject @Named("activity")
    CompositeDisposable compositeDisposable;

    @Inject
    ArticleViewModelFactory articleViewModelFactory;


    @Bind(R.id.rvArticles)
    RecyclerView articlesRecyclerView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private NetworkReceiver networkReceiver;
    private ArticleViewModel articleViewModel;
    ArticlesRecyclerAdapter articlesRecyclerAdapter;
    EndlessRecyclerOnScrollListener scrollListener;
    private static boolean isNetworkConnected;
    private static String searchQuery = Constants.DEFAULT_QUERY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this); // Binding ButterKnife

        setSupportActionBar(toolbar);
        setTitle(getString(R.string.title_article_search));

        ((MyApp) getApplication()).getNetComponent().getArticleComponent(new ArticleModule(getApplicationContext())).inject(this ); // Setting Up Dagger Dependencies


        articleViewModel = ViewModelProviders.of(this, articleViewModelFactory).get(ArticleViewModel.class); // ViewModel Setup

        articles = new ArrayList<>();
        articlesRecyclerAdapter = new ArticlesRecyclerAdapter(this, articles);


      LinearLayoutManager  mLayoutManager = new LinearLayoutManager(getApplicationContext());
        articlesRecyclerView.setLayoutManager(mLayoutManager);
        articlesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        articlesRecyclerView.setAdapter(articlesRecyclerAdapter);
         scrollListener = new EndlessRecyclerOnScrollListener(mLayoutManager) {   // Lazy loading (While Scrolling).
            @Override
            public void onLoadMore(int current_page) {

                if(!isNetworkConnected) {
                    Snackbar.make((MainActivity.this).findViewById(android.R.id.content),R.string.string_no_internet_connection , Snackbar.LENGTH_LONG).show();
                    return;
                }
                    requestArticles(searchQuery, current_page);
                    Snackbar.make((MainActivity.this).findViewById(android.R.id.content), R.string.string_loading, Snackbar.LENGTH_LONG).show();
                }
        };
        articlesRecyclerView.addOnScrollListener(scrollListener);

        articleViewModel.getLocalLiveArticles(searchQuery).observe(this, new Observer<List<ArticleEntity>>() {      // Gets Called Whenever data is Updated in a Database.
            @Override
            public void onChanged(@Nullable List<ArticleEntity> articleEntities) {

                if(!isNetworkConnected && (articleEntities!=null && articleEntities.isEmpty())){
                    Snackbar.make((MainActivity.this).findViewById(android.R.id.content), R.string.string_offline_message, Snackbar.LENGTH_INDEFINITE).show();
                }

                else if(articleEntities!= null && !articleEntities.isEmpty()){


                    MainActivity.this.articles.clear();
                           MainActivity.this.articles.addAll(articleEntities);

                            articlesRecyclerAdapter.notifyDataSetChanged();
                }

                scrollListener.setLoading(false); // loading finished..
            }
        });
        networkReceiver = new NetworkReceiver();

         requestArticles(searchQuery,1); // Getting Articles for page 1 with default query.
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_search, menu);
        final MenuItem searchMenuItem = menu.findItem(R.id.action_search);

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                articleViewModel.deleteAllArticles();
                articles.clear();
                articlesRecyclerAdapter.notifyDataSetChanged();
                searchQuery = query;
                requestArticles(searchQuery, 1);

                if(query.equalsIgnoreCase("")){ // If search text is empty set it to default query.
                    searchQuery = Constants.DEFAULT_QUERY;
                }

                if( ! searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                searchView.clearFocus();
                searchMenuItem.collapseActionView();
                return true;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    public void requestArticles(String searchText, int page){

        articleViewModel.getArticlesFromService(searchText,Constants.API_KEY, Constants.SORT_NEW, page );
    }

    public void onEvent(NetworkEvent event){        // Listen to the network change event by green robot event bus
        isNetworkConnected = event.isNetworkConnected();
        if(isNetworkConnected){
            Snackbar.make((MainActivity.this).findViewById(android.R.id.content), R.string.string_online, Snackbar.LENGTH_LONG).show();
            if(articles.size()==0){
                requestArticles(searchQuery,1);
            }
        return;
        }
        Snackbar.make((MainActivity.this).findViewById(android.R.id.content), R.string.string_offline, Snackbar.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(networkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)); // Register Receiver

        EventBus.getDefault().register(this); //  Register Green Robot Event Bus.

//        compositeDisposable.add(articleViewModel.getArticles()
//                .subscribeOn(Schedulers.computation())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<List<ArticleEntity>>() {
//                    @Override
//                    public void accept(List<ArticleEntity> articles) throws Exception {
//                        scrollListener.setLoading(false);
//                        if(articles != null) {
//                            MainActivity.this.articles.clear();
//                           MainActivity.this.articles.addAll(articles);
//                            articlesRecyclerAdapter.notifyDataSetChanged();
//                        }
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        scrollListener.setLoading(false);
//                        Log.e("MainActivity", "exception getting articles");
//                    }
//                }));
    }


    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this); // Unregister Green Robot Event.
        unregisterReceiver(networkReceiver); // Unregister network receiver.
    }

    @Override
    protected void onDestroy() {

        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.clear();
        }
        super.onDestroy();
    }
}

