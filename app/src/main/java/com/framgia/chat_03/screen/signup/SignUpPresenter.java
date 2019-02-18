package com.framgia.chat_03.screen.signup;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.framgia.chat_03.data.model.User;
import com.framgia.chat_03.data.repository.AuthenticationRepository;
import com.framgia.chat_03.data.repository.UserRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class SignUpPresenter implements SignUpContract.Presenter {
    private SignUpContract.View mView;
    private AuthenticationRepository mAuthenticationRepository;
    private UserRepository mUserRepository;
    private Uri mFilepath;

    public SignUpPresenter(AuthenticationRepository authenticationRepository, UserRepository userRepository) {
        mAuthenticationRepository = authenticationRepository;
        mUserRepository = userRepository;
    }

    @Override
    public void setView(SignUpContract.View view) {
        mView = view;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void signUp(String fullName, String email,
                       String password, String confirmPassword) {
        if (isValidateData(fullName, email, password, confirmPassword)) {
            mAuthenticationRepository.signUp(fullName, email, password,
                    new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            checkTask(task);
                            User user = mView.getCurrentAccount();
                            uploadImageAndSaveAccount(user);
                        }
                    }, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            mView.hideProgressDialog();
                            if (e instanceof FirebaseAuthWeakPasswordException) {
                                mView.onWeakPassword();
                            }
                        }
                    });
            mView.showProgressDialog();
        }
    }

    private void uploadImageAndSaveAccount(final User user) {
        mUserRepository.uploadImage(mFilepath, new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                checkTask(task);
                mUserRepository.getUrlImage(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        checkTask(task);
                        user.setImage(task.getResult().toString());
                        saveUserToFirebase(user);
                        saveUserToSharePref(user);

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

    private void saveUserToSharePref(User user) {
        mUserRepository.saveUserToSharePref(user);
    }

    @Override
    public void setImageAvatar(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            if (requestCode == SignUpActivity.PICK_IMAGE_FROM_GALLERY) {
                pickImageFromGallery(data);
            }
        }
    }

    private void pickImageFromGallery(Intent data) {
        mFilepath = data.getData();
        mView.setImageFromGallery(data.getData());
    }

    private boolean isValidateData(String fullName, String email, String password, String confirmPassword) {
        if (TextUtils.isEmpty(fullName)) {
            mView.onEmptyFullName();
            return false;
        }
        if (TextUtils.isEmpty(email)) {
            mView.onEmptyEmail();
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            mView.onEmptyPassWord();
            return false;
        }
        if (TextUtils.isEmpty(confirmPassword)) {
            mView.onEmptyConfirmPassword();
            return false;
        }
        if (!password.equals(confirmPassword)) {
            mView.onConfirmPasswordError();
            return false;
        }
        return true;
    }

    private void saveUserToFirebase(User currentAccount) {
        mUserRepository.saveUserToFireBase(currentAccount, new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {

            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mView.hideProgressDialog();
            }
        });
    }

    private void checkTask(Task task) {
        if (!task.isSuccessful()) {
            return;
        }
    }
}
