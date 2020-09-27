package com.apps.project1024.ui.articles;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.apps.project1024.adapters.ArticlesAdapter;
import com.apps.project1024.databinding.FragmentArticlesBinding;
import com.apps.project1024.viewmodels.ArticlesViewModel;

import static com.apps.project1024.utils.Utils.displayErrorMessage;
import static com.apps.project1024.utils.Utils.displaySuccessMessage;


public class ArticlesFragment extends Fragment {

    private ArticlesAdapter articlesAdapter;
    private FragmentArticlesBinding binding;
    private ArticlesViewModel mViewModel;

    public ArticlesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        articlesAdapter = new ArticlesAdapter(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentArticlesBinding.inflate(inflater, container, false);

        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(ArticlesViewModel.class);
        loadData();
    }

    private void loadData() {

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerView.setAdapter(articlesAdapter);
        binding.recyclerView.setHasFixedSize(true);

        mViewModel.fetchArticles().observe(getViewLifecycleOwner(), articles -> {

            if (articles.size() > 0) {
                articlesAdapter.setArticles(articles);
                binding.tvNoData.setVisibility(View.GONE);
            } else {
                binding.progressBar.setVisibility(View.VISIBLE);
            }

        });


        mViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading->{
            if (isLoading) {
                binding.progressBar.setVisibility(View.VISIBLE);
            } else {
                binding.progressBar.setVisibility(View.GONE);
            }
        });

        mViewModel.getSuccessMsg().observe(getViewLifecycleOwner(), s->{
            if (s.isEmpty()) {
           displaySuccessMessage(requireContext(), s);
            } else {
                binding.progressBar.setVisibility(View.GONE);
            }
        });

        mViewModel.getErrorMsg().observe(getViewLifecycleOwner(), s->{
            if (s.isEmpty()) {
                displayErrorMessage(requireContext(), s);
            } else {
                binding.progressBar.setVisibility(View.GONE);
            }
        });


    }
}