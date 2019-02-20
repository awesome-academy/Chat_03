package com.framgia.chat_03.screen;

public interface BasePresenter<T> {
    void setView(T view);

    void onStart();

    void onStop();
}
