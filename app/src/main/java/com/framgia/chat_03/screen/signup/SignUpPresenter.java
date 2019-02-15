package com.framgia.chat_03.screen.signup;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.framgia.chat_03.data.model.User;
import com.framgia.chat_03.data.repository.AuthenticationRepository;
import com.framgia.chat_03.data.repository.UserRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

final class SignUpPresenter implements SignUpContract.Presenter, OnFailureListener, OnCompleteListener {
    private SignUpContract.View mView;
    private AuthenticationRepository mAuthenticationRepository;
    private UserRepository mUserRepository;

    public SignUpPresenter(AuthenticationRepository authenticationRepository, UserRepository userRepository) {
        mAuthenticationRepository = authenticationRepository;
        mUserRepository = userRepository;
    }

    @Override
    public void setView(SignUpContract.View view) {
        mView = view;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void signUp(String fullName, String email,
                       String password, String confirmPassword) {
        if (isValidateData(fullName, email, password, confirmPassword)) {
            mAuthenticationRepository.signUp(fullName, email, password,
                    this, this);
            mView.showProgressDialog();
        }
    }

    @Override
    public void saveUser(User user) {
        mUserRepository.saveUser(user);
    }

    @Override
    public void setImageAvatar(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            if (requestCode == SignUpActivity.PICK_IMAGE_FROM_GALLERY) {
                pickImageFromGallery(data);
            }
        }
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        if (e instanceof FirebaseAuthWeakPasswordException) {
            mView.onWeakPassword();
        }
    }

    @Override
    public void onComplete(@NonNull Task task) {
        mView.hideProgressDialog();
        if (!task.isSuccessful()) {
            return;
        }
        mView.saveUser();
        mView.startHomeScreen();
    }

    private void pickImageFromGallery(Intent data) {
        mView.setImageFromGallery(data.getData());
    }

    private boolean isValidateData(String fullName, String email, String password, String confirmPassword) {
        mView.hideProgressDialog();
        if (TextUtils.isEmpty(fullName)) {
            mView.onEmptyFullName();
            return false;
        }
        if (TextUtils.isEmpty(email)) {
            mView.onEmptyEmail();
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            mView.onEmptyPassWord();
            return false;
        }
        if (TextUtils.isEmpty(confirmPassword)) {
            mView.onEmptyConfirmPassword();
            return false;
        }
        if (!password.equals(confirmPassword)) {
            mView.onConfirmPasswordError();
            return false;
        }
        return true;
    }
}
