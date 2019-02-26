package com.framgia.chat_03.data;

import android.net.Uri;

import com.framgia.chat_03.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;

public interface UserDataSource {
    interface Local {
        void saveUserToSharePref(User user);

        User getUser();
    }

    interface Remote {
        void saveUserToFireBase(User user, OnCompleteListener onCompleteListener,
                                OnFailureListener onFailureListener);

        void uploadImage(Uri file, OnCompleteListener onCompleteListener,
                         OnFailureListener onFailureListener);

        void uploadByteImage(ByteArrayOutputStream bytes, OnCompleteListener onCompleteListener,
                             OnFailureListener onFailureListener);

        void getImageUrl(OnCompleteListener onCompleteListener);

        void getUsersFromDataBase(ValueEventListener valueEventListener);

        void getCurrentUserFromDataBase(ValueEventListener valueEventListener);

        void updateUserInformation(User currentUser, OnCompleteListener onCompleteListener,
                                   OnFailureListener onFailureListener);

        void getUserFromDataBase(String uid, ValueEventListener valueEventListener);

        void changeCurrentUserState(boolean isOnline);
    }
}
