package com.framgia.chat_03.screen.listfriend;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.framgia.chat_03.R;
import com.framgia.chat_03.data.model.User;

import java.util.List;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder> {
    private Context mContext;
    private List<User> mUsers;
    private LayoutInflater mLayoutInflater;
    private OnItemClickListener mOnItemClickListener;

    public FriendAdapter(Context context, List<User> users) {
        mContext = context;
        mUsers = users;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mLayoutInflater.inflate(R.layout.item_list_message, viewGroup, false);
        return new FriendAdapter.ViewHolder(view, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bindView(mContext, mUsers.get(i));
    }

    @Override
    public int getItemCount() {
        return mUsers != null ? mUsers.size() : 0;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(User user);
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mImageAvatar;
        private ImageView mImageUserState;
        private ImageView mImageChat;
        private ImageView mImageProfile;
        private TextView mTextName;
        private TextView mTextStatus;
        private OnItemClickListener mOnItemClickListener;
        private User mUser;

        ViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            mImageAvatar = itemView.findViewById(R.id.image_avatar);
            mImageUserState = itemView.findViewById(R.id.image_user_state);
            mImageChat = itemView.findViewById(R.id.image_chat);
            mImageProfile = itemView.findViewById(R.id.image_profile);
            mTextName = itemView.findViewById(R.id.text_name);
            mTextStatus = itemView.findViewById(R.id.text_user_status);
            mOnItemClickListener = onItemClickListener;
        }

        void bindView(Context context, User user) {
            mUser = user;
            mTextName.setText(user.getName());
            Glide.with(context)
                    .load(user.getImage())
                    .apply(RequestOptions.circleCropTransform())
                    .into(mImageAvatar);
            mTextStatus.setText(user.getStatus());
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(mUser);
            }
        }
    }
}
