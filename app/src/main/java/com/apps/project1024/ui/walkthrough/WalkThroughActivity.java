package com.apps.project1024.ui.walkthrough;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.apps.project1024.R;
import com.github.paolorotolo.appintro.AppIntro;

public class WalkThroughActivity extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addSlide(Screen1Fragment.newInstance());
        addSlide(Screen2Fragment.newInstance());
        addSlide(Screen3Fragment.newInstance());
        addSlide(Screen4Fragment.newInstance());
        progressButtonEnabled = false;
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();

        }
    }


}
