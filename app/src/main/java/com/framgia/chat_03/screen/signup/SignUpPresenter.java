package com.framgia.chat_03.screen.signup;

final class SignUpPresenter implements SignUpContract.Presenter {
    private SignUpContract.View mView;

    public SignUpPresenter() {
    }

    @Override
    public void setView(SignUpContract.View view) {
        mView = view;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }
}
