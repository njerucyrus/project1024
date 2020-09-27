package com.apps.project1024.adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.project1024.R;
import com.apps.project1024.databinding.ForumPostBinding;
import com.apps.project1024.interfaces.RecyclerItemClickListener;
import com.apps.project1024.models.ForumPost;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ForumPostAdapter extends RecyclerView.Adapter<ForumPostAdapter.ViewHolder> {
    private Context context;
    private List<ForumPost> posts = new ArrayList<>();
    private RecyclerItemClickListener<ForumPost> listener;

    public ForumPostAdapter(Context context, RecyclerItemClickListener<ForumPost> listener) {
        this.context = context;
        this.listener = listener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ForumPostBinding binding = ForumPostBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ForumPostAdapter.ViewHolder(binding);
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ForumPost post = posts.get(position);
        holder.mBinding.getRoot().setOnClickListener(view -> {
            listener.onItemClicked(post);

        });
        holder.mBinding.tvTitle.setText(post.getTitle());
        if (post.getPhotoUrl() !=null) {
            Glide.with(context).load(post.getPhotoUrl())

                    .apply(RequestOptions.circleCropTransform())
                    .into(holder.mBinding.forumPostImage);
        }
        holder.mBinding.tvBody.setText(post.getBody());
        holder.mBinding.btnMore.setOnClickListener(this::showMoreOptions);
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void showMoreOptions(View view) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.inflate(R.menu.forum_options_menu);
        popupMenu.setOnMenuItemClickListener(menuItem -> {
            final int id = menuItem.getItemId();
            if (id == R.id.action_view) {
                //view here
            } else if (id == R.id.action_share) {
                //share here
            } else if (id == R.id.action_bookmark) {
                //bookmark for later here
            }

            return false;
        });
        setForceShowIcon(popupMenu);
        popupMenu.show();

    }

    public static void setForceShowIcon(PopupMenu popupMenu) {
        try {
            Field[] fields = popupMenu.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popupMenu);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper
                            .getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod(
                            "setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    static final class ViewHolder extends RecyclerView.ViewHolder {
        ForumPostBinding mBinding;

        public ViewHolder(ForumPostBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }
    }

    public void setPosts(List<ForumPost> posts) {
        this.posts = posts;
        notifyDataSetChanged();

    }
}
