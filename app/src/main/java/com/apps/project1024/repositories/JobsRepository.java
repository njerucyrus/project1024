package com.apps.project1024.repositories;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.apps.project1024.models.Job;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class JobsRepository {
    private static final String TAG = "JobsRepository";
    private static JobsRepository INSTANCE;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private MutableLiveData<String> successMsg = new MutableLiveData<>();
    private MutableLiveData<String> errorMsg = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public static JobsRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new JobsRepository();

        }
        return INSTANCE;
    }

    public void createJobPost(Job job) {
        isLoading.postValue(true);
        successMsg.postValue("");
        errorMsg.postValue("");
        DocumentReference docRef = db.collection("jobs").document();
        String docId = docRef.getId();
        job.setJobId(docId);
        db.collection("jobs").document(job.getJobId()).set(job)
                .addOnSuccessListener(aVoid -> {
                    successMsg.postValue("Job Posted successfully.");
                    isLoading.postValue(false);

                }).addOnFailureListener(e -> {
            Log.e(TAG, "createJobPost: ", e);
            errorMsg.postValue("Error occurred while posting this job to the job listing.Please try again later");
            isLoading.postValue(false);
        });
    }

    public LiveData<List<Job>> fetchJobs(){
        isLoading.postValue(true);
        errorMsg.postValue("");
        MutableLiveData<List<Job>> jobsLiveData = new MutableLiveData<>();
        db.collection("jobs").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Job> jobList = new ArrayList<>();
                for (DocumentSnapshot snapshot: queryDocumentSnapshots.getDocuments()) {
                    if (snapshot.exists()) {
                        Job job = snapshot.toObject(Job.class);
                        if (job !=null) {
                            jobList.add(job);
                        }
                    }
                }
                jobsLiveData.postValue(jobList);
            }
        }).addOnFailureListener(e -> {
            Log.e(TAG, "onFailure: ", e);
            errorMsg.postValue("Error occurred while fetching jobs listing.");
        });

        return jobsLiveData;
    }

    public MutableLiveData<String> getSuccessMsg() {
        return successMsg;
    }

    public MutableLiveData<String> getErrorMsg() {
        return errorMsg;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }


    public void resetValues() {
        errorMsg.postValue("");
        successMsg.postValue("");
        isLoading.postValue(false);
    }

}
