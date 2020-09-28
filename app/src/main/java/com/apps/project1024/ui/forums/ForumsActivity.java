package com.apps.project1024.ui.forums;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.apps.project1024.R;
import com.apps.project1024.adapters.ForumsPagerAdapter;
import com.apps.project1024.databinding.ActivityForumsBinding;
import com.apps.project1024.ui.resource_center.HealthActivity;
import com.apps.project1024.ui.resource_center.HivActivity;
import com.apps.project1024.viewmodels.ForumsViewModel;
import com.google.firebase.auth.FirebaseAuth;

import es.dmoral.toasty.Toasty;

public class ForumsActivity extends AppCompatActivity {

    private ActivityForumsBinding mBinding;
    private ForumsViewModel mViewModel;
    private int[] tabIcons = {
            R.drawable.all,
            R.drawable.grow,
            R.drawable.elevate,
            R.drawable.enterprise,
            R.drawable.connect
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityForumsBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setTitle("Forums");
        }
        mViewModel = new ViewModelProvider(this).get(ForumsViewModel.class);

        mBinding.fab.setOnClickListener(view -> {
            mViewModel.getProfile(FirebaseAuth.getInstance().getCurrentUser().getUid()).observe(ForumsActivity.this, user -> {
                if (user !=null) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("profile", user);
                    startActivity(new Intent(ForumsActivity.this, CreateForumPostActivity.class)
                            .putExtra("bundle", bundle)
                    );

                }
            });
        });

        ForumsPagerAdapter pagerAdapter = new ForumsPagerAdapter(getSupportFragmentManager());
        mBinding.viewPager.setAdapter(pagerAdapter);
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager);
        setUpTabIcons();
        mBinding.forumMenu.cardReproductive.setOnClickListener(view -> {
            startActivity(new Intent(ForumsActivity.this, HealthActivity.class));
        });
        mBinding.forumMenu.cardCovid.setOnClickListener(view -> {
            Toasty.info(getApplicationContext(), "We are working on something awesome. Coming Soon", Toast.LENGTH_SHORT).show();
        });

        mBinding.forumMenu.cardHiv.setOnClickListener(view -> startActivity(new Intent(ForumsActivity.this, HivActivity.class)));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpTabIcons() {
        mBinding.tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        mBinding.tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        mBinding.tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        mBinding.tabLayout.getTabAt(3).setIcon(tabIcons[3]);
        mBinding.tabLayout.getTabAt(4).setIcon(tabIcons[4]);

    }
}