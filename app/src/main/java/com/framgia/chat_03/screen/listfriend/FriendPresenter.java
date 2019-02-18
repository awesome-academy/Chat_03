package com.framgia.chat_03.screen.listfriend;

public class FriendPresenter implements FriendContract.Presenter {
    private FriendContract.View mView;

    public FriendPresenter() {

    }

    @Override
    public void setView(FriendContract.View view) {
        mView = view;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }
}
