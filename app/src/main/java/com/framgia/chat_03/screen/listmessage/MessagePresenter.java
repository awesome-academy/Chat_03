package com.framgia.chat_03.screen.listmessage;

public class MessagePresenter implements MessageContract.Presenter {
    private MessageContract.View mView;

    public MessagePresenter() {

    }

    @Override
    public void setView(MessageContract.View view) {
        mView = view;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }
}
