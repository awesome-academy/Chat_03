package com.framgia.chat_03.screen.listmessage;

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
import com.framgia.chat_03.data.model.Message;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private Context mContext;
    private List<Message> mMessages;
    private LayoutInflater mLayoutInflater;
    private OnItemClickListener mOnItemClickListener;

    public MessageAdapter(Context context, List<Message> messages) {
        mContext = context;
        mMessages = messages;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mLayoutInflater.inflate(R.layout.item_list_message, viewGroup, false);
        return new ViewHolder(view, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bindView(mContext, mMessages.get(i));
    }

    @Override
    public int getItemCount() {
        return mMessages != null ? mMessages.size() : 0;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(Message message);
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mImageAvatar;
        private ImageView mImageUserState;
        private TextView mTextName;
        private TextView mTextLastMessage;
        private TextView mTextLastOnline;
        private OnItemClickListener mOnItemClickListener;
        private Message mMessage;

        ViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            mImageAvatar = itemView.findViewById(R.id.image_avatar);
            mImageUserState = itemView.findViewById(R.id.image_user_state);
            mTextName = itemView.findViewById(R.id.text_name);
            mTextLastMessage = itemView.findViewById(R.id.text_last_message);
            mTextLastOnline = itemView.findViewById(R.id.text_last_online);
            mOnItemClickListener = onItemClickListener;
        }

        void bindView(Context context, Message message) {
            mMessage = message;
            mTextName.setText(message.getName());
            mTextLastMessage.setText(message.getLastMessage());
            Glide.with(context)
                    .load(message.getAvatar())
                    .apply(RequestOptions.circleCropTransform())
                    .into(mImageAvatar);
            mTextLastOnline.setText(message.getLastOnline());
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(mMessage);
            }
        }
    }
}
