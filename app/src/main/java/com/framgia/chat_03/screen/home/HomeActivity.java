package com.framgia.chat_03.screen.home;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.framgia.chat_03.R;
import com.framgia.chat_03.data.repository.AuthenticationRepository;
import com.framgia.chat_03.data.repository.UserRepository;
import com.framgia.chat_03.data.source.local.UserLocalDataSource;
import com.framgia.chat_03.data.source.remote.AuthenticationRemoteDataSource;
import com.framgia.chat_03.data.source.remote.UserRemoteDataSource;
import com.framgia.chat_03.screen.BaseActivity;
import com.framgia.chat_03.screen.listfriend.FriendFragment;
import com.framgia.chat_03.screen.listmessage.MessageFragment;
import com.framgia.chat_03.screen.profile.ProfileFragment;
import com.framgia.chat_03.screen.signin.SignInActivity;
import com.framgia.chat_03.utils.BottomNavigationBehavior;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class HomeActivity extends BaseActivity implements HomeContract.View,
        BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private HomeContract.Presenter mPresenter;
    private TextView mTextTitle;
    private ImageView mImageAvatar;

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initPresenter();
        initComponents();
        loadFragment(MessageFragment.newInstance());
    }

    private void initComponents() {
        BottomNavigationView mBottomNavigationView = findViewById(R.id.bottomNavigationView);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
        mBottomNavigationView.setSelectedItemId(0);
        CoordinatorLayout.LayoutParams layoutParams =
                (CoordinatorLayout.LayoutParams) mBottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());
        mTextTitle = findViewById(R.id.text_title);
        mImageAvatar = findViewById(R.id.image_avatar);
        mTextTitle.setText(getResources().getString(R.string.title_list_inbox));
        mPresenter.loadAvatarUrl();
        findViewById(R.id.image_logout).setOnClickListener(this);
    }

    private void initPresenter() {
        AuthenticationRepository authenticationRepository = new AuthenticationRepository(
                new AuthenticationRemoteDataSource(FirebaseAuth.getInstance()));
        UserRepository userRepository = new UserRepository(new UserLocalDataSource(PreferenceManager
                .getDefaultSharedPreferences(this)),
                new UserRemoteDataSource(FirebaseAuth.getInstance(),
                        FirebaseDatabase.getInstance(),
                        FirebaseStorage.getInstance()));
        mPresenter = new HomePresenter(userRepository, authenticationRepository);
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

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        String title = null;
        switch (menuItem.getItemId()) {
            case R.id.navigation_list_message:
                fragment = MessageFragment.newInstance();
                title = getResources().getString(R.string.title_list_inbox);
                break;
            case R.id.navigation_list_friend:
                fragment = FriendFragment.newInstance();
                title = getResources().getString(R.string.title_list_friend);
                break;
            case R.id.navigation_profile:
                fragment = ProfileFragment.newInstance();
                title = getResources().getString(R.string.title_profile);
                break;
        }
        mTextTitle.setText(title);
        loadFragment(fragment);
        return true;
    }

    @Override
    public void showAvatar(String image) {
        Glide.with(this)
                .load(image)
                .apply(RequestOptions.circleCropTransform())
                .into(mImageAvatar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_logout:
                new AlertDialog.Builder(this)
                        .setTitle(getResources().getString(R.string.log_out_title))
                        .setMessage(getResources().getString(R.string.log_out_mes))
                        .setPositiveButton(getResources().getString(R.string.action_positive),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        mPresenter.signOutFirebase();
                                        startActivity(SignInActivity.
                                                getIntent(HomeActivity.this));
                                        dialog.dismiss();
                                    }
                                })
                        .setNegativeButton(getResources()
                                .getString(R.string.action_negative), null)
                        .show();
                break;
        }
    }
}
