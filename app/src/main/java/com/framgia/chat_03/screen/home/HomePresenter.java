package com.framgia.chat_03.screen.home;

import android.support.annotation.NonNull;

import com.framgia.chat_03.data.model.User;
import com.framgia.chat_03.data.repository.AuthenticationRepository;
import com.framgia.chat_03.data.repository.UserRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class HomePresenter implements HomeContract.Presenter, ValueEventListener {
    private HomeContract.View mView;
    private UserRepository mUserRepository;
    private AuthenticationRepository mAuthenticationRepository;

    public HomePresenter(UserRepository userRepository, AuthenticationRepository authenticationRepository) {
        mUserRepository = userRepository;
        mAuthenticationRepository = authenticationRepository;
    }

    @Override
    public void setView(HomeContract.View view) {
        mView = view;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void loadAvatarUrl() {
        mUserRepository.getCurrentUserFromDataBase(this);
    }

    @Override
    public void signOutFirebase() {
        mAuthenticationRepository.signOut();
    }

    private boolean isExistUserImage(User user) {
        if (user == null) return false;
        return user.getImage() != null;
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        User currentUser = dataSnapshot.getValue(User.class);
        if (isExistUserImage(currentUser)) {
            mView.showAvatar(currentUser.getImage());
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
    }
}
