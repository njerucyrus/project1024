package com.apps.project1024.ui.forums;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.project1024.R;
import com.apps.project1024.adapters.ForumPostAdapter;
import com.apps.project1024.databinding.FragmentElevateBinding;
import com.apps.project1024.interfaces.RecyclerItemClickListener;
import com.apps.project1024.models.ForumPost;
import com.apps.project1024.viewmodels.ForumsViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.apps.project1024.utils.Utils.displayErrorMessage;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ElevateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ElevateFragment extends Fragment implements RecyclerItemClickListener<ForumPost> {

private FragmentElevateBinding mBinding;

    private ForumsViewModel mViewModel;
    private ForumPostAdapter adapter;
    public ElevateFragment() {
        // Required empty public constructor
    }


    public static ElevateFragment newInstance() {
        ElevateFragment fragment = new ElevateFragment();

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ForumPostAdapter(requireContext(), this);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mBinding = FragmentElevateBinding.inflate(inflater, container, false);
      initRecyclerView();
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(ForumsViewModel.class);
        initRecyclerView();

        mViewModel.getElevatePosts().observe(getViewLifecycleOwner(), forumPosts -> {
                    if (forumPosts.size() > 0) {
                        adapter.setPosts(forumPosts);
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




    @Override
    public void onItemClicked(ForumPost item) {

    }
}