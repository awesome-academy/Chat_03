package com.framgia.chat_03.screen.listfriend;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framgia.chat_03.R;
import com.framgia.chat_03.data.model.User;
import com.framgia.chat_03.data.repository.UserRepository;
import com.framgia.chat_03.data.source.local.UserLocalDataSource;
import com.framgia.chat_03.data.source.remote.UserRemoteDataSource;
import com.framgia.chat_03.screen.BaseFragment;
import com.framgia.chat_03.screen.chat.ChatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class FriendFragment extends BaseFragment implements FriendContract.View, FriendAdapter.OnItemClickListener {
    private FriendContract.Presenter mPresenter;
    private List<User> mUsers;
    private FriendAdapter mAdapter;

    public static FriendFragment newInstance() {
        return new FriendFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_friend, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initComponents();
    }

    private void initComponents() {
        RecyclerView recyclerView = getView().findViewById(R.id.recycler_list_friend);
        mUsers = new ArrayList<>();
        mAdapter = new FriendAdapter(getActivity(), mUsers);
        mAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void initData() {
        UserRepository userRepository = new UserRepository(
                new UserLocalDataSource(PreferenceManager.getDefaultSharedPreferences(getActivity())),
                new UserRemoteDataSource(FirebaseAuth.getInstance(),
                        FirebaseDatabase.getInstance(),
                        FirebaseStorage.getInstance()));
        mPresenter = new FriendPresenter(userRepository);
        mPresenter.setView(this);
        mPresenter.getUserFromDatabase();
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
        super.onStop();
    }

    @Override
    public void onImageChatClick(User user) {
        startActivity(ChatActivity.getIntent(getContext(), user));
    }

    @Override
    public void onImageProfileClick(User user) {
    }

    @Override
    public void onGetUsersSuccess(List<User> users) {
        mAdapter.addData(users);
    }
}
