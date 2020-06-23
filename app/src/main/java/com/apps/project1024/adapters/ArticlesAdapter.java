package com.apps.project1024.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.project1024.databinding.ArticleLayoutBinding;
import com.apps.project1024.models.Article;

import java.util.ArrayList;
import java.util.List;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ViewHolder> {

    private List<Article> articles = new ArrayList<>();
    private Context context;
    public ArticlesAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ArticleLayoutBinding binding = ArticleLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ArticlesAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    static final class ViewHolder extends RecyclerView.ViewHolder{
        ArticleLayoutBinding mBinding;
        public ViewHolder(ArticleLayoutBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
        notifyDataSetChanged();
    }
}
