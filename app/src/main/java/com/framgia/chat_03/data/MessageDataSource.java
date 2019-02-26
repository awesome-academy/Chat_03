package com.framgia.chat_03.data;

import com.framgia.chat_03.data.model.Message;
import com.google.firebase.database.ValueEventListener;

public interface MessageDataSource {
    interface Remote {
        void sendMessage(Message message, String toUid);

        void getMessageFromDataBase(String uid, ValueEventListener valueEventListener);
    }
}
