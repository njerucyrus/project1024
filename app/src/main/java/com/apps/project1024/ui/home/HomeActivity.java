package com.apps.project1024.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.apps.project1024.R;
import com.apps.project1024.databinding.ActivityHomeBinding;
import com.apps.project1024.ui.articles.ArticlesFragment;
import com.apps.project1024.ui.articles.CreateArticleActivity;
import com.apps.project1024.ui.forums.ForumsActivity;
import com.apps.project1024.ui.jobs.JobListingFragment;
import com.apps.project1024.ui.jobs.PostJobActivity;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import java.lang.reflect.Method;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private static final String TAG = "HomeActivity";

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
                    FragmentTransaction articlesTxn = getSupportFragmentManager().beginTransaction();

                    boolean exists = getSupportFragmentManager().findFragmentByTag(ArticlesFragment.class.getSimpleName()) != null;

                    articlesTxn.replace(binding.container.getId(), new ArticlesFragment());
                    if (!exists) {
                        articlesTxn.addToBackStack(ArticlesFragment.class.getSimpleName());
                    }

                    articlesTxn.commit();

                    break;
                case 2:
                    startActivity(new Intent(HomeActivity.this, ForumsActivity.class));
                    break;

                case 4:
                    FragmentTransaction jobsTxn = getSupportFragmentManager().beginTransaction();

                    boolean jobsExist = getSupportFragmentManager().findFragmentByTag(JobListingFragment.class.getSimpleName()) != null;

                    jobsTxn.replace(binding.container.getId(), new JobListingFragment());
                    if (!jobsExist) {
                            jobsTxn.addToBackStack(JobListingFragment.class.getSimpleName());
                    }

                    jobsTxn.commit();
                    break;

            }

            return null;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        if(menu.getClass().getSimpleName().equals("MenuBuilder")){
            try{
                Method m = menu.getClass().getDeclaredMethod(
                        "setOptionalIconsVisible", Boolean.TYPE);
                m.setAccessible(true);
                m.invoke(menu, true);
            }
            catch(NoSuchMethodException e){
                Log.e(TAG, "onMenuOpened", e);
            }
            catch(Exception e){
                throw new RuntimeException(e);
            }
        }
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod(
                            "setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (NoSuchMethodException e) {
                    Log.e(TAG, "onPrepareActionMode", e);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return super.onPrepareOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        final int id = item.getItemId();

        if (id == R.id.action_create_article) {
            startActivity(new Intent(this, CreateArticleActivity.class));
        } else if (id == R.id.action_post_job) {
            startActivity(new Intent(this, PostJobActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
