package com.apps.project1024.viewmodels;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.apps.project1024.models.Article;
import com.apps.project1024.repositories.ArticlesRepository;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class ArticlesViewModel extends AndroidViewModel {

    private ArticlesRepository repository;
    private MutableLiveData<String> successMsg;
    private MutableLiveData<String> errorMsg;
    private MutableLiveData<Boolean> isLoading;

    public ArticlesViewModel(@NonNull Application application) {
        super(application);
        repository = ArticlesRepository.getInstance(application);
        errorMsg = repository.getErrorMsg();
        successMsg = repository.getSuccessMsg();
        isLoading = repository.getIsLoading();

    }

    public void createArticle(Article article, Uri uri) {
        repository.createArticle(article, uri);
    }

    public LiveData<List<Article>> fetchArticles(){
        return repository.fetchArticles();
    }


    public LiveData<String> getSuccessMsg() {
        return successMsg;
    }

    public LiveData<String> getErrorMsg() {
        return errorMsg;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void resetValues() {
        repository.resetValues();
    }

}
