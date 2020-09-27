package com.apps.project1024.ui.resource_center;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.project1024.adapters.BulletListAdapter;
import com.apps.project1024.databinding.ActivityHivBinding;

import java.util.ArrayList;

public class HivActivity extends AppCompatActivity {

    private ActivityHivBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityHivBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }

        RecyclerView recyclerView = mBinding.preventHivRecyclerView;
        ArrayList<String> strings = new ArrayList<>();
        strings.add("Condoms: When used consistently and correctly, condoms are highly effective in protecting against HIV, as well as many other sexually transmitted diseases (STDs). Condoms are also the only method of protection that prevents both pregnancy and disease. Like male condoms, female condoms are another barrier method of protection. Female condoms are inserted into the vagina.");
        strings.add("PrEP: This once a day pill is available by prescription for people who do not have HIV and want added protection. When taken as prescribed, PrEP is highly effective at preventing HIV.");
        strings.add("Treatment as Prevention: In addition to improving health, antiretrovirals (ARVs), the prescription medications used to treat HIV, also prevent the spread of the virus to others by lowering the amount of virus in the body, often to levels undetectable by standard lab tests.");
        strings.add("Clean injection equipment: Needle-exchange programs in many cities offer free, clean syringes and provide a safe means of disposal of used ones. Only use syringes that come from a reliable source.");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        BulletListAdapter adapter = new BulletListAdapter(this);
        adapter.setDataList(strings);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        RecyclerView protect = mBinding.protectRecyclerView;
        ArrayList<String> protectItems = new ArrayList<>();
        protectItems.add("Abstinence");
        protectItems.add("Remain faithful in a relationship with an uninfected equally faithful partner with no other risk behavior");
        protectItems.add("Use male or female condoms correctly each time you have sex.");
        protectItems.add("Do not have oral sex when your partner is on their period, having bleeding gums, sores or abrasions on your genitals.");
        protectItems.add("Avoid sexual activities or practices that may injure body tissues and result in direct blood contact");
        protectItems.add("If you do use injection drugs don’t share needles or syringes with another person. Obtain clean needles and syringes from Needle Exchange Program");
        protectItems.add("Avoid having sex under the influence of alcohol or drugs that may alter your ability to make safer sexual decisions");
        protectItems.add("Do not share piercing or tattoo equipment.");

        BulletListAdapter protectAdapter = new BulletListAdapter(this);
        protectAdapter.setDataList(protectItems);
        protect.setLayoutManager(new LinearLayoutManager(this));
        protect.setItemAnimator(new DefaultItemAnimator());
        protect.setHasFixedSize(true);
        protect.setAdapter(protectAdapter);

        ArrayList<String> benefits = new ArrayList<>();
        benefits.add("If you know that you are infected, you can take precautions to prevent the spread of HIV to others.");
        benefits.add("If you know you are uninfected, you avoid risk sexual behavior that put you at risk of being infected");


        RecyclerView benefitsRecyclerView = mBinding.testBenefit;
        BulletListAdapter testBenefitAdapter = new BulletListAdapter(this);
        testBenefitAdapter.setDataList(benefits);
        benefitsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        benefitsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        benefitsRecyclerView.setHasFixedSize(true);
        benefitsRecyclerView.setAdapter(testBenefitAdapter);



        ArrayList<String> risks = new ArrayList<>();

        risks.add("Having unprotected anal or vaginal sex");
        risks.add("Having another sexually transmitted infection such as syphilis, herpes, chlamydia, gonorrhoea, and bacterial vaginosis;");
        risks.add("Sharing contaminated needles, syringes and other injecting equipment and drug solutions when injecting drugs");
        risks.add("Receiving unsafe injections, blood transfusions, medical procedures that involve unsterile cutting or piercing; and");
        risks.add("Experiencing accidental needle stick injuries, including among health workers");
        risks.add("Having multiple sexual partners");
        risks.add("Using nonsterile needles for piercing or tattooing");
        risks.add("Other factors that may put you at risk of HIV infection include:");
        risks.add("Having been the victim of sexual assault");
        risks.add("Having sex while under the influence of drugs or alcohol");
        risks.add("Having a mother who had HIV when you were born");

        RecyclerView riskFactors = mBinding.riskFactors;
        BulletListAdapter riskAdapter = new BulletListAdapter(this);
        riskAdapter.setDataList(risks);
        riskFactors.setLayoutManager(new LinearLayoutManager(this));
        riskFactors.setItemAnimator(new DefaultItemAnimator());
        riskFactors.setHasFixedSize(true);
        riskFactors.setAdapter(riskAdapter);




        ArrayList<String> ways = new ArrayList<>();
        ways.add("Saliva tears, sweat, feces, urine, vomit or ear wax");
        ways.add("Kissing, hugging or touching");
        ways.add("Massage");
        ways.add("Shaking hands");
        ways.add("Insect or animal bites");
        ways.add("Living in the same house with someone who has HIV");
        ways.add("Sharing showers, bathrooms, pools or toilets with someone with HIV");
        ways.add("Touching public surfaces – like doorknobs, phone booths, or public benches");
        ways.add("Sharing food, drink or dishes");
        ways.add("Sharing a cup");
        ways.add("Sneezing");
        ways.add("Sharing items of clothing, bed linens or towels");
        ways.add("Sports, going to the gym, sharing exercise equipment");


        RecyclerView noHivWays = mBinding.noHivWays;
        BulletListAdapter noHivWaysAdapter = new BulletListAdapter(this);
        noHivWaysAdapter.setDataList(ways);
        noHivWays.setLayoutManager(new LinearLayoutManager(this));
        noHivWays.setItemAnimator(new DefaultItemAnimator());
        noHivWays.setHasFixedSize(true);
        noHivWays.setAdapter(noHivWaysAdapter);




    }
}