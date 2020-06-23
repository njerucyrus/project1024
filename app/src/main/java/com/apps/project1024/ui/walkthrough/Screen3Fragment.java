package com.apps.project1024.ui.walkthrough;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.project1024.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Screen3Fragment extends Fragment {

    public Screen3Fragment() {
        // Required empty public constructor
    }

    public static Screen3Fragment newInstance() {
        
        Bundle args = new Bundle();
        
        Screen3Fragment fragment = new Screen3Fragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_screen3, container, false);
    }
}
