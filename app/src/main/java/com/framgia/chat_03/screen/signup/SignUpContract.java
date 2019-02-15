package com.framgia.chat_03.screen.signup;

import android.content.Intent;
import android.net.Uri;

import com.framgia.chat_03.data.model.User;
import com.framgia.chat_03.screen.BasePresenter;
import com.framgia.chat_03.screen.BaseView;

interface SignUpContract {
    interface View extends BaseView {
        void showProgressDialog();

        void hideProgressDialog();

        void onEmptyFullName();

        void onEmptyEmail();

        void onEmptyPassWord();

        void onEmptyConfirmPassword();

        void onConfirmPasswordError();

        void onWeakPassword();

        void saveUser();

        void startHomeScreen();

        void setImageFromGallery(Uri data);
    }

    interface Presenter extends BasePresenter<View> {
        void signUp(String fullName, String email,
                    String password, String confirmPassword);

        void saveUser(User user);

        void setImageAvatar(int requestCode, int resultCode, Intent data);
    }
}
