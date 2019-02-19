package com.framgia.chat_03.data.source.local;

import android.content.SharedPreferences;

import com.framgia.chat_03.data.UserDataSource;
import com.framgia.chat_03.data.model.User;
import com.google.gson.Gson;

import static com.framgia.chat_03.utils.SharePreferenceUtils.KEY_USER_JSON;

public class UserLocalDataSource implements UserDataSource.Local {
    private SharedPreferences mSharedPreferences;

    public UserLocalDataSource(SharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;
    }

    @Override
    public void saveUserToSharePref(User user) {
        String userJson = new Gson().toJson(user);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(KEY_USER_JSON, userJson);
        editor.apply();
    }

    @Override
    public User getUser() {
        String userJson = mSharedPreferences.getString(KEY_USER_JSON, null);
        User user = new Gson().fromJson(userJson, User.class);
        if (user == null) {
            return null;
        }
        return user;
    }
}
