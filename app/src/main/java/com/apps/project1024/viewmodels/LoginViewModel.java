package com.apps.project1024.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.apps.project1024.interfaces.AuthMethods;
import com.apps.project1024.models.User;
import com.apps.project1024.repositories.AuthRepository;
import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class LoginViewModel extends ViewModel implements AuthMethods {

    private AuthRepository repository;
    private MutableLiveData<String> successMsg;
    private MutableLiveData<String> errorMsg;
    private MutableLiveData<Boolean> isLoading;

    public LoginViewModel() {
        repository = AuthRepository.getInstance();
        errorMsg = repository.getErrorMsg();
        successMsg = repository.getSuccessMsg();
        isLoading = repository.getIsLoading();

    }

    public void signInWithEmailPassword(String email, String password) {
        repository.signInWithEmailPassword(email, password);
    }

    @Override
    public void signInWithGoogle(GoogleSignInAccount account) {
        repository.signInWithGoogle(account);
    }

    @Override
    public void signInWithFacebook(AccessToken accessToken) {
        repository.signInWithFacebook(accessToken);
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
