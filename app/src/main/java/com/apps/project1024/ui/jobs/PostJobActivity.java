package com.apps.project1024.ui.jobs;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.apps.project1024.databinding.ActivityPostJobBinding;
import com.apps.project1024.models.Job;
import com.apps.project1024.viewmodels.JobsViewModel;

import java.util.Date;

import static com.apps.project1024.utils.Utils.displayErrorMessage;
import static com.apps.project1024.utils.Utils.displayInfoMessage;
import static com.apps.project1024.utils.Utils.displaySuccessMessage;

public class PostJobActivity extends AppCompatActivity {

    private ActivityPostJobBinding mBinding;
    private JobsViewModel mViewModel;
    private String jobCategory = "";
    private String jobIndustry= "";
    private String location ="";
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityPostJobBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mProgress = new ProgressDialog(this);
        mViewModel = new ViewModelProvider(this).get(JobsViewModel.class);
        //clear any previous values.
        mViewModel.resetValues();

        mBinding.spinnerJobCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i>0) {
                   jobCategory = adapterView.getItemAtPosition(i).toString();
                } else {
                    jobCategory = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                jobCategory = "";
            }
        });


        mBinding.spinnerJobIndustry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i>0) {
                    jobIndustry = adapterView.getItemAtPosition(i).toString();
                } else {
                    jobIndustry = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                jobIndustry = "";
            }
        });

        mBinding.spinnerLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i>0) {
                    location = adapterView.getItemAtPosition(i).toString();
                } else {
                    location = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                location = "";
            }
        });


        mBinding.btnSubmit.setOnClickListener(view -> {
            if (validateInputs()) {
                Job job = new Job(
                        "",
                        mBinding.txtJobTitle.getText().toString().trim(),
                        mBinding.companyName.getText().toString().trim(),
                        jobCategory,
                        jobIndustry,
                        mBinding.txtJobDescription.getText().toString(),
                        location,
                        mBinding.checkboxInternship.isChecked(),
                        new Date().getTime()
                );


                mViewModel.createJobPost(job);
            }

        });


        mViewModel.getIsLoading().observe(this, isLoading->{
            if (isLoading) {
                showProgress("Submitting please wait...");
            } else {
                hideProgress();
            }
        });

        mViewModel.getSuccessMsg().observe(this, successMessage->{
            if (!successMessage.isEmpty()) {
                displaySuccessMessage(getApplicationContext(), successMessage);
                mViewModel.resetValues();
            }
        });

        mViewModel.getErrorMsg().observe(this, errorMessage->{
            if (!errorMessage.isEmpty()) {
                displayErrorMessage(getApplicationContext(), errorMessage);
                mViewModel.resetValues();
            }
        });



    }


    private void showProgress(String message){
        mProgress.setMessage(message);
        mProgress.setCanceledOnTouchOutside(false);
        mProgress.show();
    }

    private void hideProgress() {
        if (mProgress.isShowing()) {
            mProgress.dismiss();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewModel.resetValues();
    }

    private boolean validateInputs(){
        boolean isValid = true;
        if (TextUtils.isEmpty(mBinding.txtJobTitle.getText().toString().trim())) {
           isValid = false;
           mBinding.txtJobTitle.setError("Required.");
        } else {
            mBinding.txtJobTitle.setError(null);
        }


        if (jobCategory.isEmpty()) {
            displayInfoMessage(getApplicationContext(), "Select Job Category");
            isValid = false;
        }
        if (jobIndustry.isEmpty()) {
            displayInfoMessage(getApplicationContext(), "Select Job Industry");
            isValid = false;
        }

        if (TextUtils.isEmpty(mBinding.companyName.getText().toString().trim())) {
            isValid = false;
            mBinding.companyName.setError("Required.");
        } else {
            mBinding.companyName.setError(null);
        }

        if (location.isEmpty()) {
            isValid = false;
            displayInfoMessage(getApplicationContext(), "Select Job Location");

        }
        if (TextUtils.isEmpty(mBinding.txtJobDescription.getText().toString().trim())) {
            isValid = false;
            mBinding.txtJobDescription.setError("Required");
        } else {
            mBinding.txtJobDescription.setError(null);
        }

        return isValid;

    }
}