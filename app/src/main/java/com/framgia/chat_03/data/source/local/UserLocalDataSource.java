package com.framgia.chat_03.data.source.local;

import android.content.SharedPreferences;

import com.framgia.chat_03.data.UserDataSource;
import com.framgia.chat_03.data.model.User;

import static com.framgia.chat_03.utils.SharePreferenceUtils.KEY_EMAIL;
import static com.framgia.chat_03.utils.SharePreferenceUtils.KEY_NAME;
import static com.framgia.chat_03.utils.SharePreferenceUtils.KEY_PASSWORD;

public class UserLocalDataSource implements UserDataSource.Local {
    private SharedPreferences mSharedPreferences;

    public UserLocalDataSource(SharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;
    }

    @Override
    public void saveUserToSharePref(User user) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_PASSWORD, user.getPassword());
        editor.putString(KEY_NAME, user.getName());
        editor.apply();
    }

    @Override
    public User getUser() {
        String email = mSharedPreferences.getString(KEY_EMAIL, null);
        String pass = mSharedPreferences.getString(KEY_PASSWORD, null);
        String name = mSharedPreferences.getString(KEY_NAME, null);
        if (email == null && pass == null && name == null) {
            return null;
        }
        return new User(email, name, pass, null);
    }
}
