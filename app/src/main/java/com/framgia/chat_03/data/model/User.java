package com.framgia.chat_03.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String mEmail;
    private String mName;
    private String mImage;
    private String mStatus;
    private long mLastOnline;
    private boolean mIsOnline;
    private String mPassword;
    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public User() {
    }

    private String mUid;

    public User(String email, String name, String password) {
        mEmail = email;
        mName = name;
        mPassword = password;
    }

    public User(User.Builder builder) {
        mEmail = builder.mEmail;
        mName = builder.mName;
        mImage = builder.mImage;
        mStatus = builder.mStatus;
        mLastOnline = builder.mLastOnline;
        mIsOnline = builder.mIsOnline;
        mPassword = builder.mPassword;
        mUid = builder.mUid;
    }

    protected User(Parcel in) {
        mEmail = in.readString();
        mName = in.readString();
        mImage = in.readString();
        mStatus = in.readString();
        mLastOnline = in.readLong();
        mIsOnline = in.readByte() != 0;
        mPassword = in.readString();
        mUid = in.readString();
    }

    public String getUid() {
        return mUid;
    }

    public void setUid(String uid) {
        mUid = uid;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mEmail);
        dest.writeString(mName);
        dest.writeString(mImage);
        dest.writeString(mStatus);
        dest.writeLong(mLastOnline);
        dest.writeByte((byte) (mIsOnline ? 1 : 0));
        dest.writeString(mPassword);
        dest.writeString(mUid);
    }

    public static class Builder {
        private String mEmail;
        private String mName;
        private String mImage;
        private String mStatus;
        private long mLastOnline;
        private boolean mIsOnline;
        private String mPassword;
        private String mUid;

        public void setUid(String uid) {
            mUid = uid;
        }

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
        public static final String USER_ONLINE = "online";
    }
}
