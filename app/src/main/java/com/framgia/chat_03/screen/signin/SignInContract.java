package com.framgia.chat_03.screen.signin;

import com.framgia.chat_03.data.model.User;
import com.framgia.chat_03.screen.BasePresenter;
import com.framgia.chat_03.screen.BaseView;

interface SignInContract {
    interface View extends BaseView {
        void showProgressDiaglog();

        void hideProgressDiaglog();

        void startHomeScreen();

        void onLoginAccountEmpty();

        void onLoginInvalidUser();

        void fillEmailAndPassword(User user);
    }

    interface Presenter extends BasePresenter<View> {
        void login(String username, String password);

        void getUser();
    }
}
