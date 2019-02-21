package com.framgia.chat_03.screen.editprofile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.framgia.chat_03.R;
import com.framgia.chat_03.screen.BaseActivity;

public class EditProfileActivity extends BaseActivity implements EditProfileContract.View {
    private EditProfileContract.Presenter mPresenter;

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, EditProfileActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        mPresenter = new EditProfilePresenter();
        mPresenter.setView(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    protected void onStop() {
        mPresenter.onStop();
        super.onStop();
    }
}
