package com.framgia.chat_03.screen.editprofile;

import android.content.Intent;
import android.net.Uri;

import com.framgia.chat_03.data.model.User;
import com.framgia.chat_03.screen.BasePresenter;
import com.framgia.chat_03.screen.BaseView;

interface EditProfileContract {
    interface View extends BaseView {
        void showProgressDialog();

        void hideProgressDialog();

        void showProfile(User currentUser);

        void onEmptyFullName();

        void onEmptyEmail();

        void onEmptyStatus();

        void setImageFromGallery(Uri data);

        void showError(String message);

        void onUpdateUserSuccess();
    }

    interface Presenter extends BasePresenter<View> {
        void loadCurrentUserProfile();

        void updateUserProfile(String fullName, String email, String status);

        void setImageAvatar(int requestCode, int resultCode, Intent data);
    }
}
