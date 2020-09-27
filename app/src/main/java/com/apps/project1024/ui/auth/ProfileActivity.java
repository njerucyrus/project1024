package com.apps.project1024.ui.auth;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.apps.project1024.R;
import com.apps.project1024.databinding.ActivityProfileBinding;
import com.apps.project1024.models.User;
import com.apps.project1024.ui.home.HomeActivity;
import com.apps.project1024.viewmodels.ProfileViewModel;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.apps.project1024.utils.Utils.displayErrorMessage;
import static com.apps.project1024.utils.Utils.displayInfoMessage;
import static com.apps.project1024.utils.Utils.displaySuccessMessage;

public class ProfileActivity extends AppCompatActivity {

    private ActivityProfileBinding mBinding;
    private String ageBand;
    private String accountType;
    private FirebaseUser mCurrentUser;
    private ProfileViewModel mViewModel;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Complete Profile");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mProgress = new ProgressDialog(this);

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        mViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        mBinding.chipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == mBinding.chip1.getId()) {
                ageBand = "10-14 years";
            } else if (checkedId == mBinding.chip2.getId()) {
                ageBand = "15-19 years";
            } else if (checkedId == mBinding.chip3.getId()) {
                ageBand = "20-24 years";
            } else if (checkedId == mBinding.chip4.getId()){
                ageBand = "24+ years";
            } else {
                ageBand = "";
            }
        });

        mBinding.btnSubmitProfile.setOnClickListener(view -> {
            if (validateInputs()) {
                User user = new User(
                        mBinding.txtName.getText().toString().trim(),
                        mBinding.txtPhoneNumber.getText().toString().trim(),
                        "",
                        accountType,
                        ageBand,
                        mCurrentUser.getUid(),
                        false


                );

                mViewModel.createProfile(user);
            }else {
                Snackbar.make(view, "Fix the errors above ", Snackbar.LENGTH_LONG).show();
            }
        });

        mViewModel.getIsLoading().observe(this, isLoading->{
            if (isLoading){
                showProgress("Creating profile...");
            } else {
                hideProgress();
            }
        });

        mViewModel.getSuccessMsg().observe(this, successMessage->{
            if (!successMessage.isEmpty()) {
                displaySuccessMessage(getApplicationContext(), successMessage);
                startActivity(new Intent(ProfileActivity.this, HomeActivity.class));
                finish();
                mViewModel.resetValues();
            }
        });

        mViewModel.getErrorMsg().observe(this, errorMsg->{
            if (!errorMsg.isEmpty()) {
                displayErrorMessage(getApplicationContext(), errorMsg);
                mViewModel.resetValues();
            }
        });


        mBinding.groupAccountType.setOnCheckedChangeListener((radioGroup, i) -> {
            if (radioGroup.getCheckedRadioButtonId() == R.id.radio_student){
                accountType = "Student";
            } else if(radioGroup.getCheckedRadioButtonId() == R.id.radio_professional) {
                accountType = "Professional";
            } else {
                accountType = "";
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

    private boolean validateInputs() {
        boolean isValid = true;
        if (ageBand.isEmpty()) {
            displayInfoMessage(getApplicationContext(), "Age band not selected");
            isValid = false;
        }

        if (TextUtils.isEmpty(mBinding.txtName.getText().toString().trim())) {
            mBinding.txtName.setError("Required.");
            isValid = false;
        }  else {
            mBinding.txtName.setError(null);
        }

        if (TextUtils.isEmpty(accountType)){
            displayInfoMessage(getApplicationContext(), "Account type not selected.");
            isValid = false;
        }


        if (TextUtils.isEmpty(mBinding.txtPhoneNumber.getText().toString().trim())) {
            mBinding.txtPhoneNumber.setError("Required");
            isValid = false;
        }

        return isValid;
    }
}