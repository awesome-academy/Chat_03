package com.framgia.chat_03.screen.signup;

import com.framgia.chat_03.screen.BasePresenter;
import com.framgia.chat_03.screen.BaseView;

interface SignUpContract {
    interface View extends BaseView {
    }

    interface Presenter extends BasePresenter<View> {
    }
}