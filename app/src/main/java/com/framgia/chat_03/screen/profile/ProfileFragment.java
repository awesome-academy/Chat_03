package com.framgia.chat_03.screen.profile;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.framgia.chat_03.R;
import com.framgia.chat_03.data.model.User;
import com.framgia.chat_03.data.repository.UserRepository;
import com.framgia.chat_03.data.source.local.UserLocalDataSource;
import com.framgia.chat_03.data.source.remote.UserRemoteDataSource;
import com.framgia.chat_03.screen.BaseFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class ProfileFragment extends BaseFragment implements ProfileContract.View {
    private ProfileContract.Presenter mPresenter;
    private ImageView mImageBackgound;
    private ImageView mImageAvatar;
    private TextView mTextName;
    private TextView mTextStatus;
    private TextView mTextEmail;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponents();
    }

    private void initComponents() {
        mImageBackgound = getView().findViewById(R.id.image_background);
        mImageAvatar = getView().findViewById(R.id.image_avatar);
        mTextName = getView().findViewById(R.id.text_user_name);
        mTextStatus = getView().findViewById(R.id.text_status);
        mTextEmail = getView().findViewById(R.id.text_user_email);
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
        super.onStop();
    }

    private void initPresenter() {
        UserRepository userRepository = new UserRepository(new UserLocalDataSource(PreferenceManager
                .getDefaultSharedPreferences(getContext())),
                new UserRemoteDataSource(FirebaseAuth.getInstance(),
                        FirebaseDatabase.getInstance(),
                        FirebaseStorage.getInstance()));
        mPresenter = new ProfilePresenter(userRepository);
        mPresenter.setView(this);
        mPresenter.loadCurrentUser();
    }

    @Override
    public void showCurrentUserProfile(User currentUser) {
        Glide.with(this)
                .load(currentUser.getImage())
                .apply(RequestOptions.circleCropTransform())
                .into(mImageAvatar);
        Glide.with(this)
                .load(currentUser.getImage())
                .into(mImageBackgound);
        mTextName.setText(currentUser.getName());
        mTextStatus.setText(currentUser.getStatus());
        mTextEmail.setText(currentUser.getEmail());
    }
}
