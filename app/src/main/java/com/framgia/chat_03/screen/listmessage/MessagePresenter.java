package com.framgia.chat_03.screen.listmessage;

public class MessagePresenter implements MessageContract.Presenter {
    private final MessageContract.View mView;

    public MessagePresenter(MessageContract.View view) {
        mView = view;
    }

    @Override
    public void setView(Object view) {
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }
}
