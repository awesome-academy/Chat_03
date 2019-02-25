package com.framgia.chat_03.data.source.remote;

import com.framgia.chat_03.data.AuthenticationDataSource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;

public class AuthenticationRemoteDataSource implements AuthenticationDataSource.Remote {
    private FirebaseAuth mAuth;

    public AuthenticationRemoteDataSource(FirebaseAuth auth) {
        mAuth = auth;
    }

    @Override
    public void login(String username, String password,
                      OnCompleteListener onCompleteListener,
                      OnFailureListener onFailureListener) {
        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(onCompleteListener)
                .addOnFailureListener(onFailureListener);
    }

    @Override
    public void signUp(String fullName, String email, String password,
                       OnCompleteListener onCompleteListener,
                       OnFailureListener onFailureListener) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(onCompleteListener)
                .addOnFailureListener(onFailureListener);
    }

    @Override
    public void signOut() {
        mAuth.signOut();
    }
}
