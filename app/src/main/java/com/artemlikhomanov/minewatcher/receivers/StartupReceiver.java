package com.artemlikhomanov.minewatcher.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.artemlikhomanov.minewatcher.controller.SettingsPreferences;
import com.artemlikhomanov.minewatcher .services.LocationFetchService;

/*Класс - автономный приемник, вызывается при загрузке устройства
 и устанавливает циклический таймер для запуска службы LocationFetchService*/
public class StartupReceiver extends BroadcastReceiver {

    private static final String TAG = "StartupReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")){
          /*Проверить нужно ли запускать LocationFetchService*/
            if (SettingsPreferences.isLocationFetchServiceUsed(context)) {
                /*Установить циклический таймер для запуска службы LocationFetchService*/
                LocationFetchService.setServiceAlarm(context);
            }
        }
    }
}
