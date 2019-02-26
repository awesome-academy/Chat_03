package com.framgia.chat_03.data.model;

public class Message {
    private String mFromId;
    private String mType;
    private String mContent;
    private long mTimeStamp;
    private boolean mIsSeen;

    public Message() {
    }

    public Message(String fromId, String type, String content, long timeStamp, boolean isSeen) {
        mFromId = fromId;
        mType = type;
        mContent = content;
        mTimeStamp = timeStamp;
        mIsSeen = isSeen;
    }

    public Message(Builder builder) {
        mFromId = builder.mFromId;
        mType = builder.mType;
        mContent = builder.mContent;
        mTimeStamp = builder.mTimeStamp;
        mIsSeen = builder.isSeen;
    }

    public String getFromId() {
        return mFromId;
    }

    public void setFromId(String fromId) {
        mFromId = fromId;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public long getTimeStamp() {
        return mTimeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        mTimeStamp = timeStamp;
    }

    public boolean isSeen() {
        return mIsSeen;
    }

    public Message setSeen(boolean seen) {
        mIsSeen = seen;
        return this;
    }

    public static class Builder {
        private String mFromId;
        private String mType;
        private String mContent;
        private long mTimeStamp;
        private boolean isSeen;

        public Builder setFromId(String fromId) {
            mFromId = fromId;
            return this;
        }

        public Builder setType(String type) {
            mType = type;
            return this;
        }

        public Builder setContent(String content) {
            mContent = content;
            return this;
        }

        public Builder setTimeStamp(long timeStamp) {
            mTimeStamp = timeStamp;
            return this;
        }

        public Builder setSeen(boolean seen) {
            isSeen = seen;
            return this;
        }
    }

    public class MessageKey {
        public static final String MESSAGE_REFERENCE = "messages";
    }
}
