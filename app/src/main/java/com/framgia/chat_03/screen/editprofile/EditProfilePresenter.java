package com.framgia.chat_03.screen.editprofile;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.framgia.chat_03.data.model.User;
import com.framgia.chat_03.data.repository.UserRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class EditProfilePresenter implements EditProfileContract.Presenter, ValueEventListener {
    private EditProfileContract.View mView;
    private UserRepository mUserRepository;
    private Uri mFilepath;
    private User currentUser;

    public EditProfilePresenter(UserRepository userRepository) {
        mUserRepository = userRepository;
    }

    @Override
    public void setView(EditProfileContract.View view) {
        mView = view;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void loadCurrentUserProfile() {
        mUserRepository.getCurrentUserFromDataBase(this);
    }

    @Override
    public void updateUserProfile(String fullName, String email, String status) {
        if (isValidateData(fullName, email, status)) {
            mView.showProgressDialog();
            if (mFilepath == null) {
                updateUserWithoutAvatar(fullName, email, status);
            } else {
                updateUserWithAvatar(fullName, email, status);
            }
        }
    }

    private void updateUserWithAvatar(String fullName, String email, String status) {
        currentUser.setName(fullName);
        currentUser.setEmail(email);
        currentUser.setStatus(status);
        mUserRepository.uploadImage(mFilepath, new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                checkTask(task);
                mUserRepository.getImageUrl(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        checkTask(task);
                        currentUser.setImage(task.getResult().toString());
                        updateUser(currentUser);
                    }
                });
            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mView.hideProgressDialog();
            }
        });
    }

    private void updateUser(User currentUser) {
        mUserRepository.updateUserInformation(currentUser, new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                mView.onUpdateUserSuccess();
            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mView.showError(e.getMessage());
            }
        });
        mView.hideProgressDialog();
    }

    private void updateUserWithoutAvatar(String fullName, String email, String status) {
        currentUser.setName(fullName);
        currentUser.setEmail(email);
        currentUser.setStatus(status);
        updateUser(currentUser);
    }

    @Override
    public void setImageAvatar(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            if (requestCode == EditProfileActivity.PICK_IMAGE_FROM_GALLERY) {
                pickImageFromGallery(data);
            }
        }
    }

    private void pickImageFromGallery(Intent data) {
        mFilepath = data.getData();
        mView.setImageFromGallery(data.getData());
    }

    private boolean isValidateData(String fullName, String email, String status) {
        if (TextUtils.isEmpty(fullName)) {
            mView.onEmptyFullName();
            return false;
        }
        if (TextUtils.isEmpty(email)) {
            mView.onEmptyEmail();
            return false;
        }
        if (TextUtils.isEmpty(status)) {
            mView.onEmptyStatus();
            return false;
        }
        return true;
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        currentUser = dataSnapshot.getValue(User.class);
        mView.showProfile(currentUser);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
    }

    private void checkTask(Task task) {
        if (!task.isSuccessful()) {
            return;
        }
    }
}
