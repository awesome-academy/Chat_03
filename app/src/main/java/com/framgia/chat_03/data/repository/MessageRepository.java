package com.framgia.chat_03.data.repository;

import com.framgia.chat_03.data.MessageDataSource;
import com.framgia.chat_03.data.model.Message;
import com.google.firebase.database.ValueEventListener;

public class MessageRepository implements MessageDataSource.Remote {
    private MessageDataSource.Remote mRemote;

    public MessageRepository(MessageDataSource.Remote remote) {
        mRemote = remote;
    }

    @Override
    public void sendMessage(Message message, String toUid) {
        mRemote.sendMessage(message, toUid);
    }

    @Override
    public void getMessageFromDataBase(String uid, ValueEventListener valueEventListener) {
        mRemote.getMessageFromDataBase(uid, valueEventListener);
    }
}
