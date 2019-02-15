package com.framgia.chat_03.screen.signup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.framgia.chat_03.R;
import com.framgia.chat_03.data.model.User;
import com.framgia.chat_03.data.repository.AuthenticationRepository;
import com.framgia.chat_03.data.repository.UserRepository;
import com.framgia.chat_03.data.source.local.UserLocalDataSource;
import com.framgia.chat_03.data.source.remote.AuthenticationRemoteDataSource;
import com.framgia.chat_03.data.source.remote.UserRemoteDataSource;
import com.framgia.chat_03.screen.BaseActivity;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends BaseActivity implements SignUpContract.View, View.OnClickListener {
    public static final int PICK_IMAGE_FROM_GALLERY = 101;
    public static final String TYPE_INTENT_PICK_IMAGE = "image/*";
    private EditText mEditTextFullName;
    private EditText mEditTextEmail;
    private EditText mEditTextPassword;
    private EditText mEditTextConfirmPassword;
    private ProgressDialog mDialogLogin;
    private SignUpContract.Presenter mPresenter;
    private ImageView mImageViewAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initPresenter();
        initViews();
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
            case R.id.image_back:
                onBackPressed();
                break;
            case R.id.button_sign_up:
                String fullName = mEditTextFullName.getText().toString().trim();
                String email = mEditTextEmail.getText().toString().trim();
                String password = mEditTextPassword.getText().toString().trim();
                String confirmPassword = mEditTextConfirmPassword.getText().toString().trim();
                mPresenter.signUp(fullName, email, password, confirmPassword);
                break;
            case R.id.image_avatar:
                chooseImageFromGallery();
                break;
            default:
                break;
        }
    }

    @Override
    public void showProgressDialog() {
        mDialogLogin.setMessage(getString(R.string.login_loading));
        mDialogLogin.show();
    }

    @Override
    public void hideProgressDialog() {
        mDialogLogin.dismiss();
    }

    @Override
    public void onEmptyFullName() {
        Toast.makeText(this, R.string.signup_empty_fullname, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEmptyEmail() {
        Toast.makeText(this, R.string.signup_empty_email, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEmptyPassWord() {
        Toast.makeText(this, R.string.signup_empty_password, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEmptyConfirmPassword() {
        Toast.makeText(this, R.string.signup_empty_confirm_password, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConfirmPasswordError() {
        Toast.makeText(this, R.string.signup_confirm_password_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onWeakPassword() {
        Toast.makeText(this, R.string.signup_weak_password, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void saveUser() {
        String email = mEditTextEmail.getText().toString().trim();
        String password = mEditTextPassword.getText().toString().trim();
        String fullName = mEditTextFullName.getText().toString().trim();
        User user = new User(email, fullName, password, null);
        mPresenter.saveUser(user);
    }

    @Override
    public void startHomeScreen() {

    }

    public void chooseImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(TYPE_INTENT_PICK_IMAGE);
        startActivityForResult(intent, PICK_IMAGE_FROM_GALLERY);
    }

    @Override
    public void setImageFromGallery(Uri pathImage) {
        Glide.with(this)
                .load(pathImage)
                .apply(RequestOptions.circleCropTransform())
                .into(mImageViewAvatar);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.setImageAvatar(requestCode, resultCode, data);
    }

    private void initViews() {
        mPresenter.setView(this);
        mDialogLogin = new ProgressDialog(this);
        mEditTextFullName = findViewById(R.id.text_full_name);
        mEditTextEmail = findViewById(R.id.text_email);
        mEditTextPassword = findViewById(R.id.text_password);
        mEditTextConfirmPassword = findViewById(R.id.text_re_password);
        mImageViewAvatar = findViewById(R.id.image_avatar);
        initComponent();
    }

    private void initComponent() {
        findViewById(R.id.image_back).setOnClickListener(this);
        findViewById(R.id.button_sign_up).setOnClickListener(this);
        mImageViewAvatar.setOnClickListener(this);
    }

    private void initPresenter() {
        AuthenticationRepository authenticationRepository = new AuthenticationRepository(
                new AuthenticationRemoteDataSource(FirebaseAuth.getInstance()));
        UserRepository userRepository = new UserRepository(
                new UserLocalDataSource(PreferenceManager.getDefaultSharedPreferences(this)),
                new UserRemoteDataSource(FirebaseAuth.getInstance()));
        mPresenter = new SignUpPresenter(authenticationRepository, userRepository);
    }
}
