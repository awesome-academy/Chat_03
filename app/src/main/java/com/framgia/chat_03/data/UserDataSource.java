package com.framgia.chat_03.data;

import com.framgia.chat_03.data.model.User;

public interface UserDataSource {
    interface Local{
        void saveUser(User user);

        User getUser();
    }

    interface Remote {
        void saveUserToFireBase(User user);
    }
}
