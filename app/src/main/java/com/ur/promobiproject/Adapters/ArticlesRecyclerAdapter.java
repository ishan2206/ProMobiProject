package com.ur.promobiproject.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import com.ur.promobiproject.Activities.ArticleActivity;
import com.ur.promobiproject.Model.Article;
import com.ur.promobiproject.R;
import com.ur.promobiproject.local.ArticleEntity;

import java.util.ArrayList;


import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ishan on 08-07-2018.
 */

public class ArticlesRecyclerAdapter extends RecyclerView.Adapter<ArticlesRecyclerAdapter.ArticleViewHolder> {

    private ArrayList<ArticleEntity> articlesList;
    private Context context;

    public ArticlesRecyclerAdapter(Context context, ArrayList<ArticleEntity> articlesList){
        this.context = context;
        this.articlesList = articlesList;
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false); // Should be false
        return new ArticleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {

        ArticleEntity article = articlesList.get(position);


        holder.tvArticle.setText(article.getHeadline());
        holder.ivArticle.setImageResource(0);

        Glide.with(context).load(article.getImageUrl())     //TODO: Save Image in local db as well.
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.nytimes)
                .into(holder.ivArticle);

    }

    @Override
    public int getItemCount() {
        return articlesList.size();
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.card_image)
        ImageView ivArticle;
        @Bind(R.id.card_text)
        TextView tvArticle;
        public ArticleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {

            int position = getLayoutPosition(); // gets item position
            ArticleEntity article = articlesList.get(position);

            Toast.makeText(context, "Loading article...", Toast.LENGTH_SHORT).show();

            Intent i = new Intent(context, ArticleActivity.class);
            i.putExtra("webUrl", article.getWebUrl());
            context.startActivity(i);
        }
    }
}
