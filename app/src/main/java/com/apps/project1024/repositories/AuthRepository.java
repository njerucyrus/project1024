package com.apps.project1024.repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.apps.project1024.interfaces.AuthMethods;
import com.apps.project1024.models.User;
import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.internal.api.FirebaseNoSignedInUserException;

public class AuthRepository implements AuthMethods {
    private static final String TAG = "AuthRepository";
    private static AuthRepository INSTANCE;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private MutableLiveData<String> successMsg = new MutableLiveData<>();
    private MutableLiveData<String> errorMsg = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    private MutableLiveData<FirebaseUser> mCurrentUser = new MutableLiveData<>();

    public synchronized static AuthRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AuthRepository();
        }
        return INSTANCE;
    }

    @Override
    public void signUpWithEmailPassword(String email, String password) {
        isLoading.postValue(true);
        successMsg.postValue("");
        errorMsg.postValue("");
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        //pass the account instance here
                        isLoading.postValue(false);
                        mCurrentUser.postValue(mAuth.getCurrentUser());
                        setDeviceToken(mAuth.getCurrentUser());
                        successMsg.postValue("Account created successfully.");


                    } else {
                        String error;
                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                            error = "Account With this email already exists. Login Instead";
                        } else {
                            error = "Unable to Authenticate. please try again later";
                        }

                        isLoading.postValue(false);
                        errorMsg.postValue(error);

                    }

                });
    }

    @Override
    public void signInWithEmailPassword(String email, String password) {
        isLoading.postValue(true);
        errorMsg.postValue("");
        successMsg.postValue("");
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        isLoading.postValue(false);
                        mCurrentUser.postValue(mAuth.getCurrentUser());
                        setDeviceToken(mAuth.getCurrentUser());
                        successMsg.postValue("Login Successful.");


                    } else {

                        String error = "Unable to Authenticate. please try again later";
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            error = "Invalid email/password";
                        } else if (task.getException() instanceof FirebaseAuthInvalidUserException) {
                            error = "Sorry. We could not find any user account matching details provided. Sign Up Instead.";
                        }

                        Log.e(TAG, "signInWithEmailPassword: ", task.getException());

                        errorMsg.postValue(error);

                    }
                });

    }

    @Override
    public void signInWithGoogle(GoogleSignInAccount account) {
        isLoading.postValue(true);
        successMsg.postValue("");
        errorMsg.postValue("");
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        isLoading.postValue(false);
                        mCurrentUser.postValue(mAuth.getCurrentUser());
                        setDeviceToken(mAuth.getCurrentUser());
                        successMsg.postValue("Authentication Successful");


                    } else {
                        isLoading.postValue(false);
                        String message = "Authentication Failed.";
                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                            message = "Account already exists please login in instead.";
                        }

                        errorMsg.postValue(message);

                    }


                });
    }

    @Override
    public void signInWithFacebook(AccessToken accessToken) {
        isLoading.postValue(true);
        successMsg.postValue("");
        errorMsg.postValue("");
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        isLoading.postValue(false);
                        setDeviceToken(mAuth.getCurrentUser());
                        mCurrentUser.postValue(mAuth.getCurrentUser());
                        successMsg.postValue("Authentication Successful");


                    } else {
                        isLoading.postValue(false);

                        String message = "Authentication Failed.";
                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                            message = "Account already exists please login in instead.";
                        }

                        errorMsg.postValue(message);

                    }


                });
    }


    public void createProfile(User user) {
        isLoading.postValue(true);
        successMsg.postValue("");
        errorMsg.postValue("");
        db.collection("profiles").document(user.getUserId()).set(user)
                .addOnSuccessListener(aVoid -> {
                    isLoading.postValue(false);
                    successMsg.postValue("Profile created successfully.");
                }).addOnFailureListener(e -> {
            Log.e(TAG, "createProfile: ", e);
            isLoading.postValue(false);
            errorMsg.postValue("Error occurred while creating your profile.Please try again later.");
        });
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


    public LiveData<FirebaseUser> getCurrentUser() {
        return mCurrentUser;
    }

    public void resetValues() {
        errorMsg.postValue("");
        successMsg.postValue("");
        isLoading.postValue(false);
    }

    private void setDeviceToken(FirebaseUser mCurrentUser) {
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("device_tokens");
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(instanceIdResult -> {

            String token = instanceIdResult.getToken();

            mRef.child(mCurrentUser.getUid()).child("deviceToken").setValue(token);

        });
    }

    public LiveData<User> getProfile(String userId) {
        isLoading.postValue(true);
        successMsg.postValue("");
        errorMsg.postValue("");
        MutableLiveData<User> userProfileLiveData = new MutableLiveData<>();
        db.collection("profiles").document(userId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        User user = documentSnapshot.toObject(User.class);
                        userProfileLiveData.postValue(user);
                        successMsg.postValue("profile retrieved.");
                    } else {
                        userProfileLiveData.postValue(null);
                    }
                }).addOnFailureListener(e -> {
            userProfileLiveData.postValue(null);

            Log.e(TAG, "getProfile: ", e);
            errorMsg.postValue("Error occurred while retrieving your profile.");
        });

        return userProfileLiveData;
    }
}
