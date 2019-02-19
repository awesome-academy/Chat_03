package com.framgia.chat_03.screen.listmessage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framgia.chat_03.R;
import com.framgia.chat_03.data.model.Message;
import com.framgia.chat_03.screen.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class MessageFragment extends BaseFragment implements MessageContract.View,
        MessageAdapter.OnItemClickListener {
    private MessageContract.Presenter mPresenter;
    private List<Message> mMessages;
    private MessageAdapter mAdapter;

    public static MessageFragment newInstance() {
        return new MessageFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new MessagePresenter();
        mPresenter.setView(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_message, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initComponents();
    }

    private void initData() {
    }

    private void initComponents() {
        RecyclerView recyclerView = getView().findViewById(R.id.recycler_list_message);
        mMessages = new ArrayList<>();
        mAdapter = new MessageAdapter(getActivity(), mMessages);
        mAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
    public void onItemClick(Message message) {
    }
}
