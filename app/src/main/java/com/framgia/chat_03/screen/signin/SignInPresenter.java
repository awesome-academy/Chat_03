package com.framgia.chat_03.screen.signin;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.framgia.chat_03.data.repository.AuthenticationRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

final class SignInPresenter implements SignInContract.Presenter, OnCompleteListener, OnFailureListener {
    private SignInContract.View mView;
    private AuthenticationRepository mAuthenticationRepository;

    public SignInPresenter(AuthenticationRepository authenticationRepository) {
        mAuthenticationRepository = authenticationRepository;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void setView(SignInContract.View view) {
        mView = view;
    }

    @Override
    public void login(String username, String password) {
        if (validateData(username, password)) {
            mAuthenticationRepository.login(username, password,
                    this, this);
            mView.showProgressDiaglog();
        } else {
            mView.onLoginAccountEmpty();
        }
    }

    @Override
    public void onComplete(@NonNull Task task) {
        mView.hideProgressDiaglog();
        if (!task.isSuccessful()) {
            mView.onLoginInvalidUser();
            return;
        }
        mView.startHomeScreen();
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        if (e instanceof FirebaseAuthInvalidUserException) {
            mView.onLoginInvalidUser();
        }
    }

    private boolean validateData(String username, String password) {
        if (!(TextUtils.isEmpty(username) || TextUtils.isEmpty(password))) {
            return true;
        }
        return false;
    }

}