package com.framgia.chat_03.screen.signin;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.framgia.chat_03.R;
import com.framgia.chat_03.data.repository.AuthenticationRepository;
import com.framgia.chat_03.data.source.remote.AuthenticationRemoteDataSource;
import com.framgia.chat_03.screen.BaseActivity;
import com.google.firebase.auth.FirebaseAuth;

/**
 * SignIn Screen.
 */
public class SignInActivity extends BaseActivity implements SignInContract.View, View.OnClickListener {

    private EditText mEditTextUser;
    private EditText mEditTextPassWord;
    private ProgressDialog mDialogLogin;
    private SignInContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initViews();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_sign_in:
                String username = mEditTextUser.getText().toString().trim();
                String password = mEditTextPassWord.getText().toString().trim();
                mPresenter.login(username, password);
                break;
            default:
                break;
        }

    }

    @Override
    public void showProgressDiaglog() {
        mDialogLogin.setMessage(getString(R.string.login_loading));
        mDialogLogin.show();
    }

    @Override
    public void hideProgressDiaglog() {
        mDialogLogin.dismiss();
    }

    @Override
    public void startHomeScreen() {
    }

    @Override
    public void onLoginAccountEmpty() {
        Toast.makeText(this, R.string.msg_login_account_empty, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoginInvalidUser() {
        Toast.makeText(this, R.string.msg_login_fail, Toast.LENGTH_SHORT).show();
    }

    private void initViews() {
        mPresenter = new SignInPresenter(new AuthenticationRepository(
                new AuthenticationRemoteDataSource(FirebaseAuth.getInstance())));
        mPresenter.setView(this);
        mEditTextUser = findViewById(R.id.text_user_name);
        mEditTextPassWord = findViewById(R.id.text_password);
        mDialogLogin = new ProgressDialog(this);
        initComponent();
    }

    private void initComponent() {
        findViewById(R.id.button_sign_in).setOnClickListener(this);
        findViewById(R.id.text_sign_up).setOnClickListener(this);
    }

}