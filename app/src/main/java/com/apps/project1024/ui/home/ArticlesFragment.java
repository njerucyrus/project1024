package com.apps.project1024.ui.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTabHost;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.project1024.databinding.FragmentArticlesBinding;


public class ArticlesFragment extends Fragment {
private FragmentArticlesBinding binding;
    public ArticlesFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentArticlesBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }
}