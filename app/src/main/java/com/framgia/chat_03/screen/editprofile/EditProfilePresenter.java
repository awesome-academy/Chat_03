package com.framgia.chat_03.screen.editprofile;

final class EditProfilePresenter implements EditProfileContract.Presenter {
    private EditProfileContract.View mView;

    public EditProfilePresenter() {
    }

    @Override
    public void setView(EditProfileContract.View view) {
        mView = view;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }
}
