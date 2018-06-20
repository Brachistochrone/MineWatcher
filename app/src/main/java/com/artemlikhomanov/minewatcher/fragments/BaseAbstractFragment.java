package com.artemlikhomanov.minewatcher.fragments;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.artemlikhomanov.minewatcher.events.ConnectionStateChangedEvent;
import com.artemlikhomanov.minewatcher.events.NotifyDataSetChangedEvent;
import com.artemlikhomanov.minewatcher.model.Cls450StateDataItem;
import com.artemlikhomanov.minewatcher.model.Const;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public abstract class BaseAbstractFragment extends Fragment {

    static final String KEY_CONNECTION_STATE = "KEY_CONNECTION_STATE";

    int mConnectionStateMessage = 0;

    abstract void updateUI(Cls450StateDataItem dataItem);

    abstract void resetUI();

    abstract void initialiseUI(View view);

    abstract int getLayoutResId();

    abstract void valuesInitialisation();

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_CONNECTION_STATE, mConnectionStateMessage);
    }

    /*Шаблонный метод*/
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(getLayoutResId(), container, false);

        if (savedInstanceState != null) {
            mConnectionStateMessage = savedInstanceState.getInt(KEY_CONNECTION_STATE);
        }

        initialiseUI(view);
        hook();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (isScreenOn()) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    /*Метод задает текст и цвет текста для TextView*/
    void setTextAndColor(TextView textView, int textRes, Integer colorRes){
        textView.setText(textRes);

        if (colorRes != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                textView.setTextColor(getResources().getColor(colorRes, null));
            } else {
                textView.setTextColor(getResources().getColor(colorRes));
            }
        }
    }

    /*Метод задает текст и цвет текста для TextView*/
    void setTextAndColor(TextView textView, String text, Integer colorRes){
        if (!textView.getText().toString().equals(text)){
            textView.setText(text);

            if (colorRes != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    textView.setTextColor(getResources().getColor(colorRes, null));
                } else {
                    textView.setTextColor(getResources().getColor(colorRes));
                }
            }
        }
    }

    /*Метод задает текст для TextView*/
    void setText(TextView view, String text){
        if (!view.getText().toString().equals(text)){
            view.setText(text);
        }
    }

    /*Метод-перехватчик, переопределяется, если надо что-то вставить в алгоритм*/
    void hook() {

    }

    boolean isScreenOn() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            DisplayManager displayManager = (DisplayManager) getContext().getSystemService(Context.DISPLAY_SERVICE);
            return displayManager.getDisplays()[0].getState() != Display.STATE_OFF;
        }
        PowerManager powerManager = (PowerManager) getContext().getSystemService(Context.POWER_SERVICE);
        return powerManager.isScreenOn();
    }

    /*Метод вызывается когда по EventBus приходит сообщение NotifyDataSetChangedEvent
    * и выполняется в главном потоке*/
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDataSetChangedEvent(NotifyDataSetChangedEvent event) {
        updateUI(event.getDataItem());
    }

    /*Метод вызывается когда по EventBus приходит сообщение ConnectionStateChangedEvent
    * и выполняется в главном потоке*/
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onConnectionStateChangedEvent(ConnectionStateChangedEvent event) {

        mConnectionStateMessage = event.getMessage();

        switch (mConnectionStateMessage) {
            case 0:
                break;
            case Const.SERVER_NOT_AVAILABLE:
                resetUI();
                break;
            case Const.NETWORK_NOT_AVAILABLE:
                resetUI();
                break;
            case Const.UNDERGROUND_MODEM_NOT_AVAILABLE:
                resetUI();
                break;
            case Const.NO_CONNECTION_WITH_SHEARER:
                resetUI();
                break;
            case Const.CONNECTING:
                break;
            case Const.CONNECTION_ESTABLISHED:
                break;
        }
    }
}
