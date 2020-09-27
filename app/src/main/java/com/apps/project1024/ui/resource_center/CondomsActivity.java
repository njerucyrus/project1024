package com.apps.project1024.ui.resource_center;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.apps.project1024.R;

public class CondomsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_condoms);
        ActionBar ab = getSupportActionBar();
        if (ab !=null) {
            ab.setTitle("Condoms FAQs");
            ab.setDisplayHomeAsUpEnabled(true);

        }
    }
}