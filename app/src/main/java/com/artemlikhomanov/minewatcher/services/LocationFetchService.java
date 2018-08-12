package com.artemlikhomanov.minewatcher.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.artemlikhomanov.minewatcher.controller.Cls450StateDataParser;
import com.artemlikhomanov.minewatcher.controller.ConnectionStateChangedListener;
import com.artemlikhomanov.minewatcher.controller.SettingsPreferences;
import com.artemlikhomanov.minewatcher.controller.TCPClient;
import com.artemlikhomanov.minewatcher.controller.TCPClient.MessageCallback;
import com.artemlikhomanov.minewatcher.model.Cls450StateDataItem;
import com.artemlikhomanov.minewatcher.model.Const;
import com.artemlikhomanov.minewatcher.model.realm.ConnectionAddress;

import io.realm.Realm;
import io.realm.RealmResults;

/*Класс службы, которая периодически вызывается, подключается к серверу, получает одну посылку,
* извлекает местоположение комбайна, записывает в базу данных и умирает. Если не удалось получить
* полезную посылку - записывает время и причину неудачи*/
public class LocationFetchService extends BaseAbstractService implements MessageCallback, ConnectionStateChangedListener {

    private final static String TAG = "LocationFetchService";

    private TCPClient m_tcpClient;
    private Realm m_DataBase;

    private int m_MessageCounter = 0;

    private long mPrimaryKey;

    public LocationFetchService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        // Obtain a Realm instance
        m_DataBase = Realm.getDefaultInstance();
        RealmResults<ConnectionAddress> addresses = m_DataBase.where(ConnectionAddress.class).findAll();

        try {
            /*проверить наличие и доступность сети*/
            if (!isNetworkAvailableAndConnected()) {
                for (ConnectionAddress address : addresses) {
                    mPrimaryKey = address.getPrimaryKey();
                    notifyConnectionStateChanged(Const.NETWORK_NOT_AVAILABLE);
                }
                return;
            }

            for (ConnectionAddress address : addresses) {
                String ipAddress = address.getIpAddress();
                String portNumber = address.getPortNumber();
                mPrimaryKey = address.getPrimaryKey();
                m_MessageCounter = 0;

                /*проверить, что данные для подключения есть*/
                if (ipAddress.equals("") || portNumber.equals("")) {
                    recordData(m_DataBase, null, Const.SERVER_NOT_AVAILABLE, mPrimaryKey);
                    continue;
                }

                /*Запустить TCP клиента*/
                try{
                    m_tcpClient = new TCPClient(null, ipAddress, portNumber, this, this);
                } catch (NullPointerException e){
                    e.printStackTrace();
                }
                m_tcpClient.run();
            }
        } finally {
            m_DataBase.close();
        }
    }

    /*Обработка полученного от сервера сообщения*/
    @Override
    public void callbackMessageReceiver(String message) {
        m_MessageCounter++;
        /*Создать новый объект Cls450StateDataItem и заполнить его данными из сообщения*/
        Cls450StateDataItem dataItem = Cls450StateDataParser.parseItem(message, getApplicationContext(), this);

        if (dataItem != null) {
            /*если получена полезная посылка - записать в базу данных и отключить клиент*/
            recordData(m_DataBase, Float.valueOf(dataItem.getShearerLocation()), Const.RESULT_OK, mPrimaryKey);
            m_tcpClient.stopClient();
        } else {
            /*если 20 сообщений подряд не было полезной посылки - остановить клиент*/
            if (m_MessageCounter == 20) {
                recordData(m_DataBase, null, Const.RESULT_CANCELED, mPrimaryKey);
                m_tcpClient.stopClient();
            }
        }
    }

    @Override
    public void notifyConnectionStateChanged(int message) {
        switch (message){
            case 0:
                break;
            case Const.SERVER_NOT_AVAILABLE:
                recordData(m_DataBase, null, Const.SERVER_NOT_AVAILABLE, mPrimaryKey);
                break;
            case Const.NETWORK_NOT_AVAILABLE:
                recordData(m_DataBase, null, Const.NETWORK_NOT_AVAILABLE, mPrimaryKey);
                break;
            case Const.UNDERGROUND_MODEM_NOT_AVAILABLE:
                recordData(m_DataBase, null, Const.UNDERGROUND_MODEM_NOT_AVAILABLE, mPrimaryKey);
                m_tcpClient.stopClient();
                break;
            case Const.NO_CONNECTION_WITH_SHEARER:
                recordData(m_DataBase, null, Const.NO_CONNECTION_WITH_SHEARER, mPrimaryKey);
                m_tcpClient.stopClient();
                break;
            case Const.CONNECTING:
                break;
            case Const.CONNECTION_ESTABLISHED:
                break;
        }
    }

    @NonNull
    public static Intent newIntent(Context context) {
        return new Intent(context, LocationFetchService.class);
    }

    /*Метод установки повторяющегося таймера на запуск службы*/
    public static void setServiceAlarm(Context context) {
        Intent intent = newIntent(context);
        PendingIntent pendingIntent = PendingIntent.getService(context, 1, intent, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        /*установить таймер, который каждые 6 минут запускает данную службу, первый запуск через 6 минуту*/
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, (SystemClock.elapsedRealtime() + 360000), 360000, pendingIntent);
    }

    /*Метод проверки установлен ли таймер запуска службы*/
    public static boolean isServiceAlarmOn(Context context) {
        Intent intent = newIntent(context);
        /*Если такой PendingIntent уже есть в системе, то вернется ссылка на него,
        * если нет - вернется null*/
        PendingIntent pendingIntent = PendingIntent.getService(context, 1, intent, PendingIntent.FLAG_NO_CREATE);
        return pendingIntent != null;
    }

    /*Метод отмены таймера и PendingIntent*/
    public static void cancelServiceAlarm(Context context) {
        Intent intent = newIntent(context);
        PendingIntent pendingIntent = PendingIntent.getService(context, 1, intent, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();
    }
}
