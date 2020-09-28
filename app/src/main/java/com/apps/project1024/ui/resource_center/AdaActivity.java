package com.apps.project1024.ui.resource_center;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.Activity;
import android.os.Bundle;

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
        strings.add("Impaired speech and motor coordination");
        strings.add("Bloodshot eyes or pupils that are larger or smaller than usual");
        strings.add("Changes in physical appearance or personal hygiene");
        strings.add("Changes in appetite or sleep patterns");
        strings.add("Sudden weight loss or weight gain");
        strings.add("Unusual smells on breath, body, or clothing");
        strings.add("Changes in mood or disinterest in engaging in relationships or activities");


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        BulletListAdapter adapter = new BulletListAdapter(this);
        adapter.setDataList(strings);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        RecyclerView causesRecyclerView = mBinding.signsRecyclerview;
        ArrayList<String> causes= new ArrayList<>();
        causes.add("a)\tHome and family. Parents or older family members who use alcohol or drugs, or who are involved in criminal behavior, can increase a young person’s risk for developing a drug problem.");
        causes.add("b)\tPeers and school. Friends and acquaintances who use drugs can sway young people to try drugs for the first time. Academic failure or poor social skills can also put a person at risk for drug use.");
        causes.add("c)\tEarly use. Although taking drugs at any age can lead to addiction, research shows that the earlier a person begins to use drugs, the more likely they are to progress to more serious use. This may reflect the harmful effect that drugs can have on the developing brain. It also may be the result of early biological and social factors, such as genetics, mental illness, unstable family relationships, and exposure to physical or sexual abuse. Still, the fact remains that early drug use is a strong indicator of problems ahead—among them, substance use and addiction.");
        causes.add("d)\tMethod of use. Smoking a drug or injecting it into a vein increases its addictive potential. Both smoked and injected drugs enter the brain within seconds, producing a powerful rush of pleasure. However, this intense \"high\" can fade within a few minutes, and the person no longer feels good. Scientists believe that this low feeling drives people to repeat drug use in an attempt to recapture the high pleasurable state.");


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        BulletListAdapter causesAdapter = new BulletListAdapter(this);
        adapter.setDataList(causes);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(causesAdapter);



    }
}