package com.framgia.chat_03.data.repository;

import com.framgia.chat_03.data.UserDataSource;
import com.framgia.chat_03.data.model.User;

public class UserRepository implements UserDataSource.Local, UserDataSource.Remote {
    private UserDataSource.Local mLocal;
    private UserDataSource.Remote mRemote;

    public UserRepository(UserDataSource.Local local, UserDataSource.Remote remote) {
        mLocal = local;
        mRemote = remote;
    }

    @Override
    public void saveUser(User user) {
        mLocal.saveUser(user);
    }

    @Override
    public User getUser() {
        return mLocal.getUser();
    }

    @Override
    public void saveUserToFireBase(User user) {
        mRemote.saveUserToFireBase(user);
    }
}
