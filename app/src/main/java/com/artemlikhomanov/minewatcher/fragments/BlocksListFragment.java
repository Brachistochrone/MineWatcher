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
import com.artemlikhomanov.minewatcher.model.Cls450StateDataItem;

import java.util.ArrayList;
import java.util.List;

public class BlocksListFragment extends ListAbstractFragment {

    private boolean isReset = false;

    private int mCounter = 6;

    private Block[] mBlocks = Block.values();

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mAddresses = new ArrayList<>();
    private ArrayList<Drawable> mThumbnails = new ArrayList<>();

    private String mConnectedString;
    private String mNotConnectedString;

    private int mConnectedColor;
    private int mNotConnectedColor;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            mBlocks[0].setConnected(dataItem.getFrontPanel_I7050_ConnectionState());
            mBlocks[1].setConnected(dataItem.getFrontPanel_I7065_ConnectionState());
            mBlocks[2].setConnected(dataItem.getRemoteControl_Receiver_ConnectionState());
            mBlocks[3].setConnected(dataItem.getFirstRemoteControl_ConnectionState());
            mBlocks[4].setConnected(dataItem.getControlUnit_I7050_ConnectionState());
            mBlocks[5].setConnected(dataItem.getControlUnit_I7017F_A13_ConnectionState());
            mBlocks[6].setConnected(dataItem.getControlUnit_I7017F_A14_ConnectionState());
            mBlocks[7].setConnected(dataItem.getControlUnit_I7017F_A15_ConnectionState());
            mBlocks[8].setConnected(dataItem.getLeftCutterMotor_BKS_ConnectionState());
            mBlocks[9].setConnected(dataItem.getRightCutterMotor_BKS_ConnectionState());
            mBlocks[10].setConnected(dataItem.getLeftControlPanel_tCON_ConnectionState());
            mBlocks[11].setConnected(dataItem.getLeftControlPanel_I7015_ConnectionState());
            mBlocks[12].setConnected(dataItem.getLeftControlPanel_CodesDisplay_ConnectionState());
            mBlocks[13].setConnected(dataItem.getRightControlPanel_tCON_ConnectionState());
            mBlocks[14].setConnected(dataItem.getRightControlPanel_I7015_ConnectionState());
            mBlocks[15].setConnected(dataItem.getRightControlPanel_CodesDisplay_ConnectionState());
            mBlocks[16].setConnected(dataItem.getRoadControlPanel_tCON_ConnectionState());
            mBlocks[17].setConnected(dataItem.getRoadControlPanel_CodesDisplay_ConnectionState());
            mBlocks[18].setConnected(dataItem.getHydraulicFluidSensor_ConnectionState());
            mBlocks[19].setConnected(dataItem.getHydraulicValveControlUnit_I7067_ConnectionState());
            mBlocks[20].setConnected(dataItem.getHydraulicValveControlUnit_I7017FС_A2_ConnectionState());
            mBlocks[21].setConnected(dataItem.getHydraulicValveControlUnit_I7017FС_A3_ConnectionState());
            mBlocks[22].setConnected(dataItem.getInverter_ConnectionState());

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

    @NonNull
    public static Fragment newInstance() {
        return new BlocksListFragment();
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
