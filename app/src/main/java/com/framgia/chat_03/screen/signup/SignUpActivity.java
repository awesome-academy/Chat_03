package com.framgia.chat_03.screen.signup;

import android.os.Bundle;

import com.framgia.chat_03.R;
import com.framgia.chat_03.screen.BaseActivity;

public class SignUpActivity extends BaseActivity implements SignUpContract.View {
    private SignUpContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initView();
    }

    private void initView() {
        mPresenter = new SignUpPresenter();
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
