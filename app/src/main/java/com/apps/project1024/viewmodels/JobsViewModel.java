package com.apps.project1024.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.apps.project1024.models.Job;
import com.apps.project1024.repositories.JobsRepository;

import java.util.List;

public class JobsViewModel extends ViewModel {
    private JobsRepository repository;
    private MutableLiveData<String> successMsg;
    private MutableLiveData<String> errorMsg;
    private MutableLiveData<Boolean> isLoading;

    public JobsViewModel() {
        repository = JobsRepository.getInstance();
        errorMsg = repository.getErrorMsg();
        successMsg = repository.getSuccessMsg();
        isLoading = repository.getIsLoading();
    }

    public void createJobPost(Job job) {
        repository.createJobPost(job);
    }

    public LiveData<List<Job>> fetchJobs(){
        return repository.fetchJobs();
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


