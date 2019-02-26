package com.framgia.chat_03.data.source.remote;

import com.framgia.chat_03.data.MessageDataSource;
import com.framgia.chat_03.data.model.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MessageRemoteDataSource implements MessageDataSource.Remote {
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;

    public MessageRemoteDataSource(FirebaseAuth auth, FirebaseDatabase database) {
        mAuth = auth;
        mDatabase = database;
    }

    @Override
    public void sendMessage(Message message, String toUid) {
        String messId = mDatabase.getReference(Message.MessageKey.MESSAGE_REFERENCE)
                .child(mAuth.getUid())
                .child(toUid)
                .push().getKey();
        mDatabase.getReference(Message.MessageKey.MESSAGE_REFERENCE)
                .child(mAuth.getUid())
                .child(toUid)
                .child(messId)
                .setValue(message);
        mDatabase.getReference(Message.MessageKey.MESSAGE_REFERENCE)
                .child(toUid)
                .child(mAuth.getUid())
                .child(messId)
                .setValue(message);
    }

    @Override
    public void getMessageFromDataBase(String uid, ValueEventListener valueEventListener) {
        mDatabase.getReference(Message.MessageKey.MESSAGE_REFERENCE)
                .child(mAuth.getUid())
                .child(uid)
                .addValueEventListener(valueEventListener);
    }
}
