package com.apps.project1024.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.project1024.databinding.ListItemBinding;

import java.net.ContentHandler;
import java.util.ArrayList;
import java.util.List;

public class BulletListAdapter extends RecyclerView.Adapter<BulletListAdapter.ViewHolder> {
    private List<String> dataList = new ArrayList<>();
    private Context context;

    public BulletListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemBinding mBinding = ListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new BulletListAdapter.ViewHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String text = dataList.get(position);
        holder.mBinding.listText.setText(text);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

     static final class ViewHolder extends RecyclerView.ViewHolder {
        ListItemBinding mBinding;
        public ViewHolder(ListItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }
    }

    public void setDataList(List<String> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }
}
