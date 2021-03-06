package com.artemlikhomanov.minewatcher.application;

import android.app.Application;

import com.artemlikhomanov.minewatcher.services.LocationFetchService;
import com.crashlytics.android.Crashlytics;
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

        /*Установить циклический таймер для запуска службы LocationFetchService*/
        if (!LocationFetchService.isServiceAlarmOn(getApplicationContext())) {
            LocationFetchService.setServiceAlarm(getApplicationContext());
        }

        Fabric.with(this, new Crashlytics());
    }
}
