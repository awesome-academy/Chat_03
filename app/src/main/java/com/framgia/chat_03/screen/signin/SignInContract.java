package com.framgia.chat_03.screen.signin;

import com.framgia.chat_03.screen.BasePresenter;
import com.framgia.chat_03.screen.BaseView;

interface SignInContract {

    interface View extends BaseView {
    }

    interface Presenter extends BasePresenter<View> {
    }
}
