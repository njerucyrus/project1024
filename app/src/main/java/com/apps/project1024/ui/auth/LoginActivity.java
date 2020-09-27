package com.apps.project1024.ui.auth;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.apps.project1024.R;
import com.apps.project1024.databinding.ActivityLoginBinding;
import com.apps.project1024.ui.home.HomeActivity;
import com.apps.project1024.viewmodels.LoginViewModel;
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
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;

import static com.apps.project1024.utils.Utils.displayErrorMessage;
import static com.apps.project1024.utils.Utils.displaySuccessMessage;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 1000;
    private static final String TAG = "LoginActivity";
    private ActivityLoginBinding mBinding;
    private LoginViewModel mViewModel;
    private ProgressDialog mProgress;
    private CallbackManager mCallbackManager;
    private GoogleSignInClient mGoogleSignInClient;
    private LoginManager loginManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityLoginBinding.inflate(getLayoutInflater());
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
        mGoogleSignInClient = GoogleSignIn.getClient(LoginActivity.this, gso);

        initFacebookSignIn();

        mProgress = new ProgressDialog(this);
        mViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        mBinding.btnFbLogin.setOnClickListener(v->{
            loginManager.logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile"));

        });

        mBinding.btnGoogleLogin.setOnClickListener(view -> {
            openGoogleAccountChooser();
        });
        mBinding.tvNoAccount.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });

        mBinding.btnLogin.setOnClickListener(v->{
            if (validateInputs()) {
                String email = mBinding.txtEmail.getText().toString().trim();
                String password = mBinding.txtPassword.getText().toString().trim();
                mViewModel.signInWithEmailPassword(email, password);
            }

        });

        mViewModel.getIsLoading().observe(this, isLoading->{
            if (isLoading) {
                //showProgress
                showProgress("Authenticating please wait...");
            } else {
                //hideProgress
                hideProgress();
            }
        });

        mViewModel.getSuccessMsg().observe(this, successMessage->{
            if (!successMessage.isEmpty()) {
                displaySuccessMessage(getApplicationContext(), successMessage);
                mViewModel.resetValues();
                checkIfProfileExists();
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

    private boolean validateInputs() {
        boolean isValid = true;
        if (TextUtils.isEmpty(mBinding.txtEmail.getText().toString().trim())) {
            isValid = false;
            mBinding.txtEmail.setError("Required.");
        } else {
            mBinding.txtEmail.setError(null);
        }

        if (TextUtils.isEmpty(mBinding.txtPassword.getText().toString().trim())) {
            isValid = false;
            mBinding.txtPassword.setError("Required.");
        } else {
            mBinding.txtPassword.setError(null);
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
                displayErrorMessage(LoginActivity.this, "Google sign in failed");
                Log.e(TAG, "Google sign in failed", e);

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
                displayErrorMessage(LoginActivity.this, "Login Canceled");

            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                displayErrorMessage(LoginActivity.this, "Unable to login");
            }
        });

    }


    private void openGoogleAccountChooser() {
        showProgress("Opening account chooser please wait...");
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void checkIfProfileExists() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() !=null) {
            showProgress("Checking profile");
            mViewModel.getProfile(mAuth.getCurrentUser().getUid()).observe(this, user -> {
                if (user != null) {
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));

                } else {
                    startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
                }
                mViewModel.resetValues();
                finish();
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkIfProfileExists();
    }
}
