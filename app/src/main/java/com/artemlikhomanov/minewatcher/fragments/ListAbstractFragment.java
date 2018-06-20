package com.artemlikhomanov.minewatcher.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.artemlikhomanov.minewatcher.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class ListAbstractFragment extends BaseAbstractFragment {

    @BindView(R.id.list)
    RecyclerView mRecyclerView;

    abstract void setupRecyclerView();

    @Override
    int getLayoutResId() {
        return R.layout.fragment_list;
    }

    @Override
    void initialiseUI(View view) {
        ButterKnife.bind(this, view);
        setupRecyclerView();
    }
}
