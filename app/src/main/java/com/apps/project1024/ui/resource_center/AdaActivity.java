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

        RecyclerView causesRecyclerView = mBinding.causesRecyclerview;
        ArrayList<String> causes= new ArrayList<>();
        causes.add("a)\tHome and family. Parents or older family members who use alcohol or drugs, or who are involved in criminal behavior, can increase a young person’s risk for developing a drug problem.");
        causes.add("b)\tPeers and school. Friends and acquaintances who use drugs can sway young people to try drugs for the first time. Academic failure or poor social skills can also put a person at risk for drug use.");
        causes.add("c)\tEarly use. Although taking drugs at any age can lead to addiction, research shows that the earlier a person begins to use drugs, the more likely they are to progress to more serious use. This may reflect the harmful effect that drugs can have on the developing brain. It also may be the result of early biological and social factors, such as genetics, mental illness, unstable family relationships, and exposure to physical or sexual abuse. Still, the fact remains that early drug use is a strong indicator of problems ahead—among them, substance use and addiction.");
        causes.add("d)\tMethod of use. Smoking a drug or injecting it into a vein increases its addictive potential. Both smoked and injected drugs enter the brain within seconds, producing a powerful rush of pleasure. However, this intense \"high\" can fade within a few minutes, and the person no longer feels good. Scientists believe that this low feeling drives people to repeat drug use in an attempt to recapture the high pleasurable state.");


        causesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        BulletListAdapter causesAdapter = new BulletListAdapter(this);
        adapter.setDataList(causes);
        causesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        causesRecyclerView.setHasFixedSize(true);
        causesRecyclerView.setAdapter(causesAdapter);

        RecyclerView effectsRecyclerView = mBinding.effectsRecyclerview;
        ArrayList<String> effects= new ArrayList<>();
        effects.add("•\tThe spread of infectious diseases such as HIV/AIDS and hepatitis C either through sharing of drug paraphernalia or unprotected sex");
        effects.add("•\tDeaths due to overdose or other complications from drug use");
        effects.add("•\tEffects on unborn children of pregnant women who use drugs");
        effects.add("•\tCrime, unemployment, domestic abuse, family dissolution, and homelessness");

        effectsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        BulletListAdapter effectsAdapter = new BulletListAdapter(this);
        effectsAdapter.setDataList(effects);
        effectsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        effectsRecyclerView.setHasFixedSize(true);
        effectsRecyclerView.setAdapter(effectsAdapter);


        RecyclerView consequencesRecyclerView = mBinding.consequencesRecyclerview;
        ArrayList<String> consequences= new ArrayList<>();
        consequences.add("Deaths; through vehicle crashes, homicides, suicides, alcohol poisoning, falls, burns and drowning");
        consequences.add("Injuries: drinking alcohol can cause kids to have accidents and get hurt.");
        consequences.add("Impairs judgment: Drinking can lead to poor decisions about engaging in risky behavior, including drinking and driving, sexual activity (such as unprotected sex), and aggressive or violent behavior.");
        consequences.add("Increases the risk of physical and sexual assault: Underage youth who drink are more likely to carry out or be the victim of a physical or sexual assault after drinking than others their age who do not drink.");
        consequences.add("Can lead to other problems: Drinking may cause youth to have trouble in school or with the law. Drinking alcohol also is associated with the use of other drugs.");
        consequences.add("Increases the risk of alcohol problems later in life; people who start drinking before the age of 15 are 4 times more likely to meet the criteria for alcohol dependence at some point in their lives");
        consequences.add("Interferes with brain development; young people’s brains keep developing well into their 20s. Alcohol can alter this development, potentially affecting both brain structure and function. ");

        consequencesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        BulletListAdapter consequencesAdapter = new BulletListAdapter(this);
        consequencesAdapter.setDataList(effects);
        consequencesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        consequencesRecyclerView.setHasFixedSize(true);
        consequencesRecyclerView.setAdapter(consequencesAdapter);


        RecyclerView treatmentRecyclerView = mBinding.treatmentRecyclerview;
        ArrayList<String> treatments= new ArrayList<>();
        treatments.add("•\tAddiction is a complex but treatable disease that affects brain function and behavior.");
        treatments.add("•\tNo single treatment is right for everyone.");
        treatments.add("•\tPeople need to have quick access to treatment.");
        treatments.add("•\tEffective treatment addresses all of the patient’s needs, not just his or her drug use.");
        treatments.add("•\tStaying in treatment long enough is critical.");
        treatments.add("•\tCounseling and other behavioral therapies are the most commonly used forms of treatment.");
        treatments.add("•\tMedications are often an important part of treatment, especially when combined with behavioral therapies");
        treatments.add("•\tTreatment plans must be reviewed often and modified to fit the patient’s changing needs.");
        treatments.add("•\tTreatment should address other possible mental disorders.");
        treatments.add("•\tMedically assisted detoxification is only the first stage of treatment.");
        treatments.add("•\tTreatment doesn't need to be voluntary to be effective.");
        treatments.add("•\tDrug use during treatment must be monitored continuously.");
        treatments.add("•\tTreatment programs should test patients for HIV/AIDS, hepatitis B and C, tuberculosis, and other infectious diseases as well as teach them about steps they can take to reduce their risk of these illnesses.");

        treatmentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        BulletListAdapter treatmentAdapter = new BulletListAdapter(this);
        treatmentAdapter.setDataList(treatments);
        treatmentRecyclerView.setItemAnimator(new DefaultItemAnimator());
        treatmentRecyclerView.setHasFixedSize(true);
        treatmentRecyclerView.setAdapter(treatmentAdapter);

        RecyclerView medicalTreatmentRecyclerView = mBinding.medicalTreatmentRecyclerview;
        ArrayList<String> medicalTreatments= new ArrayList<>();
        medicalTreatments.add("•\tDetoxification\n" +
                "Before any actual drug treatment can occur, it is necessary that drug dependent people be 'detoxified' (meaning that all drugs they are addicted to get physically removed from their bodies).\n");
        medicalTreatments.add("•\tDiet and Nutrition Concerns\n" +
                "Drug dependent persons often pursue their drugs of choice to the exclusion of eating a healthy diet. It is not uncommon to find that people presenting for drug or alcohol treatment are moderately to severely undernourished and lacking in the proper vitamins and nutrients. Getting drug dependent people on a healthy balanced diet is an important part of treatment.\n");
        medicalTreatments.add("•\tMedication (Symptom Reduction)\n" +
                "Recovering drug or alcohol dependent persons often complain of mood and anxiety problems. such symptoms can provoke the recovering person to return to substance abuse. For these reasons, a physician may prescribe medication to reduce symptoms of depression, anxiety, and other concerns.\n");
        medicalTreatments.add("•\tMedication (Relapse Reduction)\n" +
                "Detoxified drug and alcohol dependent people are at significant risk for relapsing back to using their drugs of choice, particularly in the early hours, days, weeks and months of their recovery. There are several medications that physicians can prescribe for their recovering patients which can help to minimize their chances of relapsing.\n");
        medicalTreatments.add("•\tDrug Screening\n" +
                "It is necessary to obtain objective and indisputable evidence of whether or not a drug and alcohol dependent person is using drugs or alcohol during the early and middle phases of treatment.\n");

        medicalTreatmentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        BulletListAdapter medicalTreatmentAdapter = new BulletListAdapter(this);
        medicalTreatmentAdapter.setDataList(medicalTreatments);
        medicalTreatmentRecyclerView.setItemAnimator(new DefaultItemAnimator());
        medicalTreatmentRecyclerView.setHasFixedSize(true);
        medicalTreatmentRecyclerView.setAdapter(medicalTreatmentAdapter);






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