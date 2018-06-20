package com.artemlikhomanov.minewatcher.services;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.artemlikhomanov.minewatcher.events.ConnectionStateChangedEvent;
import com.artemlikhomanov.minewatcher.model.Const;

import org.greenrobot.eventbus.EventBus;

/*Служба для остановки FetchService если она запущена в фоне*/
public class FetchingKillerService extends IntentService {

    private final static String TAG = "FetchingKillerService";

     public FetchingKillerService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        /*Проверить запущена ли служба FetchService*/
        if (isServiceRunning(FetchService.class)) {
            /*Остановить службу FetchService*/
            Intent fetchServiceIntent = FetchService.newIntent(getApplicationContext(), null, null, (long) 0.0);
            getApplicationContext().stopService(fetchServiceIntent);
            EventBus.getDefault().post(new ConnectionStateChangedEvent(Const.SERVER_DISCONNECTED));
        }
        /*Установить циклический таймер для запуска службы LocationFetchService*/
        if (!LocationFetchService.isServiceAlarmOn(getApplicationContext())) {
            LocationFetchService.setServiceAlarm(getApplicationContext());
        }
    }

    @NonNull
    public static Intent newIntent(Context context) {
        return new Intent(context, FetchingKillerService.class);
    }

    /*Метод проверки запущена ли служба*/
    private boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    /*Метод установки таймера на запуск службы*/
    public static void setServiceAlarm(Context context) {
        Intent intent = newIntent(context);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, (SystemClock.elapsedRealtime() + Const.FETCHING_KILLER_TRIGGER_TIME), pendingIntent);
    }

    /*Метод проверки установлен ли таймер запуска службы*/
    public static boolean isServiceAlarmOn(Context context) {
        Intent intent = newIntent(context);
        /*Если такой PendingIntent уже есть в системе, то вернется ссылка на него,
        * если нет - вернется null*/
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_NO_CREATE);
        return pendingIntent != null;
    }

    /*Метод отмены таймера и PendingIntent*/
    public static void cancelServiceAlarm(Context context) {
        Intent intent = newIntent(context);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();
    }
}
