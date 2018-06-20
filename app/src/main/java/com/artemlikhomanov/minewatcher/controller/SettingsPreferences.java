package com.artemlikhomanov.minewatcher.controller;

import android.content.Context;
import android.preference.PreferenceManager;

public class SettingsPreferences {

    private static final String PREF_LOCATION_SERVICE_USAGE = "PREF_LOCATION_SERVICE_USAGE";

    /*метод проверки используется ли LocationFetchService*/
    public static boolean isLocationFetchServiceUsed(Context context) {
        /*второй параметр - что должно вернуться если нет записи с заданным ключом*/
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(PREF_LOCATION_SERVICE_USAGE, true);
    }

    /*метод записывает используется ли LocationFetchService*/
    public static void setLocationFetchServiceUsage(Context context, boolean isUsed) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(PREF_LOCATION_SERVICE_USAGE, isUsed)
                .apply();
    }
}
