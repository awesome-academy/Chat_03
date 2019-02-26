package com.framgia.chat_03.screen.chat;

import android.support.annotation.NonNull;

import com.framgia.chat_03.data.model.Message;
import com.framgia.chat_03.data.model.User;
import com.framgia.chat_03.data.repository.MessageRepository;
import com.framgia.chat_03.data.repository.UserRepository;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatPresenter implements ChatContract.Presenter, ValueEventListener {
    private static final int QUALITY = 100;
    private static final String DATA = "data";
    private ChatContract.View mView;
    private MessageRepository mMessageRepository;
    private UserRepository mUserRepository;

    public ChatPresenter(MessageRepository messageRepository,
                         UserRepository userRepository) {
        mMessageRepository = messageRepository;
        mUserRepository = userRepository;
    }

    @Override
    public void setView(ChatContract.View view) {
        mView = view;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void loadMessageFromDataBase(String uid) {
        mMessageRepository.getMessageFromDataBase(uid, this);
    }

    @Override
    public void loadCurrentUser() {
        mUserRepository.getCurrentUserFromDataBase(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                user.setUid(dataSnapshot.getKey());
                mView.onGetCurrentUserSuccess(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void loadInteractUser(String uid) {
        mUserRepository.getUserFromDataBase(uid, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                user.setUid(dataSnapshot.getKey());
                mView.showInformationUser(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        List<Message> messages = new ArrayList<>();
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            Message message = snapshot.getValue(Message.class);
            messages.add(message);
        }
        mView.onGetMessagesSuccess(messages);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
    }

    private void checkTask(Task task) {
        if (!task.isSuccessful()) {
            return;
        }
    }
}
