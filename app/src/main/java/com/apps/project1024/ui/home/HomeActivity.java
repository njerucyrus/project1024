package com.apps.project1024.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.apps.project1024.R;
import com.apps.project1024.databinding.ActivityHomeBinding;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MeowBottomNavigation bottomNavigation = binding.bottomNavigation;

        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_baseline_home_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_baseline_forum_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_baseline_message_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.ic_baseline_work_24));

        bottomNavigation.setOnClickMenuListener(model -> {
            switch (model.getId()) {
                case 1:
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                    boolean exists = getSupportFragmentManager().findFragmentByTag(ArticlesFragment.class.getSimpleName()) != null;

                    transaction.replace(binding.container.getId(), new ArticlesFragment());
                    if (!exists) {
                        transaction.addToBackStack(ArticlesFragment.class.getSimpleName());
                    }

                    transaction.commit();

                    break;
            }

            return null;
        });
    }
}
