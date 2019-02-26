package com.framgia.chat_03.data.repository;

import android.net.Uri;

import com.framgia.chat_03.data.UserDataSource;
import com.framgia.chat_03.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;

public class UserRepository implements UserDataSource.Local, UserDataSource.Remote {
    private UserDataSource.Local mLocal;
    private UserDataSource.Remote mRemote;

    public UserRepository(UserDataSource.Local local, UserDataSource.Remote remote) {
        mLocal = local;
        mRemote = remote;
    }

    @Override
    public void saveUserToSharePref(User user) {
        mLocal.saveUserToSharePref(user);
    }

    @Override
    public User getUser() {
        return mLocal.getUser();
    }

    @Override
    public void saveUserToFireBase(User user,
                                   OnCompleteListener onCompleteListener,
                                   OnFailureListener onFailureListener) {
        mRemote.saveUserToFireBase(user, onCompleteListener, onFailureListener);
    }

    @Override
    public void uploadImage(Uri file, OnCompleteListener onCompleteListener,
                            OnFailureListener onFailureListener) {
        mRemote.uploadImage(file, onCompleteListener, onFailureListener);
    }

    @Override
    public void uploadByteImage(ByteArrayOutputStream bytes,
                                OnCompleteListener onCompleteListener
            , OnFailureListener onFailureListener) {
        mRemote.uploadByteImage(bytes, onCompleteListener, onFailureListener);
    }

    @Override
    public void getImageUrl(OnCompleteListener onCompleteListener) {
        mRemote.getImageUrl(onCompleteListener);
    }

    @Override
    public void getUsersFromDataBase(ValueEventListener valueEventListener) {
        mRemote.getUsersFromDataBase(valueEventListener);
    }

    @Override
    public void getCurrentUserFromDataBase(ValueEventListener valueEventListener) {
        mRemote.getCurrentUserFromDataBase(valueEventListener);
    }

    @Override
    public void updateUserInformation(User currentUser, OnCompleteListener onCompleteListener,
                                      OnFailureListener onFailureListener) {
        mRemote.updateUserInformation(currentUser, onCompleteListener, onFailureListener);
    }

    @Override
    public void getUserFromDataBase(String uid, ValueEventListener valueEventListener) {
        mRemote.getUserFromDataBase(uid, valueEventListener);
    }

    @Override
    public void changeUserState(boolean isOnline) {
        mRemote.changeUserState(isOnline);
    }
}
