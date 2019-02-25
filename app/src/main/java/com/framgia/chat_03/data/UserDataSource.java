package com.framgia.chat_03.data;

import android.net.Uri;

import com.framgia.chat_03.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.ValueEventListener;

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

        void getImageUrl(OnCompleteListener onCompleteListener);

        void getUserFromDataBase(ValueEventListener valueEventListener);

        void getCurrentUserFromDataBase(ValueEventListener valueEventListener);

        void updateUserInformation(User currentUser, OnCompleteListener onCompleteListener,
                                   OnFailureListener onFailureListener);
    }
}
