package com.apps.project1024.viewmodels;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.apps.project1024.models.ForumPost;
import com.apps.project1024.models.User;
import com.apps.project1024.repositories.AuthRepository;
import com.apps.project1024.repositories.ForumPostRepository;

import java.util.List;

public class ForumsViewModel extends AndroidViewModel {

    private ForumPostRepository repository;
    private AuthRepository authRepository;
    private MutableLiveData<String> successMsg;
    private MutableLiveData<String> errorMsg;
    private MutableLiveData<Boolean> isLoading;

    public ForumsViewModel(@NonNull Application application) {
        super(application);
        repository = ForumPostRepository.getInstance(application);
        authRepository = AuthRepository.getInstance();
        errorMsg = repository.getErrorMsg();
        successMsg = repository.getSuccessMsg();
        isLoading = repository.getIsLoading();
    }

    public LiveData<User> getProfile(String userId) {
        return authRepository.getProfile(userId);
    }

    public void createPost(ForumPost post, Uri mediaUri) {
        repository.createForumPost(post, mediaUri);
    }


    public LiveData<List<ForumPost>> getAllPosts() {
        return repository.getAllPost();
    }

    public LiveData<List<ForumPost>> getGrowthPosts() {
        return repository.getGrowthPosts();
    }

    public LiveData<List<ForumPost>> getElevatePosts() {
        return repository.getElevatePosts();
    }

    public LiveData<List<ForumPost>> getEmpowerPosts() {
        return repository.getEmpowerPosts();
    }

    public LiveData<List<ForumPost>> getKonnectPosts() {
        return repository.getKonnectPosts();
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
