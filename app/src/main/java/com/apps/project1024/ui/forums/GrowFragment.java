package com.apps.project1024.ui.forums;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.project1024.R;
import com.apps.project1024.adapters.ForumPostAdapter;
import com.apps.project1024.databinding.FragmentGrowBinding;
import com.apps.project1024.interfaces.RecyclerItemClickListener;
import com.apps.project1024.models.ForumPost;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GrowFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GrowFragment extends Fragment implements RecyclerItemClickListener<ForumPost> {

    private FragmentGrowBinding mBinding;
    public GrowFragment() {
        // Required empty public constructor
    }


    public static GrowFragment newInstance() {
        GrowFragment fragment = new GrowFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentGrowBinding.inflate(inflater, container, false);
        loadData();
        return mBinding.getRoot();
    }
    private void loadData() {
        List<ForumPost> posts = new ArrayList<>();
        posts.add(new ForumPost());
        posts.add(new ForumPost());
        posts.add(new ForumPost());
        posts.add(new ForumPost());
        posts.add(new ForumPost());

        ForumPostAdapter adapter = new ForumPostAdapter(requireContext(), this);
        RecyclerView recyclerView = mBinding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        adapter.setPosts(posts);
    }

    @Override
    public void onItemClicked(ForumPost item) {

    }
}