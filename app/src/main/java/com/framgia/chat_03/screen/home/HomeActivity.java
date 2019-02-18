package com.framgia.chat_03.screen.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.framgia.chat_03.utils.BottomNavigationBehavior;
import com.framgia.chat_03.R;
import com.framgia.chat_03.screen.BaseActivity;
import com.framgia.chat_03.screen.listmessage.MessageFragment;

public class HomeActivity extends BaseActivity implements HomeContract.View,
        BottomNavigationView.OnNavigationItemSelectedListener {
    private HomeContract.Presenter mPresenter;

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
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
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) mBottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());
    }

    private void initPresenter() {
        mPresenter = new HomePresenter();
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
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment;
        switch (menuItem.getItemId()) {
            case R.id.navigation_list_message:
                fragment = new MessageFragment();
                loadFragment(fragment);
                return true;
            case R.id.navigation_list_friend:
                return true;
            case R.id.navigation_profile:
                return true;
        }
        return false;
    }
}