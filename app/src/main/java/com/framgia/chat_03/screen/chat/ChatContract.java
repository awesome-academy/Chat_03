package com.framgia.chat_03.screen.chat;

import com.framgia.chat_03.data.model.Message;
import com.framgia.chat_03.data.model.User;
import com.framgia.chat_03.screen.BasePresenter;
import com.framgia.chat_03.screen.BaseView;

import java.util.List;

interface ChatContract {
    interface View extends BaseView {
        void showProgressDialog();

        void hideProgressDialog();

        void onGetMessagesSuccess(List<Message> messages);

        void showInformationUser(User user);

        void onUploadImageSuccess(String result);

        void onGetCurrentUserSuccess(User user);
    }

    interface Presenter extends BasePresenter<View> {
        void loadMessageFromDataBase(String uid);

        void loadCurrentUser();

        void loadInteractUser(String uid);
    }
}
