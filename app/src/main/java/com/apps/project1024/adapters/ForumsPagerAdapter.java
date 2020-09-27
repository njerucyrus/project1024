package com.apps.project1024.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.apps.project1024.ui.forums.AllForumsFragment;
import com.apps.project1024.ui.forums.ConnectFragment;
import com.apps.project1024.ui.forums.ElevateFragment;
import com.apps.project1024.ui.forums.EnterpriseFragment;
import com.apps.project1024.ui.forums.GrowFragment;

public class ForumsPagerAdapter extends FragmentStatePagerAdapter {
    private final static String[] PAGE_TITLES = {
            "ALL",
            "GROWTH",
            "ELEVATE",
            "EMPOWER",
            "KONNECT"
    };
    public ForumsPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new AllForumsFragment();
            case 1:
                return new GrowFragment();
            case 2:
                return new ElevateFragment();
            case 3:
                return new EnterpriseFragment();

            case 4:
                return new ConnectFragment();
            default: return null;
        }

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return PAGE_TITLES[0];
            case 1:
                return PAGE_TITLES[1];
            case 2:
                return PAGE_TITLES[2];
            case 3:
                return PAGE_TITLES[3];

            case 4:
                return PAGE_TITLES[4];
        }
        return super.getPageTitle(position);
    }

    @Override
    public int getCount() {
        return 5;
    }
}
