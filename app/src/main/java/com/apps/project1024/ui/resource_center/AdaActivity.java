package com.apps.project1024.ui.resource_center;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

import com.apps.project1024.R;
import com.apps.project1024.adapters.BulletListAdapter;
import com.apps.project1024.databinding.ActivityAdaBinding;

import java.util.ArrayList;

public class AdaActivity extends AppCompatActivity {

    private ActivityAdaBinding mBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityAdaBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        ActionBar ab = getSupportActionBar();
        if (ab!=null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setTitle("ADA FAQs");
        }

        RecyclerView recyclerView = mBinding.signsRecyclerview;
        ArrayList<String> strings = new ArrayList<>();
        strings.add("impaired speech and motor coordination");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        BulletListAdapter adapter = new BulletListAdapter(this);
        adapter.setDataList(strings);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}