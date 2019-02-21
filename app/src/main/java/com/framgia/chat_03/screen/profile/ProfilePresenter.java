package com.framgia.chat_03.screen.profile;

import android.support.annotation.NonNull;

import com.framgia.chat_03.data.model.User;
import com.framgia.chat_03.data.repository.UserRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class ProfilePresenter implements ProfileContract.Presenter, ValueEventListener {
    private ProfileContract.View mView;
    private UserRepository mUserRepository;

    public ProfilePresenter(UserRepository userRepository) {
        mUserRepository = userRepository;
    }

    @Override
    public void setView(ProfileContract.View view) {
        mView = view;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void loadCurrentUser() {
        mUserRepository.getCurrentUserFromDataBase(this);
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        User currentUser = dataSnapshot.getValue(User.class);
        mView.showCurrentUserProfile(currentUser);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
    }
}
