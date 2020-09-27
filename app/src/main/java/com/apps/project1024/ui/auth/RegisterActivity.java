package com.apps.project1024.ui.auth;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.apps.project1024.R;
import com.apps.project1024.databinding.ActivityRegisterBinding;
import com.apps.project1024.viewmodels.LoginViewModel;
import com.apps.project1024.viewmodels.RegisterViewModel;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import static com.apps.project1024.utils.Utils.displayErrorMessage;
import static com.apps.project1024.utils.Utils.displaySuccessMessage;
import static com.apps.project1024.utils.Utils.isValidEmail;

public class RegisterActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 100 ;
    private static final String TAG = "RegisterActivity";
    private ActivityRegisterBinding mBinding;
    private RegisterViewModel mViewModel;
    private ProgressDialog mProgress;
    private CallbackManager mCallbackManager;
    private GoogleSignInClient mGoogleSignInClient;
    private LoginManager loginManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        loginManager = LoginManager.getInstance();
        //Google Sign-in
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(RegisterActivity.this, gso);

        initFacebookSignIn();

        mProgress = new ProgressDialog(this);
        mViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        mBinding.btnRegister.setOnClickListener(view -> {

            if (validateInputs()) {
                mViewModel.signUpWithEmailPassword(mBinding.txtEmail.getText().toString().trim(), mBinding.txtPassword.getText().toString().trim());
            } else {
                Snackbar.make(view, "Fix the errors above", Snackbar.LENGTH_LONG).show();
            }

        });

        mViewModel.getSuccessMsg().observe(this, message->{
            if (!message.isEmpty()) {
                displaySuccessMessage(getApplicationContext(), message);
                startActivity(new Intent(RegisterActivity.this, ProfileActivity.class));
                finish();
                mViewModel.resetValues();
            }
        });

        mViewModel.getErrorMsg().observe(this, message->{
            if (!message.isEmpty()) {
                displayErrorMessage(getApplicationContext(), message);
                mViewModel.resetValues();
            }
        });

        mViewModel.getIsLoading().observe(this, isLoading->{
            if (isLoading) {
                showProgress("Creating account please wait...");
            } else {
                hideProgress();
            }
        });


    }

    private boolean validateInputs() {
        boolean isValid = true;

        if (TextUtils.isEmpty(mBinding.txtEmail.getText().toString().trim())) {
            mBinding.txtEmail.setError("Required");
            isValid = false;
        }else {
            mBinding.txtEmail.setError(null);
        }

        if(!isValidEmail(mBinding.txtEmail.getText().toString())) {
            mBinding.txtEmail.setError("Invalid email");
            isValid = false;
        } else {
            mBinding.txtEmail.setError(null);

        }

        if (TextUtils.isEmpty(mBinding.txtPassword.getText().toString().trim())) {
            mBinding.txtPassword.setError("Required");
            isValid = false;
        }else{
            mBinding.txtPassword.setError(null);
        }

        if (TextUtils.isEmpty(mBinding.txtConfirmPassword.getText().toString().trim())) {
            mBinding.txtConfirmPassword.setError("Required");
            isValid = false;
        } else {
            mBinding.txtConfirmPassword.setError(null);
        }

        if (!TextUtils.equals(mBinding.txtConfirmPassword.getText().toString().trim(), mBinding.txtPassword.getText().toString())) {
            mBinding.txtConfirmPassword.setError("Password do not match");
            isValid = false;
        } else {
            mBinding.txtConfirmPassword.setError(null);
        }

        return isValid;


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //check which provider initiated sign-in
        if (requestCode == RC_SIGN_IN) {
            hideProgress();
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);

                mViewModel.signInWithGoogle(account);
            } catch (ApiException e) {
                hideProgress();
                displayErrorMessage(RegisterActivity.this, "Google sign in failed");
                Log.w(TAG, "Google sign in failed", e);

            }
        } else {
            //pass data to facebook sdk
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void initFacebookSignIn() {
        mCallbackManager = CallbackManager.Factory.create();

        loginManager.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                mViewModel.signInWithFacebook(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

                Log.d(TAG, "facebook:onCancel");
                displayErrorMessage(RegisterActivity.this, "Login Canceled");

            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                displayErrorMessage(RegisterActivity.this, "Unable to login");
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

    private void openGoogleAccountChooser() {
        showProgress("Opening account chooser please wait...");
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
}
