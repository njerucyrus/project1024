package com.apps.project1024.ui.auth;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.apps.project1024.R;
import com.apps.project1024.databinding.ActivityLoginBinding;
import com.apps.project1024.ui.home.HomeActivity;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }


        mBinding.btnFbLogin.setOnClickListener(v->{
                startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
        });

        mBinding.btnLogin.setOnClickListener(v->{
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));

        });


    }
}
