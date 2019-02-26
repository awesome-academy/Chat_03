package com.framgia.chat_03.screen.chat;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.framgia.chat_03.R;
import com.framgia.chat_03.data.model.Message;
import com.framgia.chat_03.data.model.User;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import static com.framgia.chat_03.screen.chat.ChatActivity.MESSAGE_TYPE_IMAGE;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private static final int SENT_TEXT_MESSAGE = 0;
    private static final int RECEIVED_TEXT_MESSAGE = 1;
    private static final int SENT_IMAGE_MESSAGE = 2;
    private static final int RECEIVED_IMAGE_MESSAGE = 3;
    private static final int ROUNDING_RADIUS = 20;
    private Context mContext;
    private List<Message> mMessages;
    private LayoutInflater mLayoutInflater;
    private OnItemClickListener mOnItemClickListener;
    private User mUser;

    public ChatAdapter(Context context, User user, List<Message> messages) {
        mContext = context;
        mMessages = messages;
        mUser = user;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = null;
        switch (i) {
            case SENT_TEXT_MESSAGE:
                view = mLayoutInflater.inflate(R.layout.item_message_send, viewGroup, false);
                break;
            case RECEIVED_TEXT_MESSAGE:
                view = mLayoutInflater.inflate(R.layout.item_message_receive, viewGroup, false);
                break;
            case SENT_IMAGE_MESSAGE:
                view = mLayoutInflater.inflate(R.layout.item_image_send, viewGroup, false);
                break;
            case RECEIVED_IMAGE_MESSAGE:
                view = mLayoutInflater.inflate(R.layout.item_image_receive, viewGroup, false);
                break;
        }
        return new ViewHolder(view, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bindView(mContext, mMessages.get(i), mUser);
    }

    @Override
    public int getItemCount() {
        return mMessages != null ? mMessages.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (mMessages.get(position).getFromId().equals(FirebaseAuth.getInstance().getUid())) {
            if (mMessages.get(position).getType().equals(MESSAGE_TYPE_IMAGE)) {
                return SENT_IMAGE_MESSAGE;
            } else {
                return SENT_TEXT_MESSAGE;
            }
        } else {
            if (mMessages.get(position).getType().equals(MESSAGE_TYPE_IMAGE)) {
                return RECEIVED_IMAGE_MESSAGE;
            } else {
                return RECEIVED_TEXT_MESSAGE;
            }
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void addData(List<Message> messages) {
        if (messages == null) {
            return;
        }
        mMessages.clear();
        mMessages.addAll(messages);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mImageAvatar;
        private ImageView mImageMessage;
        private TextView mTextMess;
        private ProgressBar mProgressBar;

        ViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            mImageAvatar = itemView.findViewById(R.id.image_avatar);
            mImageMessage = itemView.findViewById(R.id.image_message);
            mTextMess = itemView.findViewById(R.id.text_message);
            mProgressBar = itemView.findViewById(R.id.progress_bar);
        }

        void bindView(Context context, Message message, User user) {
            switch (getItemViewType()) {
                case SENT_TEXT_MESSAGE:
                    mTextMess.setText(message.getContent());
                    break;
                case RECEIVED_TEXT_MESSAGE:
                    showAvatar(context, user.getImage());
                    mTextMess.setText(message.getContent());
                    break;
                case SENT_IMAGE_MESSAGE:
                    showImage(context, message.getContent());
                    break;
                case RECEIVED_IMAGE_MESSAGE:
                    showAvatar(context, user.getImage());
                    showImage(context, message.getContent());
                    break;
            }
        }

        private void showAvatar(Context context, String url) {
            Glide.with(context)
                    .load(url)
                    .apply(RequestOptions.circleCropTransform())
                    .into(mImageAvatar);
        }

        private void showImage(Context context, String url) {
            mProgressBar.setVisibility(View.VISIBLE);
            RequestOptions requestOptions = new RequestOptions();
            Glide.with(context).load(url).apply(requestOptions.transforms(new CenterCrop(),
                    new RoundedCorners(ROUNDING_RADIUS))).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e,
                                            Object model, Target<Drawable> target,
                                            boolean isFirstResource) {
                    mProgressBar.setVisibility(View.GONE);
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource,
                                               Object model, Target<Drawable> target,
                                               DataSource dataSource, boolean isFirstResource) {
                    mProgressBar.setVisibility(View.GONE);
                    return false;
                }
            }).into(mImageMessage);
        }

        @Override
        public void onClick(View v) {
        }
    }
}

