package com.apps.project1024.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.project1024.databinding.ChatItemLayoutBinding;
import com.apps.project1024.interfaces.RecyclerItemClickListener;
import com.apps.project1024.models.ChatMessage;
import com.apps.project1024.models.Conversation;
import com.apps.project1024.models.User;
import com.apps.project1024.ui.livechat.ChatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ConversationViewHolder> implements RecyclerItemClickListener<Conversation> {

    private Context mContext;
    private FirebaseUser mCurrentUser;
    private DatabaseReference mMessagesRef;
    private CollectionReference mUsersRef;
    private List<Conversation> conversationList;

    public ConversationAdapter(Context context, List<Conversation> conversationList) {
        this.mContext = context;
        this.conversationList = conversationList;
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        mMessagesRef = FirebaseDatabase.getInstance().getReference("messages").child(mCurrentUser.getUid());
        mUsersRef = FirebaseFirestore.getInstance().collection("users");

    }

    @NonNull
    @Override
    public ConversationAdapter.ConversationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        ChatItemLayoutBinding mBinding = ChatItemLayoutBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);

        return new ConversationAdapter.ConversationViewHolder(mBinding);
    }


    @Override
    public void onBindViewHolder(@NonNull ConversationViewHolder holder, int position) {
        Conversation conversation = getItemAt(position);

        String listUserId = conversation.getDocKey();

        if (listUserId != null) {
            Query lastMessageQuery = mMessagesRef.child(listUserId).limitToLast(1);
            lastMessageQuery.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    ChatMessage message = dataSnapshot.getValue(ChatMessage.class);
                    if (message != null) {
                        holder.mBinding.tvLastMessage.setText(message.getMessage());
                        holder.mBinding.messageTime.setText(message.formattedTime());
                    }
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            mUsersRef.document(listUserId).get().addOnSuccessListener(documentSnapshot -> {
                User user = documentSnapshot.toObject(User.class);
                if (user != null) {
                   // holder.mBinding.setUser(user);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("toUser", user);
                    holder.mBinding.chatLayout.setOnClickListener(view -> mContext.startActivity(new Intent(mContext, ChatActivity.class)
                            .putExtra("bundle", bundle)));
                }

            });
        }


    }

    @Override
    public int getItemCount() {
        return conversationList.size();
    }

    @Override
    public void onItemClicked(Conversation item) {

    }

    @Override
    public Conversation getItemAt(int position) {
        return conversationList.get(position);
    }

    @Override
    public void restoreItem(Conversation item, int position) {

    }

    public static class ConversationViewHolder extends RecyclerView.ViewHolder {

        public ChatItemLayoutBinding mBinding;

        ConversationViewHolder(ChatItemLayoutBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }


    }
}
