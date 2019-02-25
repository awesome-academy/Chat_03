package com.framgia.chat_03.data;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;

public interface AuthenticationDataSource {
    interface Remote {
        void login(String userName, String password,
                   OnCompleteListener onCompleteListener,
                   OnFailureListener onFailureListener);

        void signUp(String fullName, String email, String password,
                    OnCompleteListener onCompleteListener,
                    OnFailureListener onFailureListener);

        void signOut();
    }
}
