package com.apps.project1024.ui.walkthrough;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.apps.project1024.R;
import com.apps.project1024.ui.auth.LoginActivity;
import com.apps.project1024.ui.auth.WelcomeActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class Screen4Fragment extends Fragment {

    public Screen4Fragment() {
        // Required empty public constructor
    }

    public static Screen4Fragment newInstance() {
        
        Bundle args = new Bundle();
        
        Screen4Fragment fragment = new Screen4Fragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_screen4, container, false);
        Button btnGetStarted = v.findViewById(R.id.btn_get_started);
        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireActivity(), WelcomeActivity.class));
                requireActivity().finish();
            }
        });
        return v;
    }
}
