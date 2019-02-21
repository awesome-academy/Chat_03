package com.framgia.chat_03.screen.signin;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.framgia.chat_03.data.model.User;
import com.framgia.chat_03.data.repository.AuthenticationRepository;
import com.framgia.chat_03.data.repository.UserRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

final class SignInPresenter implements SignInContract.Presenter, OnCompleteListener, OnFailureListener {
    private SignInContract.View mView;
    private AuthenticationRepository mAuthenticationRepository;
    private UserRepository mUserRepository;

    public SignInPresenter(AuthenticationRepository authenticationRepository, UserRepository userRepository) {
        mAuthenticationRepository = authenticationRepository;
        mUserRepository = userRepository;
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
    public void getUser() {
        User user = mUserRepository.getUser();
        if (isExistUser(user)) {
            mView.fillEmailAndPassword(user);
        }
    }

    public boolean isExistUser(User user) {
        if (user == null) return false;
        return user.getEmail() != null || user.getPassword() != null;
    }

    @Override
    public void onComplete(@NonNull Task task) {
        mView.hideProgressDiaglog();
        if (!task.isSuccessful()) {
            mView.onLoginInvalidUser();
            return;
        }
        mUserRepository.getCurrentUserFromDataBase(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User currentUser = dataSnapshot.getValue(User.class);
                mUserRepository.saveUserToSharePref(currentUser);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        mView.startHomeScreen();
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        if (e instanceof FirebaseAuthInvalidUserException) {
            mView.onLoginInvalidUser();
        }
    }

    private boolean validateData(String username, String password) {
        return !(TextUtils.isEmpty(username) || TextUtils.isEmpty(password));
    }
}
