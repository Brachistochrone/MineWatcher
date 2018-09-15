package com.artemlikhomanov.minewatcher.application;

import android.app.Application;

import com.artemlikhomanov.minewatcher.workers.LocationFetchWorker;
import com.crashlytics.android.Crashlytics;

import java.util.concurrent.TimeUnit;

import androidx.work.Constraints;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MineWatcherApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize Realm
        Realm.init(this);
        // The Realm file will be located in Context.getFilesDir() with name "mineWatcher.realm"
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name("mineWatcher.realm")
                .build();
        // Setting a default configuration in Application class, will ensure that it is available in the rest of code
        Realm.setDefaultConfiguration(realmConfiguration);

//        /*Установить циклический таймер для запуска службы LocationFetchService*/
//        if (!LocationFetchService.isServiceAlarmOn(getApplicationContext())) {
//            LocationFetchService.setServiceAlarm(getApplicationContext());
//        }

        /*Установить циклический таймер для запуска службы LocationFetchWorker*/
        PeriodicWorkRequest.Builder locationFetchBuilder = new PeriodicWorkRequest.Builder(LocationFetchWorker.class,
                                                                                    6, TimeUnit.MINUTES);
        Constraints constraints = new Constraints.Builder()
                                    .setRequiresBatteryNotLow(true)
                                    .build();
        PeriodicWorkRequest locationFetchWork = locationFetchBuilder
                                                .setConstraints(constraints)
                                                .build();
        WorkManager.getInstance().enqueue(locationFetchWork);

        Fabric.with(this, new Crashlytics());
    }
}
