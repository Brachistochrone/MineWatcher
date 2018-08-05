package com.artemlikhomanov.minewatcher.model;

import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;

public interface Block {

    @StringRes
    int getBlockShortNameResourceId();

    @StringRes
    int getBlockAddressResourceId();

    int getBlockThumbnailResourceId();

    @ColorRes
    int getBlockConnectedColorResourceId();

    @ColorRes
    int getBlockDisconnectedColorResourceId();

    @StringRes
    int getBlockConnectedStringResourceId();

    @StringRes
    int getBlockDisconnectedStringResourceId();

    boolean isConnected();

    void setConnected(boolean connected);

    int getAddress();
}
