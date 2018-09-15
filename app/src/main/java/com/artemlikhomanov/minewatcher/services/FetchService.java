package com.artemlikhomanov.minewatcher.services;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.artemlikhomanov.minewatcher.controller.Cls450StateDataParser;
import com.artemlikhomanov.minewatcher.controller.ConnectionStateChangedListener;
import com.artemlikhomanov.minewatcher.controller.TCPClient;
import com.artemlikhomanov.minewatcher.events.ConnectionStateChangedEvent;
import com.artemlikhomanov.minewatcher.events.NotifyDataSetChangedEvent;
import com.artemlikhomanov.minewatcher.model.Cls450StateDataItem;
import com.artemlikhomanov.minewatcher.model.Const;

import org.greenrobot.eventbus.EventBus;

import io.realm.Realm;

import static com.artemlikhomanov.minewatcher.controller.TCPClient.*;

/*Служба для подключения к серверу и получения от него данных в фоновом потоке*/
public class FetchService extends BaseAbstractService implements ConnectionStateChangedListener, MessageCallback {

    private final static String TAG = "FetchService";
    private final static String IP_ADDRESS_KEY = "IP_ADDRESS_KEY";
    private final static String PORT_NUMBER_KEY = "PORT_NUMBER_KEY";
    private final static String PRIMARY_KEY_KEY = "PRIMARY_KEY_KEY";

    private TCPClient m_tcpClient;
    private Realm m_DataBase;

    private long mPrimaryKey;

    private int m_MessageCounter = 0;

    public FetchService() {
        super(TAG);                                                                                 //задается имя рабочего потока
        /*Сделать службу START_NOT_STICKY. Если система остановит службу, то она не будет потом перезапущена
        * При START_REDELIVER_INTENT на API19 после удаления задачи из списка задач система перезапускала службу*/
        setIntentRedelivery(false);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String ipAddress = "";
        String portNumber = "";

//        if (LocationFetchService.isServiceAlarmOn(getApplicationContext())) {
//            LocationFetchService.cancelServiceAlarm(getApplicationContext());
//        }

        // Obtain a Realm instance
        m_DataBase = Realm.getDefaultInstance();

        if (intent != null) {
            /*получить сохраненные значения ip адреса и номера порта*/
            ipAddress = intent.getStringExtra(IP_ADDRESS_KEY);
            portNumber = intent.getStringExtra(PORT_NUMBER_KEY);
            mPrimaryKey = intent.getLongExtra(PRIMARY_KEY_KEY, (long) 0.0);
        }

        try{
            /*проверить наличие и доступность сети*/
            if (!isNetworkAvailableAndConnected()) {
                /*Отправить сообщение для обновления UI*/
                notifyConnectionStateChanged(Const.NETWORK_NOT_AVAILABLE);
                return;
            }
            /*Запустить TCP клиента*/
            try{
                m_tcpClient = new TCPClient(null, ipAddress, portNumber, this, this);
            } catch (NullPointerException e){
                Log.d(TAG, "Caught null pointer exception");
                e.printStackTrace();
            }
            m_tcpClient.run();

        } finally {
            m_DataBase.close();
            /*Установить циклический таймер для запуска службы LocationFetchService*/
//            if (!LocationFetchService.isServiceAlarmOn(getApplicationContext())) {
//                LocationFetchService.setServiceAlarm(getApplicationContext());
//            }
//            notifyConnectionStateChanged(Const.SERVER_DISCONNECTED);
        }
    }

    @Override
    public void onDestroy() {
        if (m_tcpClient != null) {
            m_tcpClient.stopClient();
        }
        super.onDestroy();
    }

    /*Обработка полученного от сервера сообщения*/
    @Override
    public void callbackMessageReceiver(String message) {
        m_MessageCounter++;
        /*Создать новый объект Cls450StateDataItem и заполнить его данными из сообщения*/
        Cls450StateDataItem dataItem = Cls450StateDataParser.parseItem(message, getApplicationContext(), this);

        if (dataItem != null) {
            /*Записать причину выключения комбайна в базу данных если она есть*/
            if (!dataItem.getLastStopCause().equals("")){
                recordData(m_DataBase, dataItem.getLastStopCause(), mPrimaryKey);
            }

            /*Разослать сообщение через EventBus, передать ссылку на dataItem*/
            EventBus.getDefault().post(new NotifyDataSetChangedEvent(dataItem));
            /*Разослать сообщение через EventBus что связь в порядке*/
            notifyConnectionStateChanged(Const.CONNECTION_ESTABLISHED);

            //записывать данные примерно раз в минуту
            if (m_MessageCounter == 120) {
                /*записать в базу данных положение комбайна*/
                recordData(m_DataBase, Float.valueOf(dataItem.getShearerLocation()), Const.RESULT_OK, mPrimaryKey);
                m_MessageCounter = 0;
            }
        } else {
            if (m_MessageCounter == 120) {
                /*записать в базу данных положение комбайна*/
                recordData(m_DataBase, null, Const.RESULT_CANCELED, mPrimaryKey);
                m_MessageCounter = 0;
            }
        }
    }

    @Override
    public void notifyConnectionStateChanged(int message) {
        switch (message) {
            case 0:
                break;
            case Const.SERVER_NOT_AVAILABLE:
                EventBus.getDefault().post(new ConnectionStateChangedEvent(Const.SERVER_NOT_AVAILABLE));
                recordData(m_DataBase, null, Const.SERVER_NOT_AVAILABLE, mPrimaryKey);
                break;
            case Const.NETWORK_NOT_AVAILABLE:
                EventBus.getDefault().post(new ConnectionStateChangedEvent(Const.NETWORK_NOT_AVAILABLE));
                recordData(m_DataBase, null, Const.NETWORK_NOT_AVAILABLE, mPrimaryKey);
                break;
            case Const.UNDERGROUND_MODEM_NOT_AVAILABLE:
                EventBus.getDefault().post(new ConnectionStateChangedEvent(Const.UNDERGROUND_MODEM_NOT_AVAILABLE));
                if (m_MessageCounter == 120) {
                    recordData(m_DataBase, null, Const.UNDERGROUND_MODEM_NOT_AVAILABLE, mPrimaryKey);
                    m_MessageCounter = 0;
                }
                break;
            case Const.NO_CONNECTION_WITH_SHEARER:
                EventBus.getDefault().post(new ConnectionStateChangedEvent(Const.NO_CONNECTION_WITH_SHEARER));
                if (m_MessageCounter == 120) {
                    recordData(m_DataBase, null, Const.NO_CONNECTION_WITH_SHEARER, mPrimaryKey);
                    m_MessageCounter = 0;
                }
                break;
            case Const.CONNECTING:
                break;
            case Const.CONNECTION_ESTABLISHED:
                EventBus.getDefault().post(new ConnectionStateChangedEvent(Const.CONNECTION_ESTABLISHED));
                break;
            case Const.SERVER_DISCONNECTED:
                EventBus.getDefault().post(new ConnectionStateChangedEvent(Const.SERVER_DISCONNECTED));
                break;
        }
    }

    public static Intent newIntent(Context context, String ipAddress, String portNumber, long primaryKey) {
        Intent intent = new Intent(context, FetchService.class);
        intent.putExtra(IP_ADDRESS_KEY, ipAddress);
        intent.putExtra(PORT_NUMBER_KEY, portNumber);
        intent.putExtra(PRIMARY_KEY_KEY, primaryKey);
        return intent;
    }
}
