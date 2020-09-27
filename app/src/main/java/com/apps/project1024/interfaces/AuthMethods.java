package com.apps.project1024.interfaces;

import android.util.Log;

import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public interface AuthMethods {

    default void signUpWithEmailPassword(String email, String password){
        // avoid implementing this method in login
    }
    default void signInWithEmailPassword(String email, String password){
        // avoid implementing this method in register page

    };
    void signInWithGoogle(GoogleSignInAccount account);
    void signInWithFacebook(AccessToken accessToken);
}
