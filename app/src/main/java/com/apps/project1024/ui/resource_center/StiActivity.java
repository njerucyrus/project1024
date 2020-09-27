package com.apps.project1024.ui.resource_center;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.apps.project1024.R;
import com.apps.project1024.databinding.ActivityStiBinding;

public class StiActivity extends AppCompatActivity {

    private ActivityStiBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityStiBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle("STIs");
            ab.setDisplayHomeAsUpEnabled(true);
        }

    }
}