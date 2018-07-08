package com.ur.promobiproject.Utils;

/**
 * Created by Ishan on 07-07-2018.
 */

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {
    public static String TAG = EndlessRecyclerOnScrollListener.class.getSimpleName();

    private int previousTotal = 0;
    private boolean loading = false;

    private int current_page = 1;

    private LinearLayoutManager mLinearLayoutManager;

    public EndlessRecyclerOnScrollListener(LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        if(dy < 0) {
            return;
        }
        // check for scroll down only
        int visibleItemCount = recyclerView.getChildCount();
        int totalItemCount = mLinearLayoutManager.getItemCount();
        int firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

        // to make sure only one onLoadMore is triggered
        synchronized (this) {
            int visibleThreshold = 2;
            if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
                // End has been reached, Do something
                current_page++;
                onLoadMore(current_page);
                loading = true;
            }
        }
//        totalItemCount = mLinearLayoutManager.getItemCount();
//        int lastVisibleItem = mLinearLayoutManager
//                .findLastVisibleItemPosition();
//        if (!loading
//                && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
//            //End of the items
//            onLoadMore(current_page);
//            loading = true;
//
//        }
    }



    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    public abstract void onLoadMore(int current_page);

}