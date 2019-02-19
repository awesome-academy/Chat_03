package com.framgia.chat_03.data.source.remote;

import android.net.Uri;

import com.framgia.chat_03.data.UserDataSource;
import com.framgia.chat_03.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class UserRemoteDataSource implements UserDataSource.Remote {
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private FirebaseStorage mStorage;

    public UserRemoteDataSource(FirebaseAuth auth,
                                FirebaseDatabase database,
                                FirebaseStorage storage) {
        mAuth = auth;
        mDatabase = database;
        mStorage = storage;
    }

    @Override
    public void saveUserToFireBase(User user, OnCompleteListener onCompleteListener,
                                   OnFailureListener onFailureListener) {
        mDatabase.getReference(User.UserKey.USER_REFERENCE)
                .child(mAuth.getUid())
                .setValue(user)
                .addOnCompleteListener(onCompleteListener)
                .addOnFailureListener(onFailureListener);
    }

    @Override
    public void uploadImage(Uri file,
                            OnCompleteListener onCompleteListener,
                            OnFailureListener onFailureListener) {
        StorageReference storageReference = mStorage.getReference();
        storageReference.child(mAuth.getUid())
                .putFile(file)
                .addOnCompleteListener(onCompleteListener)
                .addOnFailureListener(onFailureListener);
    }

    @Override
    public void getImageUrl(OnCompleteListener onCompleteListener) {
        StorageReference storageReference = mStorage.getReference(mAuth.getUid());
        storageReference.getDownloadUrl().addOnCompleteListener(onCompleteListener);
    }

    @Override
    public void getUserFromDataBase(ValueEventListener valueEventListener) {
        mDatabase.getReference(User.UserKey.USER_REFERENCE)
                .addValueEventListener(valueEventListener);
    }
}
