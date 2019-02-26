package com.framgia.chat_03.screen.chat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.framgia.chat_03.R;
import com.framgia.chat_03.data.model.Message;
import com.framgia.chat_03.data.model.User;
import com.framgia.chat_03.data.repository.MessageRepository;
import com.framgia.chat_03.data.repository.UserRepository;
import com.framgia.chat_03.data.source.local.UserLocalDataSource;
import com.framgia.chat_03.data.source.remote.MessageRemoteDataSource;
import com.framgia.chat_03.data.source.remote.UserRemoteDataSource;
import com.framgia.chat_03.screen.BaseActivity;
import com.framgia.chat_03.utils.ResizeWidthAnimation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends BaseActivity implements ChatContract.View,
        View.OnClickListener, ChatAdapter.OnItemClickListener {
    public static final String MESSAGE_TYPE_IMAGE = "image";
    public static final String MESSAGE_TYPE_TEXT = "text";
    public static final int PICK_IMAGE_FROM_GALLERY = 101;
    public static final int PICK_IMAGE_FROM_CAMERA = 102;
    public static final String TYPE_INTENT_PICK_IMAGE = "image/*";
    private static final String EXTRA_USER = "com.framgia.chat_03.extras.EXTRA_USER";
    private static final int DURATION_300 = 300;
    private static final int WIDTH_0 = 0;
    private ChatContract.Presenter mPresenter;
    private ProgressDialog mDialog;
    private EditText mTextMessage;
    private ImageView mImageAdd;
    private LinearLayout mLayoutCollapse;
    private int mWidthTextMessage;
    private int mWidthLayoutCollapse;
    private boolean isTextMessageZoomOut;
    private User mInteractUser;
    private User mCurrentUser;
    private List<Message> mMessages;
    private ChatAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private ImageView mImageAvatar;
    private ImageView mImageStateUser;
    private TextView mTextName;
    private TextView mTextLastOnline;
    private String mConversationId;

    public static Intent getIntent(Context context, User user) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(EXTRA_USER, user);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initComponent();
        initPresenter();
    }

    private void initComponent() {
        mDialog = new ProgressDialog(this);
        mInteractUser = getIntent().getParcelableExtra(EXTRA_USER);
        mTextMessage = findViewById(R.id.text_message);
        mTextName = findViewById(R.id.text_name);
        mTextLastOnline = findViewById(R.id.text_last_online);
        mLayoutCollapse = findViewById(R.id.linear_collapse);
        mImageAdd = findViewById(R.id.image_add);
        mImageAvatar = findViewById(R.id.image_avatar);
        mImageStateUser = findViewById(R.id.image_user_state);
        mRecyclerView = findViewById(R.id.recycler_message);
        mMessages = new ArrayList<>();
        mAdapter = new ChatAdapter(this, mInteractUser, mMessages);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mWidthTextMessage = getResources().getDimensionPixelOffset(R.dimen.dp_180);
        mWidthLayoutCollapse = getResources().getDimensionPixelOffset(R.dimen.dp_84);
        isTextMessageZoomOut = false;
        setupAnimationTextMessage();
        mImageAdd.setOnClickListener(this);
        findViewById(R.id.image_send).setOnClickListener(this);
        findViewById(R.id.image_photo).setOnClickListener(this);
        findViewById(R.id.image_camera).setOnClickListener(this);
        findViewById(R.id.image_back).setOnClickListener(this);
    }

    private void setupAnimationTextMessage() {
        mTextMessage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                zoomOutTextMessage();
                changeResourceImageAdd();
            }
        });
        mTextMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zoomOutTextMessage();
            }
        });
        mTextMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!isTextMessageZoomOut && s.length() > 0) {
                    zoomOutTextMessage();
                }
                if (TextUtils.isEmpty(s) || count == 0) {
                    zoomInTextMessage();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void changeResourceImageAdd() {
        if (!isTextMessageZoomOut) {
            mImageAdd.setImageResource(R.drawable.ic_add);
        } else {
            mImageAdd.setImageResource(R.drawable.ic_forward);
        }
    }

    private void zoomInTextMessage() {
        setChangeWidthAnimation(mTextMessage, mWidthTextMessage, DURATION_300);
        setChangeWidthAnimation(mLayoutCollapse, mWidthLayoutCollapse, DURATION_300);
        isTextMessageZoomOut = false;
        changeResourceImageAdd();
    }

    private void zoomOutTextMessage() {
        setChangeWidthAnimation(mTextMessage,
                mWidthTextMessage + mWidthLayoutCollapse, DURATION_300);
        setChangeWidthAnimation(mLayoutCollapse, WIDTH_0, DURATION_300);
        isTextMessageZoomOut = true;
        changeResourceImageAdd();
    }

    private void setChangeWidthAnimation(View view, int width, int duration) {
        ResizeWidthAnimation resizeWidthAnimation = new ResizeWidthAnimation(view, width);
        resizeWidthAnimation.setDuration(duration);
        view.clearAnimation();
        view.setAnimation(resizeWidthAnimation);
    }

    private void initPresenter() {
        UserRepository userRepository = new UserRepository(
                new UserLocalDataSource(PreferenceManager.getDefaultSharedPreferences(this)),
                new UserRemoteDataSource(FirebaseAuth.getInstance(),
                        FirebaseDatabase.getInstance(),
                        FirebaseStorage.getInstance()));
        MessageRepository messageRepository = new MessageRepository(new MessageRemoteDataSource(
                FirebaseAuth.getInstance(), FirebaseDatabase.getInstance()));
        mPresenter = new ChatPresenter(messageRepository, userRepository);
        mPresenter.setView(this);
        mPresenter.loadCurrentUser();
        mPresenter.loadInteractUser(mInteractUser.getUid());
        mPresenter.loadMessageFromDataBase(mInteractUser.getUid());
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    protected void onStop() {
        mPresenter.onStop();
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_add:
                if (isTextMessageZoomOut) {
                    zoomInTextMessage();
                }
                break;
            case R.id.image_send:
                break;
            case R.id.image_photo:
                break;
            case R.id.image_camera:
                break;
            case R.id.image_back:
                break;
        }
    }

    @Override
    public void showProgressDialog() {
        mDialog.setMessage(getString(R.string.login_loading));
        mDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        mDialog.dismiss();
    }

    @Override
    public void onGetMessagesSuccess(List<Message> messages) {
        mAdapter.addData(messages);
        if (messages.size() > 0) {
            mRecyclerView.smoothScrollToPosition(messages.size() - 1);
        }
    }

    @Override
    public void showInformationUser(User user) {
        try {
            mTextName.setText(user.getName());
            Glide.with(this)
                    .load(user.getImage())
                    .apply(RequestOptions.circleCropTransform())
                    .into(mImageAvatar);
            if (user.isOnline()) {
                mImageStateUser.setImageResource(R.drawable.bg_dot_online);
                mTextLastOnline.setText(getResources().getString(R.string.state_online));
            } else {
                mImageStateUser.setImageResource(R.drawable.bg_dot_offline);
                CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(user.getLastOnline(),
                        System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS);
                mTextLastOnline.setText(timeAgo);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUploadImageSuccess(String result) {
    }

    @Override
    public void onGetCurrentUserSuccess(User user) {
        mCurrentUser = user;
    }
}
