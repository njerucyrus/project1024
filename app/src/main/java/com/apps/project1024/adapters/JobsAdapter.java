package com.apps.project1024.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.project1024.databinding.JobItemLayoutBinding;
import com.apps.project1024.models.Job;

import java.util.ArrayList;
import java.util.List;

public class JobsAdapter extends RecyclerView.Adapter<JobsAdapter.ViewHolder> {

    private List<Job> jobs = new ArrayList<>();

    public JobsAdapter(Context context) {
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        JobItemLayoutBinding binding = JobItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new JobsAdapter.ViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Job job = jobs.get(position);
        holder.mBinding.tvJobTitle.setText(job.getTitle());
        holder.mBinding.tvCompany.setText(job.getCompany());
        holder.mBinding.tvDescription.setText(job.getDescription());
        holder.mBinding.tvCategory.setText(String.format("Job Category: %s", job.getCategory()));
        String jobType = job.isInternship()? "Internship": "FullTime";
        holder.mBinding.tvLocation.setText(job.getLocation()+"|"+jobType+"|"+"Ksh Confidential");
        holder.mBinding.tvDate.setText(job.getTimeAgo());
    }

    @Override
    public int getItemCount() {
        return jobs.size();
    }

    public static final class ViewHolder extends RecyclerView.ViewHolder {
        JobItemLayoutBinding mBinding;

        public ViewHolder(JobItemLayoutBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }
    }

    public void setData(List<Job> jobs) {
        this.jobs = jobs;
        notifyDataSetChanged();
    }
}
