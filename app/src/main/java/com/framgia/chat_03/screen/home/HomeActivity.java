package com.framgia.chat_03.screen.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.framgia.chat_03.R;
import com.framgia.chat_03.data.repository.UserRepository;
import com.framgia.chat_03.data.source.local.UserLocalDataSource;
import com.framgia.chat_03.data.source.remote.UserRemoteDataSource;
import com.framgia.chat_03.screen.BaseActivity;
import com.framgia.chat_03.screen.listfriend.FriendFragment;
import com.framgia.chat_03.screen.listmessage.MessageFragment;
import com.framgia.chat_03.screen.profile.ProfileFragment;
import com.framgia.chat_03.utils.BottomNavigationBehavior;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class HomeActivity extends BaseActivity implements HomeContract.View,
        BottomNavigationView.OnNavigationItemSelectedListener {
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
        CoordinatorLayout.LayoutParams layoutParams =
                (CoordinatorLayout.LayoutParams) mBottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());
        mTextTitle = findViewById(R.id.text_title);
        mImageAvatar = findViewById(R.id.image_avatar);
        mTextTitle.setText(getResources().getString(R.string.title_list_inbox));
        mPresenter.loadAvatarUrl();
    }

    private void initPresenter() {
        UserRepository userRepository = new UserRepository(new UserLocalDataSource(PreferenceManager
                .getDefaultSharedPreferences(this)),
                new UserRemoteDataSource(FirebaseAuth.getInstance(),
                        FirebaseDatabase.getInstance(),
                        FirebaseStorage.getInstance()));
        mPresenter = new HomePresenter(userRepository);
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
}
