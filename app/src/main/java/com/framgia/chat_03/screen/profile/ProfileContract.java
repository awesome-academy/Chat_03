package com.framgia.chat_03.screen.profile;

import com.framgia.chat_03.data.model.User;
import com.framgia.chat_03.screen.BasePresenter;
import com.framgia.chat_03.screen.BaseView;

interface ProfileContract {
    interface View extends BaseView {
        void showCurrentUserProfile(User currentUser);
    }

    interface Presenter extends BasePresenter<View> {
        void loadCurrentUser();
    }
}
