package com.framgia.chat_03.screen.listfriend;

import android.support.annotation.NonNull;

import com.framgia.chat_03.data.model.User;
import com.framgia.chat_03.data.repository.UserRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FriendPresenter implements FriendContract.Presenter, ValueEventListener {
    private FriendContract.View mView;
    private UserRepository mRepository;

    public FriendPresenter(UserRepository repository) {
        mRepository = repository;
    }

    @Override
    public void setView(FriendContract.View view) {
        mView = view;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void getUserFromDatabase() {
        mRepository.getUserFromDataBase(this);
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        List<User> users = new ArrayList<>();
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            User user = snapshot.getValue(User.class);
            users.add(user);
        }
        mView.onGetUsersSuccess(users);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
    }
}
