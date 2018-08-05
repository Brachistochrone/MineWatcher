package com.artemlikhomanov.minewatcher.model;

import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;

import com.artemlikhomanov.minewatcher.R;

public enum Block_cls450_first implements Block {
    FRONT_PANEL_I7050(R.string.front_panel_i7050_A21,
            R.string.A1_A21,
            R.drawable.thumbnail_i7050,
            R.color.colorGreenForConnection,
            R.color.colorRedForConnection,
            R.string.connection_available,
            R.string.connection_not_available,
            Const.BLOCK_01h),
    FRONT_PANEL_I7065BD(R.string.front_panel_i7065BD_A22,
            R.string.A1_A22, R.drawable.thumbnail_i7065,
            R.color.colorGreenForConnection,
            R.color.colorRedForConnection,
            R.string.connection_available,
            R.string.connection_not_available,
            Const.BLOCK_02h),
    FRONT_PANEL_RECEIVER(R.string.front_panel_receiver_A23,
            R.string.A1_A23,
            R.drawable.thumbnail_receiver,
            R.color.colorGreenForConnection,
            R.color.colorRedForConnection,
            R.string.connection_available,
            R.string.connection_not_available,
            Const.BLOCK_23h),
    FRONT_PANEL_REMOTE_CONTROL(R.string.front_panel_remote_control_A5,
            R.string.A5,
            R.drawable.thumbnail_remote_control,
            R.color.colorGreenForConnection,
            R.color.colorRedForConnection,
            R.string.connection_available,
            R.string.connection_not_available,
            Const.BLOCK_100h),
    CONTROL_UNIT_I7050(R.string.control_unit_i7050_A10,
            R.string.A1_A10,
            R.drawable.thumbnail_i7050,
            R.color.colorGreenForConnection,
            R.color.colorRedForConnection,
            R.string.connection_available,
            R.string.connection_not_available,
            Const.BLOCK_11h),
    CONTROL_UNIT_I7017F_1(R.string.control_unit_i7017F_A13,
            R.string.A1_A13,
            R.drawable.thumbnail_i7017f,
            R.color.colorGreenForConnection,
            R.color.colorRedForConnection,
            R.string.connection_available,
            R.string.connection_not_available,
            Const.BLOCK_12h),
    CONTROL_UNIT_I7017F_2(R.string.control_unit_i7017F_A14,
            R.string.A1_A14,
            R.drawable.thumbnail_i7017f,
            R.color.colorGreenForConnection,
            R.color.colorRedForConnection,
            R.string.connection_available,
            R.string.connection_not_available,
            Const.BLOCK_13h),
    CONTROL_UNIT_I7017F_3(R.string.control_unit_i7017F_A15,
            R.string.A1_A15,
            R.drawable.thumbnail_i7017f,
            R.color.colorGreenForConnection,
            R.color.colorRedForConnection,
            R.string.connection_available,
            R.string.connection_not_available,
            Const.BLOCK_15h),
    BREAKERS_PANEL_BKS1(R.string.breakers_panel_BKS1_A6,
            R.string.A1_A6,
            R.drawable.thumbnail_bks,
            R.color.colorGreenForConnection,
            R.color.colorRedForConnection,
            R.string.connection_available,
            R.string.connection_not_available,
            Const.BLOCK_25h),
    BREAKERS_PANEL_BKS2(R.string.breakers_panel_BKS2_A7,
            R.string.A1_A7,
            R.drawable.thumbnail_bks,
            R.color.colorGreenForConnection,
            R.color.colorRedForConnection,
            R.string.connection_available,
            R.string.connection_not_available,
            Const.BLOCK_26h),
    LEFT_CONTROL_PANEL_tCON(R.string.left_control_panel_tCON_A1,
            R.string.A2_A1,
            R.drawable.thumbnail_tcon,
            R.color.colorGreenForConnection,
            R.color.colorRedForConnection,
            R.string.connection_available,
            R.string.connection_not_available,
            Const.BLOCK_31h),
    LEFT_CONTROL_PANEL_I7015(R.string.left_control_panel_i7015_A2,
            R.string.A2_A2,
            R.drawable.thumbnail_i7015,
            R.color.colorGreenForConnection,
            R.color.colorRedForConnection,
            R.string.connection_available,
            R.string.connection_not_available,
            Const.BLOCK_32h),
    LEFT_CONTROL_PANEL_INDICATOR(R.string.left_control_panel_codes_display_A3,
            R.string.A2_A3,
            R.drawable.thumbnail_indicator,
            R.color.colorGreenForConnection,
            R.color.colorRedForConnection,
            R.string.connection_available,
            R.string.connection_not_available,
            Const.BLOCK_91h),
    RIGHT_CONTROL_PANEL_tCON(R.string.right_control_panel_tCON_A1,
            R.string.A3_A1,
            R.drawable.thumbnail_tcon,
            R.color.colorGreenForConnection,
            R.color.colorRedForConnection,
            R.string.connection_available,
            R.string.connection_not_available,
            Const.BLOCK_41h),
    RIGHT_CONTROL_PANEL_I7015(R.string.right_control_panel_i7015_A2,
            R.string.A3_A2,
            R.drawable.thumbnail_i7015,
            R.color.colorGreenForConnection,
            R.color.colorRedForConnection,
            R.string.connection_available,
            R.string.connection_not_available,
            Const.BLOCK_42h),
    RIGHT_CONTROL_PANEL_INDICATOR(R.string.right_control_panel_codes_display_A3,
            R.string.A3_A3,
            R.drawable.thumbnail_indicator,
            R.color.colorGreenForConnection,
            R.color.colorRedForConnection,
            R.string.connection_available,
            R.string.connection_not_available,
            Const.BLOCK_92h),
    ROAD_CONTROL_PANEL_tCON(R.string.road_control_panel_tCON_A2,
            R.string.A7_A2,
            R.drawable.thumbnail_tcon,
            R.color.colorGreenForConnection,
            R.color.colorRedForConnection,
            R.string.connection_available,
            R.string.connection_not_available,
            Const.BLOCK_51h),
    ROAD_CONTROL_PANEL_INDICATOR(R.string.road_control_panel_codes_display_A3,
            R.string.A7_A3,
            R.drawable.thumbnail_indicator,
            R.color.colorGreenForConnection,
            R.color.colorRedForConnection,
            R.string.connection_available,
            R.string.connection_not_available,
            Const.BLOCK_93h),
    HYDRAULIC_FLUID_SENSOR(R.string.hydraulic_fluid_sensor_BT5,
            R.string.BT5,
            R.drawable.thumbnail_dkm,
            R.color.colorGreenForConnection,
            R.color.colorRedForConnection,
            R.string.connection_available,
            R.string.connection_not_available,
            Const.BLOCK_55h),
    HYDRAULIC_VALVE_CONTROL_UNIT_I7067(R.string.hydraulic_valve_control_unit_i7067_A1,
            R.string.A4_A1,
            R.drawable.thumbnail_i7067,
            R.color.colorGreenForConnection,
            R.color.colorRedForConnection,
            R.string.connection_available,
            R.string.connection_not_available,
            Const.BLOCK_54h),
    HYDRAULIC_VALVE_CONTROL_UNIT_I7017FC_1(R.string.hydraulic_valve_control_unit_i7017FC_A2,
            R.string.A4_A2,
            R.drawable.thumbnail_i7017f,
            R.color.colorGreenForConnection,
            R.color.colorRedForConnection,
            R.string.connection_available,
            R.string.connection_not_available,
            Const.BLOCK_52h),
    HYDRAULIC_VALVE_CONTROL_UNIT_I7017FC_2(R.string.hydraulic_valve_control_unit_i7017FC_A3,
            R.string.A4_A3,
            R.drawable.thumbnail_i7017f,
            R.color.colorGreenForConnection,
            R.color.colorRedForConnection,
            R.string.connection_available,
            R.string.connection_not_available,
            Const.BLOCK_53h),
    INVERTER(R.string.inverter_A1,
            R.string.A1_UZ,
            R.drawable.thumbnail_inverter,
            R.color.colorGreenForConnection,
            R.color.colorRedForConnection,
            R.string.connection_available,
            R.string.connection_not_available,
            Const.BLOCK_61h),
    ;

    private final int mBlockShortNameResourceId;
    private final int mBlockAddressResourceId;
    private final int mBlockThumbnailResourceId;
    private final int mBlockConnectedStringResourceId;
    private final int mBlockDisconnectedStringResourceId;
    private final int mBlockConnectedColorResourceId;
    private final int mBlockDisconnectedColorResourceId;

    private int mBlockAddress;
    private boolean mIsConnected;

    Block_cls450_first(@StringRes int blockShortNameResourceId, @StringRes int blockAddressResourceId, int blockThumbnailResourceId,
                       @ColorRes int blockConnectedColorResourceId, @ColorRes int blockDisconnectedColorResourceId,
                       @StringRes int blockConnectedStringResourceId, @StringRes int blockDisconnectedStringResourceId,
                       int address) {
        mBlockShortNameResourceId = blockShortNameResourceId;
        mBlockAddressResourceId = blockAddressResourceId;
        mBlockThumbnailResourceId = blockThumbnailResourceId;
        mBlockConnectedColorResourceId = blockConnectedColorResourceId;
        mBlockDisconnectedColorResourceId = blockDisconnectedColorResourceId;
        mBlockConnectedStringResourceId = blockConnectedStringResourceId;
        mBlockDisconnectedStringResourceId = blockDisconnectedStringResourceId;
        mBlockAddress = address;
    }

    @StringRes
    @Override
    public int getBlockShortNameResourceId() {
        return mBlockShortNameResourceId;
    }

    @StringRes
    @Override
    public int getBlockAddressResourceId() {
        return mBlockAddressResourceId;
    }

    public int getBlockThumbnailResourceId() {
        return mBlockThumbnailResourceId;
    }

    @ColorRes
    @Override
    public int getBlockConnectedColorResourceId() {
        return mBlockConnectedColorResourceId;
    }

    @ColorRes
    @Override
    public int getBlockDisconnectedColorResourceId() {
        return mBlockDisconnectedColorResourceId;
    }

    @StringRes
    @Override
    public int getBlockConnectedStringResourceId() {
        return mBlockConnectedStringResourceId;
    }

    @StringRes
    @Override
    public int getBlockDisconnectedStringResourceId() {
        return mBlockDisconnectedStringResourceId;
    }

    @Override
    public boolean isConnected() {
        return mIsConnected;
    }

    @Override
    public void setConnected(boolean connected) {
        mIsConnected = connected;
    }

    @Override
    public int getAddress() {
        return mBlockAddress;
    }
}
