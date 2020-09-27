package com.apps.project1024.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.project1024.databinding.HealthItemBinding;
import com.apps.project1024.interfaces.RecyclerItemClickListener;
import com.apps.project1024.models.HealthMenuItem;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class HealthAdapter extends RecyclerView.Adapter<HealthAdapter.ViewHolder> {
    private Context context;
    private RecyclerItemClickListener<HealthMenuItem> listener;
    private List<HealthMenuItem> menuItems = new ArrayList<>();

    public HealthAdapter(Context context, RecyclerItemClickListener<HealthMenuItem> listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HealthItemBinding binding = HealthItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new HealthAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final HealthMenuItem item = menuItems.get(position);
        Glide.with(context).load(item.getResId()).into(holder.mBinding.imageView);
        holder.mBinding.tvTitle.setText(item.getTitle());
        holder.mBinding.getRoot().setOnClickListener(view -> {
            listener.onItemClicked(item);
        });
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    public static final class ViewHolder extends RecyclerView.ViewHolder{
        HealthItemBinding mBinding;
        public ViewHolder(HealthItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }
    }

    public void setMenuItems(List<HealthMenuItem> items) {
        this.menuItems = items;
        notifyDataSetChanged();
    }
}
