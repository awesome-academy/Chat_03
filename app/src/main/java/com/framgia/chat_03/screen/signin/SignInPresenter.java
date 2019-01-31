package com.framgia.chat_03.screen.signin;

final class SignInPresenter implements SignInContract.Presenter {
    private SignInContract.View mView;

    public SignInPresenter() {
    }

    @Override
    public void setView(SignInContract.View view) {
        mView = view;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }
}
