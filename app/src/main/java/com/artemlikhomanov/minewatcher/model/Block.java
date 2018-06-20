package com.artemlikhomanov.minewatcher.model;

import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;

import com.artemlikhomanov.minewatcher.R;

public enum Block {
    FRONT_PANEL_I7050(R.string.front_panel_i7050_A21, R.string.A1_A21, R.drawable.thumbnail_i7050, R.color.colorGreenForConnection, R.color.colorRedForConnection, R.string.connection_available, R.string.connection_not_available),
    FRONT_PANEL_I7065BD(R.string.front_panel_i7065BD_A22, R.string.A1_A22, R.drawable.thumbnail_i7065, R.color.colorGreenForConnection, R.color.colorRedForConnection, R.string.connection_available, R.string.connection_not_available),
    FRONT_PANEL_RECEIVER(R.string.front_panel_receiver_A23, R.string.A1_A23, R.drawable.thumbnail_receiver, R.color.colorGreenForConnection, R.color.colorRedForConnection, R.string.connection_available, R.string.connection_not_available),
    FRONT_PANEL_REMOTE_CONTROL(R.string.front_panel_remote_control_A5, R.string.A5, R.drawable.thumbnail_remote_control, R.color.colorGreenForConnection, R.color.colorRedForConnection, R.string.connection_available, R.string.connection_not_available),
    CONTROL_UNIT_I7050(R.string.control_unit_i7050_A10, R.string.A1_A10, R.drawable.thumbnail_i7050, R.color.colorGreenForConnection, R.color.colorRedForConnection, R.string.connection_available, R.string.connection_not_available),
    CONTROL_UNIT_I7017F_1(R.string.control_unit_i7017F_A13, R.string.A1_A13, R.drawable.thumbnail_i7017f, R.color.colorGreenForConnection, R.color.colorRedForConnection, R.string.connection_available, R.string.connection_not_available),
    CONTROL_UNIT_I7017F_2(R.string.control_unit_i7017F_A14, R.string.A1_A14, R.drawable.thumbnail_i7017f, R.color.colorGreenForConnection, R.color.colorRedForConnection, R.string.connection_available, R.string.connection_not_available),
    CONTROL_UNIT_I7017F_3(R.string.control_unit_i7017F_A15, R.string.A1_A15, R.drawable.thumbnail_i7017f, R.color.colorGreenForConnection, R.color.colorRedForConnection, R.string.connection_available, R.string.connection_not_available),
    BREAKERS_PANEL_BKS1(R.string.breakers_panel_BKS1_A6, R.string.A1_A6, R.drawable.thumbnail_bks, R.color.colorGreenForConnection, R.color.colorRedForConnection, R.string.connection_available, R.string.connection_not_available),
    BREAKERS_PANEL_BKS2(R.string.breakers_panel_BKS2_A7, R.string.A1_A7, R.drawable.thumbnail_bks, R.color.colorGreenForConnection, R.color.colorRedForConnection, R.string.connection_available, R.string.connection_not_available),
    LEFT_CONTROL_PANEL_tCON(R.string.left_control_panel_tCON_A1, R.string.A2_A1, R.drawable.thumbnail_tcon, R.color.colorGreenForConnection, R.color.colorRedForConnection, R.string.connection_available, R.string.connection_not_available),
    LEFT_CONTROL_PANEL_I7015(R.string.left_control_panel_i7015_A2, R.string.A2_A2, R.drawable.thumbnail_i7015, R.color.colorGreenForConnection, R.color.colorRedForConnection, R.string.connection_available, R.string.connection_not_available),
    LEFT_CONTROL_INDICATOR(R.string.left_control_panel_codes_display_A3, R.string.A2_A3, R.drawable.thumbnail_indicator, R.color.colorGreenForConnection, R.color.colorRedForConnection, R.string.connection_available, R.string.connection_not_available),
    RIGHT_CONTROL_PANEL_tCON(R.string.right_control_panel_tCON_A1, R.string.A3_A1, R.drawable.thumbnail_tcon, R.color.colorGreenForConnection, R.color.colorRedForConnection, R.string.connection_available, R.string.connection_not_available),
    RIGHT_CONTROL_PANEL_I7015(R.string.right_control_panel_i7015_A2, R.string.A3_A2, R.drawable.thumbnail_i7015, R.color.colorGreenForConnection, R.color.colorRedForConnection, R.string.connection_available, R.string.connection_not_available),
    RIGHT_CONTROL_INDICATOR(R.string.right_control_panel_codes_display_A3, R.string.A3_A3, R.drawable.thumbnail_indicator, R.color.colorGreenForConnection, R.color.colorRedForConnection, R.string.connection_available, R.string.connection_not_available),
    ROAD_CONTROL_PANEL_tCON(R.string.road_control_panel_tCON_A2, R.string.A7_A2, R.drawable.thumbnail_tcon, R.color.colorGreenForConnection, R.color.colorRedForConnection, R.string.connection_available, R.string.connection_not_available),
    ROAD_CONTROL_PANEL_INDICATOR(R.string.road_control_panel_codes_display_A3, R.string.A7_A3, R.drawable.thumbnail_indicator, R.color.colorGreenForConnection, R.color.colorRedForConnection, R.string.connection_available, R.string.connection_not_available),
    HYDRAULIC_FLUID_SENSOR(R.string.hydraulic_fluid_sensor_BT5, R.string.BT5, R.drawable.thumbnail_dkm, R.color.colorGreenForConnection, R.color.colorRedForConnection, R.string.connection_available, R.string.connection_not_available),
    HYDRAULIC_VALVE_CONTROL_UNIT_I7067(R.string.hydraulic_valve_control_unit_i7067_A1, R.string.A4_A1, R.drawable.thumbnail_i7067, R.color.colorGreenForConnection, R.color.colorRedForConnection, R.string.connection_available, R.string.connection_not_available),
    HYDRAULIC_VALVE_CONTROL_UNIT_I7017FC_1(R.string.hydraulic_valve_control_unit_i7017FC_A2, R.string.A4_A2, R.drawable.thumbnail_i7017f, R.color.colorGreenForConnection, R.color.colorRedForConnection, R.string.connection_available, R.string.connection_not_available),
    HYDRAULIC_VALVE_CONTROL_UNIT_I7017FC_2(R.string.hydraulic_valve_control_unit_i7017FC_A3, R.string.A4_A3, R.drawable.thumbnail_i7017f, R.color.colorGreenForConnection, R.color.colorRedForConnection, R.string.connection_available, R.string.connection_not_available),
    INVERTER(R.string.inverter_A1, R.string.A1_UZ, R.drawable.thumbnail_inverter, R.color.colorGreenForConnection, R.color.colorRedForConnection, R.string.connection_available, R.string.connection_not_available),
    ;

    private final int mBlockShortNameResourceId;
    private final int mBlockAddressResourceId;
    private final int mBlockThumbnailResourceId;
    private final int mBlockConnectedStringResourceId;
    private final int mBlockDisconnectedStringResourceId;
    private final int mBlockConnectedColorResourceId;
    private final int mBlockDisconnectedColorResourceId;
    private boolean mIsConnected;

    Block(@StringRes int blockShortNameResourceId, @StringRes int blockAddressResourceId, int blockThumbnailResourceId,
          @ColorRes int blockConnectedColorResourceId, @ColorRes int blockDisconnectedColorResourceId,
          @StringRes int blockConnectedStringResourceId, @StringRes int blockDisconnectedStringResourceId) {
        mBlockShortNameResourceId = blockShortNameResourceId;
        mBlockAddressResourceId = blockAddressResourceId;
        mBlockThumbnailResourceId = blockThumbnailResourceId;
        mBlockConnectedColorResourceId = blockConnectedColorResourceId;
        mBlockDisconnectedColorResourceId = blockDisconnectedColorResourceId;
        mBlockConnectedStringResourceId = blockConnectedStringResourceId;
        mBlockDisconnectedStringResourceId = blockDisconnectedStringResourceId;
    }

    @StringRes
    public int getBlockShortNameResourceId() {
        return mBlockShortNameResourceId;
    }

    @StringRes
    public int getBlockAddressResourceId() {
        return mBlockAddressResourceId;
    }

    public int getBlockThumbnailResourceId() {
        return mBlockThumbnailResourceId;
    }

    @ColorRes
    public int getBlockConnectedColorResourceId() {
        return mBlockConnectedColorResourceId;
    }

    @ColorRes
    public int getBlockDisconnectedColorResourceId() {
        return mBlockDisconnectedColorResourceId;
    }

    @StringRes
    public int getBlockConnectedStringResourceId() {
        return mBlockConnectedStringResourceId;
    }

    @StringRes
    public int getBlockDisconnectedStringResourceId() {
        return mBlockDisconnectedStringResourceId;
    }

    public boolean isConnected() {
        return mIsConnected;
    }

    public void setConnected(boolean connected) {
        mIsConnected = connected;
    }
}
