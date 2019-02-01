package com.framgia.chat_03.screen.signin;

import android.os.Bundle;

import com.framgia.chat_03.R;
import com.framgia.chat_03.screen.BaseActivity;

/**
 * SignIn Screen.
 */
public class SignInActivity extends BaseActivity implements SignInContract.View {

    private SignInContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mPresenter = new SignInPresenter();
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
