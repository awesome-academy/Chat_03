package com.framgia.chat_03.screen.signin;

import com.framgia.chat_03.screen.BasePresenter;
import com.framgia.chat_03.screen.BaseView;

interface SignInContract {

    interface View extends BaseView {
        void showProgressDiaglog();

        void hideProgressDiaglog();

        void startHomeScreen();

        void onLoginAccountEmpty();

        void onLoginInvalidUser();
    }

    interface Presenter extends BasePresenter<View> {
        void login(String username, String password);
    }
}
