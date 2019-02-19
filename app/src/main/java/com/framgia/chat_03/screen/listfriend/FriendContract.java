package com.framgia.chat_03.screen.listfriend;

import com.framgia.chat_03.data.model.User;
import com.framgia.chat_03.screen.BasePresenter;
import com.framgia.chat_03.screen.BaseView;

import java.util.List;

interface FriendContract {
    interface View extends BaseView {
        void onGetUsersSuccess(List<User> users);
    }

    interface Presenter extends BasePresenter<View> {
        void getUserFromDatabase();
    }
}
