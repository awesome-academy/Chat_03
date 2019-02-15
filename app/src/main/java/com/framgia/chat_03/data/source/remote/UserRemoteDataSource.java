package com.framgia.chat_03.data.source.remote;

import com.framgia.chat_03.data.UserDataSource;
import com.framgia.chat_03.data.model.User;
import com.google.firebase.auth.FirebaseAuth;

public class UserRemoteDataSource implements UserDataSource.Remote {
    private FirebaseAuth mAuth;

    public UserRemoteDataSource(FirebaseAuth auth) {
        mAuth = auth;
    }

    @Override
    public void saveUserToFireBase(User user) {

    }
}
