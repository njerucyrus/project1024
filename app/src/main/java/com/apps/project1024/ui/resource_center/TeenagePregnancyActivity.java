package com.apps.project1024.ui.resource_center;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.apps.project1024.R;
import com.apps.project1024.adapters.BulletListAdapter;
import com.apps.project1024.databinding.ActivityTeenagePregnancyBinding;

import java.util.ArrayList;

public class TeenagePregnancyActivity extends AppCompatActivity {

    private ActivityTeenagePregnancyBinding mBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityTeenagePregnancyBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        ActionBar ab =getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setElevation(0f);
            ab.setTitle("Teenage Pregnancy");
        }

        RecyclerView recyclerView = mBinding.teenagePregnancyCauses;
        ArrayList<String> strings = new ArrayList<>();
        strings.add("Lack of information about sexual and reproductive health and rights");
        strings.add("Inadequate access to services tailored to young people");
        strings.add("Family, community and social pressure to marry ");
        strings.add("Sexual violence ");
        strings.add("Child, early and forced marriage, which can be both a cause and a consequence");
        strings.add("Lack of education or dropping out of school");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        BulletListAdapter adapter = new BulletListAdapter(this);
        adapter.setDataList(strings);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);



        RecyclerView pregnancySigns = mBinding.pregnancySigns;
        ArrayList<String> signs = new ArrayList<>();
        signs.add("Missed or very light period");
        signs.add("Breast tenderness");
        signs.add("Nausea, often in the morning");
        signs.add("Vomiting");
        signs.add("Feeling lightheaded");
        signs.add("Fainting");
        signs.add("Weight gain");
        signs.add("Feeling tired");
        signs.add("Swelling abdomen");


        pregnancySigns.setLayoutManager(new LinearLayoutManager(this));
        BulletListAdapter signsAdapter = new BulletListAdapter(this);
        signsAdapter.setDataList(signs);
        pregnancySigns.setItemAnimator(new DefaultItemAnimator());
        pregnancySigns.setHasFixedSize(true);
        pregnancySigns.setAdapter(signsAdapter);




        RecyclerView pregnancyEffects = mBinding.pregnancyEffects;
        ArrayList<String> effects = new ArrayList<>();
        effects.add("Higher risk for pregnancy-related high blood pressure and its complications ");
        effects.add("Higher chance of becoming anemic. ");
        effects.add("Pregnancy or childbirth complications are the leading cause of death globally for girls ages 15 to 19.");
        effects.add("Frightened and worried about impact to family and friends.");
        effects.add("They get isolated and depressed");
        effects.add("Leads to problems both at home and at school");
        effects.add("Become school drop outs");
        effects.add("Living a life of poverty");


        pregnancyEffects.setLayoutManager(new LinearLayoutManager(this));
        BulletListAdapter effectsAdapter = new BulletListAdapter(this);
        effectsAdapter.setDataList(effects);
        pregnancyEffects.setItemAnimator(new DefaultItemAnimator());
        pregnancyEffects.setHasFixedSize(true);
        pregnancyEffects.setAdapter(effectsAdapter);



        RecyclerView pregnancyEffectsOnBabies = mBinding.effectsOnBabies;
        ArrayList<String> effectsOnBabies = new ArrayList<>();
        effectsOnBabies.add("Teen mothers are more likely to give birth to premature babies");
        effectsOnBabies.add("Sometimes, these babies lack complete development in their bodies and brains. Depending on how premature the baby is, this can lead to lifelong difficulties with health and development");
        effectsOnBabies.add("Premature babies also tend to be underweight. Underweight babies might have trouble breathing and feeding as infants. As adults, underweight babies are more susceptible to diseases such as diabetes and heart disease");
        effectsOnBabies.add("Low birth weight also affects brain development. Children who were born underweight have been observed to have learning difficulties.");
        effectsOnBabies.add("Infants born to teenage mothers are also at a higher risk of infant mortality.");



        pregnancyEffectsOnBabies.setLayoutManager(new LinearLayoutManager(this));
        BulletListAdapter effectsOnBabiesAdapter = new BulletListAdapter(this);
        effectsOnBabiesAdapter.setDataList(effectsOnBabies);
        pregnancyEffectsOnBabies.setItemAnimator(new DefaultItemAnimator());
        pregnancyEffectsOnBabies.setHasFixedSize(true);
        pregnancyEffectsOnBabies.setAdapter(effectsOnBabiesAdapter);



        RecyclerView pregnancyEffectsOnTeenFathers = mBinding.effectsOnTeenFathers;
        ArrayList<String> effectsOnFathers = new ArrayList<>();

        effectsOnFathers.add("Fathering a child as a teenager can be a frightening and life-changing event. Teen fathers don’t have to worry about the health implications of pregnancy and childbirth, but they could face similar difficulties staying in school and earning a living.");
        effectsOnFathers.add("Arrest or legal action against teens who are sexually active can have devastating effects. A young man may be required to register as a sex offender if he has reached the legal age of 18 years and his partner has not aged 17 or under");
        pregnancyEffectsOnTeenFathers.setLayoutManager(new LinearLayoutManager(this));
        BulletListAdapter effectsOnFathersAdapter = new BulletListAdapter(this);
        effectsOnFathersAdapter.setDataList(effectsOnFathers);
        pregnancyEffectsOnTeenFathers.setItemAnimator(new DefaultItemAnimator());
        pregnancyEffectsOnTeenFathers.setHasFixedSize(true);
        pregnancyEffectsOnTeenFathers.setAdapter(effectsOnFathersAdapter);


        RecyclerView riskFactors = mBinding.riskFactors;
        ArrayList<String> riskFactorsList = new ArrayList<>();
        riskFactorsList.add("having parents with low education levels");
        riskFactorsList.add("a history of child abuse");
        riskFactorsList.add("limited social networks");
        riskFactorsList.add("living in chaotic and unstable home environments");
        riskFactorsList.add("living in low-income communities");

        riskFactors.setLayoutManager(new LinearLayoutManager(this));
        BulletListAdapter riskFactorsAdapter = new BulletListAdapter(this);
        riskFactorsAdapter.setDataList(riskFactorsList);
        riskFactors.setItemAnimator(new DefaultItemAnimator());
        riskFactors.setHasFixedSize(true);
        riskFactors.setAdapter(riskFactorsAdapter);



        RecyclerView impactsToMothers = mBinding.impactToMother;
        ArrayList<String> impactsToMothersList = new ArrayList<>();
        impactsToMothersList.add("greater risk for lower birth weight and infant mortality");
        impactsToMothersList.add("less prepared to enter kindergarten");
        impactsToMothersList.add("rely more heavily on publicly funded health care");
        impactsToMothersList.add("are more likely to be incarcerated at some time during adolescence");
        impactsToMothersList.add("are more likely to drop out of high school");
        impactsToMothersList.add("are more likely to be unemployed or underemployed as a young adult");

        riskFactors.setLayoutManager(new LinearLayoutManager(this));
        BulletListAdapter impactsToMothersAdapter = new BulletListAdapter(this);
        impactsToMothersAdapter.setDataList(impactsToMothersList);
        impactsToMothers.setItemAnimator(new DefaultItemAnimator());
        impactsToMothers.setHasFixedSize(true);
        impactsToMothers.setAdapter(impactsToMothersAdapter);

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