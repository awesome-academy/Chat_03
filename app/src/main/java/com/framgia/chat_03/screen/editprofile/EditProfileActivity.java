package com.framgia.chat_03.screen.editprofile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.framgia.chat_03.R;
import com.framgia.chat_03.data.model.User;
import com.framgia.chat_03.data.repository.UserRepository;
import com.framgia.chat_03.data.source.local.UserLocalDataSource;
import com.framgia.chat_03.data.source.remote.UserRemoteDataSource;
import com.framgia.chat_03.screen.BaseActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class EditProfileActivity extends BaseActivity
        implements EditProfileContract.View, View.OnClickListener {
    public static final int PICK_IMAGE_FROM_GALLERY = 197;
    public static final String TYPE_INTENT_PICK_IMAGE = "image/*";
    private EditProfileContract.Presenter mPresenter;
    private ProgressDialog mDialog;
    private ImageView mImageBackground;
    private ImageView mImageAvatar;
    private TextView mTextFullName;
    private TextView mTextStatus;
    private TextView mTextEmail;

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, EditProfileActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        initPresenter();
        initComponents();
        mPresenter.loadCurrentUserProfile();
    }

    private void initComponents() {
        mDialog = new ProgressDialog(this);
        mImageBackground = findViewById(R.id.image_background);
        mImageAvatar = findViewById(R.id.image_avatar);
        mTextFullName = findViewById(R.id.text_full_name);
        mTextStatus = findViewById(R.id.text_status);
        mTextEmail = findViewById(R.id.text_email);
        mImageAvatar.setOnClickListener(this);
        findViewById(R.id.image_back).setOnClickListener(this);
        findViewById(R.id.button_accept).setOnClickListener(this);
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
    public void showProgressDialog() {
        mDialog.setMessage(getString(R.string.login_loading));
        mDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        mDialog.dismiss();
    }

    private void initPresenter() {
        UserRepository userRepository = new UserRepository(new UserLocalDataSource(PreferenceManager
                .getDefaultSharedPreferences(this)),
                new UserRemoteDataSource(FirebaseAuth.getInstance(),
                        FirebaseDatabase.getInstance(),
                        FirebaseStorage.getInstance()));
        mPresenter = new EditProfilePresenter(userRepository);
        mPresenter.setView(this);
    }

    @Override
    public void showProfile(User currentUser) {
        Glide.with(this)
                .load(currentUser.getImage())
                .apply(RequestOptions.circleCropTransform())
                .into(mImageAvatar);
        Glide.with(this)
                .load(currentUser.getImage())
                .into(mImageBackground);
        mTextEmail.setText(currentUser.getEmail());
        mTextFullName.setText(currentUser.getName());
        mTextStatus.setText(currentUser.getStatus());
    }

    @Override
    public void onEmptyFullName() {
        Toast.makeText(this, R.string.error_empty_fullname, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEmptyEmail() {
        Toast.makeText(this, R.string.error_empty_email, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEmptyStatus() {
        Toast.makeText(this, R.string.error_empty_status, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setImageFromGallery(Uri data) {
        Glide.with(this)
                .load(data)
                .apply(RequestOptions.circleCropTransform())
                .into(mImageAvatar);
        Glide.with(this)
                .load(data)
                .into(mImageBackground);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateUserSuccess() {
        Toast.makeText(this, R.string.success_update_user, Toast.LENGTH_SHORT).show();
        onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_back:
                onBackPressed();
                break;
            case R.id.button_accept:
                String fullName = mTextFullName.getText().toString().trim();
                String email = mTextEmail.getText().toString().trim();
                String status = mTextStatus.getText().toString().trim();
                mPresenter.updateUserProfile(fullName, email, status);
                break;
            case R.id.image_avatar:
                chooseImageFromGallery();
                break;
        }
    }

    private void chooseImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(TYPE_INTENT_PICK_IMAGE);
        startActivityForResult(intent, PICK_IMAGE_FROM_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.setImageAvatar(requestCode, resultCode, data);
    }
}
