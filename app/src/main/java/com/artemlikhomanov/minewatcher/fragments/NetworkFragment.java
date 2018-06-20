package com.artemlikhomanov.minewatcher.fragments;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Display;

import com.artemlikhomanov.minewatcher.controller.ConnectionStateChangedListener;
import com.artemlikhomanov.minewatcher.events.ConnectionStateChangedEvent;
import com.artemlikhomanov.minewatcher.model.Const;
import com.artemlikhomanov.minewatcher.services.FetchService;
import com.artemlikhomanov.minewatcher.services.FetchingKillerService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class NetworkFragment extends Fragment {

    private String mIpAddress;
    private String mPortNumber;

    private long mPrimaryKey;

    private int mConnectionStateMessage = 0;

    private ConnectionStateChangedListener mConnectionStateListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onStart() {
        super.onStart();

        if(!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        stopServiceKiller();
        startFetchService();
    }

    @Override
    public void onStop() {
        super.onStop();
        startServiceKiller();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        stopFetchService();
    }

    private void startFetchService() {
        if (isScreenOn() && !isServiceRunning(FetchService.class) && mConnectionStateMessage == 0) {
            Intent intent = FetchService.newIntent(getContext(), mIpAddress, mPortNumber, mPrimaryKey);
            getContext().startService(intent);
        }
    }

    private void stopFetchService() {
        getContext().stopService(new Intent(getContext(), FetchService.class));
    }

    private void startServiceKiller() {
        if (isServiceRunning(FetchService.class)) {
            FetchingKillerService.setServiceAlarm(getContext());
        }
    }

    private void stopServiceKiller() {
        if (FetchingKillerService.isServiceAlarmOn(getContext())) {
            FetchingKillerService.cancelServiceAlarm(getContext());
        }
    }

    private boolean isScreenOn() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            DisplayManager displayManager = (DisplayManager) getContext().getSystemService(Context.DISPLAY_SERVICE);
            return displayManager.getDisplays()[0].getState() != Display.STATE_OFF;
        }
        PowerManager powerManager = (PowerManager) getContext().getSystemService(Context.POWER_SERVICE);
        return powerManager.isScreenOn();
    }

    private boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getContext().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public void setIpAddress(String ipAddress) {
        mIpAddress = ipAddress;
    }

    public void setPortNumber(String portNumber) {
        mPortNumber = portNumber;
    }

    public void setPrimaryKey(long primaryKey) {
        mPrimaryKey = primaryKey;
    }

    public void setConnectionStateListener(ConnectionStateChangedListener listener) {
        mConnectionStateListener = listener;
    }

    public void removeListeners() {
        mConnectionStateListener = null;
    }

    public int getConnectionStateMessage() {
        return mConnectionStateMessage;
    }

    @NonNull
    public static Fragment newInstance() {
        return new NetworkFragment();
    }

    /*Метод вызывается когда по EventBus приходит сообщение ConnectionStateChangedEvent
    * и выполняется в главном потоке*/
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onConnectionStateChangedEvent(ConnectionStateChangedEvent event) {
        if (mConnectionStateMessage != event.getMessage() && mConnectionStateMessage != Const.SERVER_DISCONNECTED) {
            mConnectionStateMessage = event.getMessage();
            if (mConnectionStateListener != null) {
                mConnectionStateListener.notifyConnectionStateChanged(mConnectionStateMessage);
            }
        }
    }
}
