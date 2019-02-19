package com.framgia.chat_03.screen.home;

import com.framgia.chat_03.data.model.User;
import com.framgia.chat_03.data.repository.UserRepository;

public class HomePresenter implements HomeContract.Presenter {
    private HomeContract.View mView;
    private UserRepository mUserRepository;

    public HomePresenter(UserRepository userRepository) {
        mUserRepository = userRepository;
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

    @Override
    public void loadAvatarUrl() {
        User user = mUserRepository.getUser();
        if (isExitUser(user)) {
           mView.showAvatar(user.getImage());
        }
    }

    public boolean isExitUser(User user) {
        if (user == null) return false;
        return user.getEmail() != null || user.getPassword() != null;
    }
}
