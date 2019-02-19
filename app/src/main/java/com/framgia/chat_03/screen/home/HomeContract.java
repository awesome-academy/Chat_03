package com.framgia.chat_03.screen.home;

import com.framgia.chat_03.screen.BasePresenter;
import com.framgia.chat_03.screen.BaseView;

interface HomeContract {
    interface View extends BaseView {
        void showAvatar(String image);
    }

    interface Presenter extends BasePresenter<View> {
        void loadAvatarUrl();
    }
}
