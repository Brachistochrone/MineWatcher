package com.artemlikhomanov.minewatcher.controller;

/*Callback Interface for sending messages when connection state changes*/
public interface ConnectionStateChangedListener {

    void notifyConnectionStateChanged(int message);
}
