package com.apps.project1024.ui.resource_center;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.apps.project1024.R;
import com.apps.project1024.adapters.HealthAdapter;
import com.apps.project1024.databinding.ActivityHealthBinding;

import com.apps.project1024.interfaces.RecyclerItemClickListener;
import com.apps.project1024.models.HealthMenuItem;

import java.util.ArrayList;
import java.util.List;

public class HealthActivity extends AppCompatActivity implements RecyclerItemClickListener<HealthMenuItem> {

    private ActivityHealthBinding mBinding;
    private HealthAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityHealthBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        adapter = new HealthAdapter(this, this);
        mBinding.recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        mBinding.recyclerView.setAdapter(adapter);
        mBinding.recyclerView.setHasFixedSize(true);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle("Health");
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setElevation(0f);
        }
        loadMenus();

    }

    private void loadMenus() {
        List<HealthMenuItem> menuItems = new ArrayList<>();
        menuItems.add(new HealthMenuItem(
                R.drawable.stds,
                "Sexual Transmitted Diseases"
        ));

        menuItems.add(new HealthMenuItem(
                R.drawable.contraceptives,
                "ADA FAQs"
        ));

        menuItems.add(new HealthMenuItem(
                R.drawable.condoms,
                "Condoms"
        ));

        menuItems.add(new HealthMenuItem(
                R.drawable.pregnant,
                "Teenage Pregnancy"
        ));

        menuItems.add(new HealthMenuItem(
                R.drawable.mental_health,
                "Mental Health"
        ));
        menuItems.add(new HealthMenuItem(
                R.drawable.covid19,
                "Covid-19"
        ));

        adapter.setMenuItems(menuItems);


    }

    @Override
    public void onItemClicked(HealthMenuItem item) {
        if (item.getResId() == R.drawable.contraceptives) {
            startActivity(new Intent(HealthActivity.this, AdaActivity.class));
        } else if (item.getResId() == R.drawable.stds) {
            startActivity(new Intent(HealthActivity.this, StiActivity.class));
        } else if (item.getResId() == R.drawable.pregnant) {
            startActivity(new Intent(HealthActivity.this, TeenagePregnancyActivity.class));

        } else if (item.getResId() == R.drawable.condoms) {
            startActivity(new Intent(HealthActivity.this, CondomsActivity.class));

        }
    }
}