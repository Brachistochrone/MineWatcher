package com.artemlikhomanov.minewatcher.fragments;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.artemlikhomanov.minewatcher.R;
import com.artemlikhomanov.minewatcher.model.Block;
import com.artemlikhomanov.minewatcher.model.Block_cls450;
import com.artemlikhomanov.minewatcher.model.Block_cls450_first;
import com.artemlikhomanov.minewatcher.model.Block_kdk500;
import com.artemlikhomanov.minewatcher.model.Cls450StateDataItem;
import com.artemlikhomanov.minewatcher.model.Const;

import java.util.ArrayList;

public class BlocksListFragment extends ListAbstractFragment {

    private static final String SHEARER_TYPE_KEY = "SHEARER_TYPE_KEY";

    private boolean isReset = false;

    private int mCounter = 6;
    private int mShearerTypeInt;

    private Block[] mBlocks;

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mAddresses = new ArrayList<>();
    private ArrayList<Drawable> mThumbnails = new ArrayList<>();

    private String mConnectedString;
    private String mNotConnectedString;

    private int mConnectedColor;
    private int mNotConnectedColor;

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SHEARER_TYPE_KEY, mShearerTypeInt);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        extractExtraValues(savedInstanceState);
        valuesInitialisation();
    }

    @Override
    void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), linearLayoutManager.getOrientation());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(new BlocksListFragment.BlockAdapter(mBlocks));
        mRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    void updateUI(Cls450StateDataItem dataItem) {
        isReset = false;
        mCounter++;
        if (mCounter == 8) {
            for(Block block : mBlocks) {
                switch (block.getAddress()) {
                    case Const.BLOCK_01h:
                        block.setConnected(dataItem.getBlock_01h_ConnectionState());
                        break;
                    case Const.BLOCK_02h:
                        block.setConnected(dataItem.getBlock_02h_ConnectionState());
                        break;
                    case Const.BLOCK_11h:
                        block.setConnected(dataItem.getBlock_11h_ConnectionState());
                        break;
                    case Const.BLOCK_12h:
                        block.setConnected(dataItem.getBlock_12h_ConnectionState());
                        break;
                    case Const.BLOCK_13h:
                        block.setConnected(dataItem.getBlock_13h_ConnectionState());
                        break;
                    case Const.BLOCK_15h:
                        block.setConnected(dataItem.getBlock_15h_ConnectionState());
                        break;
                    case Const.BLOCK_23h:
                        block.setConnected(dataItem.getBlock_23h_ConnectionState());
                        break;
                    case Const.BLOCK_25h:
                        block.setConnected(dataItem.getBlock_25h_ConnectionState());
                        break;
                    case Const.BLOCK_26h:
                        block.setConnected(dataItem.getBlock_26h_ConnectionState());
                        break;
                    case Const.BLOCK_31h:
                        block.setConnected(dataItem.getBlock_31h_ConnectionState());
                        break;
                    case Const.BLOCK_32h:
                        block.setConnected(dataItem.getBlock_32h_ConnectionState());
                        break;
                    case Const.BLOCK_41h:
                        block.setConnected(dataItem.getBlock_41h_ConnectionState());
                        break;
                    case Const.BLOCK_42h:
                        block.setConnected(dataItem.getBlock_42h_ConnectionState());
                        break;
                    case Const.BLOCK_51h:
                        block.setConnected(dataItem.getBlock_51h_ConnectionState());
                        break;
                    case Const.BLOCK_52h:
                        block.setConnected(dataItem.getBlock_52h_ConnectionState());
                        break;
                    case Const.BLOCK_53h:
                        block.setConnected(dataItem.getBlock_53h_ConnectionState());
                        break;
                    case Const.BLOCK_54h:
                        block.setConnected(dataItem.getBlock_54h_ConnectionState());
                        break;
                    case Const.BLOCK_55h:
                        block.setConnected(dataItem.getBlock_55h_ConnectionState());
                        break;
                    case Const.BLOCK_61h:
                        block.setConnected(dataItem.getBlock_61h_ConnectionState());
                        break;
                    case Const.BLOCK_91h:
                        block.setConnected(dataItem.getBlock_91h_ConnectionState());
                        break;
                    case Const.BLOCK_92h:
                        block.setConnected(dataItem.getBlock_92h_ConnectionState());
                        break;
                    case Const.BLOCK_93h:
                        block.setConnected(dataItem.getBlock_93h_ConnectionState());
                        break;
                    case Const.BLOCK_100h:
                        block.setConnected(dataItem.getFirstRemoteControl_ConnectionState());
                        break;
                }
            }

            mRecyclerView.getAdapter().notifyDataSetChanged();

            mCounter = 0;
        }
    }

    @Override
    void resetUI() {
        if (!isReset){
            isReset = true;
            mCounter = 0;
            for (Block block : mBlocks) {
                block.setConnected(false);
            }
            mRecyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    void valuesInitialisation() {

        switch (mShearerTypeInt) {
            case Const.SHEARER_TYPE_CLS450_first:
                mBlocks = Block_cls450_first.values();
                break;
            case Const.SHEARER_TYPE_CLS450:
                mBlocks = Block_cls450.values();
                break;
            case Const.SHEARER_TYPE_KDK500:
                mBlocks = Block_kdk500.values();
                break;
        }

        for (Block block : mBlocks) {
            mNames.add(getString(block.getBlockShortNameResourceId()));
            mAddresses.add(getString(block.getBlockAddressResourceId()));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mThumbnails.add(getResources().getDrawable(block.getBlockThumbnailResourceId(), null));
            } else {
                mThumbnails.add(getResources().getDrawable(block.getBlockThumbnailResourceId()));
            }
        }

        mConnectedString = getString(mBlocks[0].getBlockConnectedStringResourceId());
        mNotConnectedString = getString(mBlocks[0].getBlockDisconnectedStringResourceId());

        mConnectedColor = getResources().getColor(mBlocks[0].getBlockConnectedColorResourceId());
        mNotConnectedColor = getResources().getColor(mBlocks[0].getBlockDisconnectedColorResourceId());
    }

    private void extractExtraValues(Bundle savedState) {
        if (savedState != null) {
            mShearerTypeInt = savedState.getInt(SHEARER_TYPE_KEY);
        } else {
            Bundle args = getArguments();
            mShearerTypeInt = args.getInt(SHEARER_TYPE_KEY);
        }
    }

    @NonNull
    public static Fragment newInstance(int shearerType) {
        Fragment fragment = new BlocksListFragment();
        Bundle args = new Bundle();
        args.putInt(SHEARER_TYPE_KEY, shearerType);
        fragment.setArguments(args);
        return fragment;
    }

    private class BlockHolder extends RecyclerView.ViewHolder {

        private TextView mName;
        private TextView mAddress;
        private TextView mState;
        private ImageView mThumbnail;

        BlockHolder(View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.block_name);
            mAddress = itemView.findViewById(R.id.block_address);
            mState = itemView.findViewById(R.id.block_state);
            mThumbnail = itemView.findViewById(R.id.block_image);
        }

        void bindData(Block block, int position) {
            mName.setText(mNames.get(position));
            mAddress.setText(mAddresses.get(position));
            mThumbnail.setImageDrawable(mThumbnails.get(position));

            if (block.isConnected()) {
                mState.setText(mConnectedString);
                mState.setTextColor(mConnectedColor);
            } else {
                mState.setText(mNotConnectedString);
                mState.setTextColor(mNotConnectedColor);
            }
        }
    }

    private class BlockAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private Block[] mBlocks;

        BlockAdapter(Block[] blocks) {
            mBlocks = blocks;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_item_block, parent, false);
            return new BlocksListFragment.BlockHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ((BlocksListFragment.BlockHolder)holder).bindData(mBlocks[position], position);
        }

        @Override
        public int getItemCount() {
            return mBlocks.length;
        }
    }
}
