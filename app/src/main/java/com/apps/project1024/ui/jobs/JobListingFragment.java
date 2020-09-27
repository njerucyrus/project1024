package com.apps.project1024.ui.jobs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.project1024.adapters.JobsAdapter;
import com.apps.project1024.databinding.FragmentJobListingBinding;
import com.apps.project1024.models.Job;
import com.apps.project1024.viewmodels.JobsViewModel;

import java.util.List;

import static com.apps.project1024.utils.Utils.displayErrorMessage;

public class JobListingFragment extends Fragment {

    private FragmentJobListingBinding mBinding;
    private JobsViewModel mViewModel;
    private JobsAdapter adapter;

    public JobListingFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new JobsAdapter(requireContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mBinding = FragmentJobListingBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(JobsViewModel.class);
        initRecyclerView();

        mViewModel.fetchJobs().observe(getViewLifecycleOwner(), jobs -> {
                    if (jobs.size() > 0) {
                        adapter.setData(jobs);
                    }
                    mBinding.progressBar.setVisibility(View.GONE);

                }
        );

        mViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) {
                mBinding.progressBar.setVisibility(View.VISIBLE);
            } else {
                mBinding.progressBar.setVisibility(View.GONE);

            }
        });

        mViewModel.getErrorMsg().observe(getViewLifecycleOwner(), errorMessage -> {
            if (!errorMessage.isEmpty()) {
                displayErrorMessage(requireContext(), errorMessage);
            }
        });

    }


    private void initRecyclerView() {

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        mBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        mBinding.recyclerView.setHasFixedSize(true);
        mBinding.recyclerView.setAdapter(adapter);
    }


}