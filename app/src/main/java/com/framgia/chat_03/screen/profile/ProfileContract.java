package com.framgia.chat_03.screen.profile;

import com.framgia.chat_03.screen.BasePresenter;
import com.framgia.chat_03.screen.BaseView;

interface ProfileContract {
    interface View extends BaseView {
    }

    interface Presenter extends BasePresenter<View> {
    }
}
