package com.framgia.chat_03.data.repository;

import com.framgia.chat_03.data.AuthenticationDataSource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;

public class AuthenticationRepository implements AuthenticationDataSource.Remote {
    private AuthenticationDataSource.Remote mRemote;

    public AuthenticationRepository(AuthenticationDataSource.Remote remote) {
        mRemote = remote;
    }

    @Override
    public void login(String username, String password,
                      OnCompleteListener onCompleteListener,
                      OnFailureListener onFailureListener) {
        mRemote.login(username, password, onCompleteListener, onFailureListener);
    }

    @Override
    public void signUp(String fullName, String email, String password,
                       OnCompleteListener onCompleteListener,
                       OnFailureListener onFailureListener) {
        mRemote.signUp(fullName, email, password, onCompleteListener, onFailureListener);
    }

    @Override
    public void signOut() {
        mRemote.signOut();
    }
}
