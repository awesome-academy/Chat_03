package com.framgia.chat_03.screen.home;

public class HomePresenter implements HomeContract.Presenter {
    private HomeContract.View mView;

    public HomePresenter() {
    }

    @Override
    public void setView(HomeContract.View view) {
        mView = view;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }
}
