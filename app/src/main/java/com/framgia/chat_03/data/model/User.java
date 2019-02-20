package com.framgia.chat_03.data.model;

public class User {
    private String mEmail;
    private String mName;
    private String mImage;
    private String mStatus;
    private long mLastOnline;
    private boolean mIsOnline;
    private String mPassword;

    public User() {
    }

    public User(User.Builder builder) {
        mEmail = builder.mEmail;
        mName = builder.mName;
        mImage = builder.mImage;
        mStatus = builder.mStatus;
        mLastOnline = builder.mLastOnline;
        mIsOnline = builder.mIsOnline;
        mPassword = builder.mPassword;
    }

    public User(String email, String name, String password) {
        mEmail = email;
        mName = name;
        mPassword = password;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public long getLastOnline() {
        return mLastOnline;
    }

    public void setLastOnline(long lastOnline) {
        mLastOnline = lastOnline;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
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

    public static class Builder {
        private String mEmail;
        private String mName;
        private String mImage;
        private String mStatus;
        private long mLastOnline;
        private boolean mIsOnline;
        private String mPassword;

        public Builder setEmail(String email) {
            mEmail = email;
            return this;
        }

        public Builder setName(String name) {
            mName = name;
            return this;
        }

        public Builder setImage(String image) {
            mImage = image;
            return this;
        }

        public Builder setStatus(String status) {
            mStatus = status;
            return this;
        }

        public Builder setLastOnline(long lastOnline) {
            mLastOnline = lastOnline;
            return this;
        }

        public Builder setOnline(boolean online) {
            mIsOnline = online;
            return this;
        }

        public Builder setPassword(String password) {
            mPassword = password;
            return this;
        }
    }

    public class UserKey {
        public static final String USER_REFERENCE = "users";
    }
}
