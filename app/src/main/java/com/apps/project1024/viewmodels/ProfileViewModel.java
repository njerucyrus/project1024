package com.apps.project1024.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.apps.project1024.models.User;
import com.apps.project1024.repositories.AuthRepository;

public class ProfileViewModel extends ViewModel {
    private AuthRepository repository;
    private MutableLiveData<String> successMsg;
    private MutableLiveData<String> errorMsg;
    private MutableLiveData<Boolean> isLoading;

    public ProfileViewModel() {
        repository = AuthRepository.getInstance();
        errorMsg = repository.getErrorMsg();
        successMsg = repository.getSuccessMsg();
        isLoading = repository.getIsLoading();
    }

    public void createProfile(User user) {
        repository.createProfile(user);
    }
    public LiveData<User> getProfile(String userId) {
        return repository.getProfile(userId);
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
