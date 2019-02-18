package com.framgia.chat_03.data.model;

public class Message {
    private String mName;
    private String mLastMessage;
    private String mAvatar;
    private String mLastOnline;
    private long mTimeStamp;
    private boolean mSeen;
    private boolean mOnline;

    public Message() {
    }

    public Message(String name, String lastMessage, String avatar, String lastOnline, boolean seen) {
        mName = name;
        mLastMessage = lastMessage;
        mAvatar = avatar;
        mLastOnline = lastOnline;
        mSeen = seen;
    }

    public long getTimeStamp() {
        return mTimeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        mTimeStamp = timeStamp;
    }

    public boolean isOnline() {
        return mOnline;
    }

    public void setOnline(boolean online) {
        mOnline = online;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getLastMessage() {
        return mLastMessage;
    }

    public void setLastMessage(String lastMessage) {
        mLastMessage = lastMessage;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public void setAvatar(String avatar) {
        mAvatar = avatar;
    }

    public String getLastOnline() {
        return mLastOnline;
    }

    public void setLastOnline(String lastOnline) {
        mLastOnline = lastOnline;
    }

    public boolean isSeen() {
        return mSeen;
    }

    public void setSeen(boolean seen) {
        mSeen = seen;
    }
}
