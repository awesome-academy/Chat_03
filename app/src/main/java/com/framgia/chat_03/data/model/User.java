package com.framgia.chat_03.data.model;

public class User {
    private String mEmail;
    private String mPassword;
    private String mName;
    private String mImage;

    public User() {
    }

    public User(String email, String name, String password,String image) {
        mEmail = email;
        mName = name;
        mPassword = password;
        mImage=image;
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
}
