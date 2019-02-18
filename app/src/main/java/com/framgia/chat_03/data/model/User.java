package com.framgia.chat_03.data.model;

public class User {
    private String mUid;
    private String mEmail;
    private String mPassword;
    private String mName;
    private String mImage;
    private String mStatus;
    private long mLastSignIn;
    private boolean mIsOnline;

    public User(String name, String image, String status) {
        mName = name;
        mImage = image;
        mStatus = status;
    }

    public User() {
    }

    public User(String uid, String email, String name, String password, String image) {
        mUid = uid;
        mEmail = email;
        mName = name;
        mPassword = password;
        mImage = image;
    }

    public User(String email, String name, String password, String image) {
        mEmail = email;
        mName = name;
        mPassword = password;
        mImage = image;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public long getLastSignIn() {
        return mLastSignIn;
    }

    public void setLastSignIn(long lastSignIn) {
        mLastSignIn = lastSignIn;
    }

    public boolean isOnline() {
        return mIsOnline;
    }

    public void setOnline(boolean online) {
        mIsOnline = online;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getUid() {
        return mUid;
    }

    public void setUid(String uid) {
        mUid = uid;
    }

    public class UserKey {
        public static final String USER_REFERENCE = "user";
    }
}
