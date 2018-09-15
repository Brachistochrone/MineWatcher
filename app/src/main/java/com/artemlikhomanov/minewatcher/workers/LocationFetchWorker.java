package com.artemlikhomanov.minewatcher.workers;

import android.net.ConnectivityManager;
import android.support.annotation.NonNull;

import com.artemlikhomanov.minewatcher.controller.Cls450StateDataParser;
import com.artemlikhomanov.minewatcher.controller.ConnectionStateChangedListener;
import com.artemlikhomanov.minewatcher.controller.TCPClient;
import com.artemlikhomanov.minewatcher.model.Cls450StateDataItem;
import com.artemlikhomanov.minewatcher.model.Const;
import com.artemlikhomanov.minewatcher.model.realm.ConnectionAddress;
import com.artemlikhomanov.minewatcher.model.realm.LocationDiagram;
import com.artemlikhomanov.minewatcher.model.realm.LocationDiagramDot;
import com.artemlikhomanov.minewatcher.model.realm.LocationDiagramSegment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import androidx.work.Worker;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class LocationFetchWorker extends Worker implements TCPClient.MessageCallback, ConnectionStateChangedListener {

    private TCPClient m_tcpClient;
    private Realm m_DataBase;

    private int m_MessageCounter = 0;

    private long mPrimaryKey;

    @NonNull
    @Override
    public Result doWork() {

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
                return Result.SUCCESS;
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

        return Result.SUCCESS;
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

    /*метод проверки доступности сети для фоновых операций*/
    private boolean isNetworkAvailableAndConnected() {
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);

        boolean isNetworkAvailable = cm.getActiveNetworkInfo() != null;

        return isNetworkAvailable && cm.getActiveNetworkInfo().isConnected();
    }

    /*метод создает объект LocationDiagramDot, вносит в него данные и записывает в базу данных*/
    private void recordData(Realm database, Float location, int resultCode, long primaryKey){

        RealmResults<ConnectionAddress> addresses = database.where(ConnectionAddress.class)
                .equalTo("absoluteTime", primaryKey)
                .findAll();

        /*Проверить существует ли в базе диаграмма с текущей датой*/
        if (!isTodayDiagramCreated(database, primaryKey)) {
            /*создать диаграмму с сегоднешней датой*/
            createTodayDiagram(database, addresses, primaryKey);
            /*удалить позавчерашнюю диаграмму*/
            deleteOutOfDateDiagrams(database, primaryKey);
        }

        RealmResults<LocationDiagram> diagrams = database.where(LocationDiagram.class)
                .equalTo("parentPrimaryKey", primaryKey)
                .findAll()
                .sort("absoluteTime", Sort.DESCENDING);

        RealmResults<LocationDiagramSegment> segments = database.where(LocationDiagramSegment.class)
                .equalTo("parentPrimaryKey", primaryKey)
                .equalTo("diagrams.absoluteTime", diagrams.first().getAbsoluteTime())
                .findAll()
                .sort("absoluteTime", Sort.DESCENDING);

        if (!segments.first().isCompleted()) {
            if (location != null) {
                //Создать объект LocationDiagramDot и внести данные
                database.beginTransaction();

                LocationDiagramDot diagramDot = database.createObject(LocationDiagramDot.class, getAbsoluteTime());
                diagramDot.setTimeForChart(getTimeForChart());
                diagramDot.setTime(getTime());
                diagramDot.setLocation(location);
                diagramDot.setResultCode(resultCode);
                diagramDot.setParentPrimaryKey(primaryKey);

                segments.first().addLocationDot(diagramDot);

                database.commitTransaction();
            } else {
                database.beginTransaction();
                segments.first().complete(true);
                database.commitTransaction();
            }
        } else {
            if (location != null) {
                //Создать объект LocationDiagramSegment и внести данные
                database.beginTransaction();

                LocationDiagramSegment segment = database.createObject(LocationDiagramSegment.class, getAbsoluteTime());
                segment.setDate(getDate());
                segment.setParentPrimaryKey(primaryKey);
                //Создать объект LocationDiagramDot и внести данные
                LocationDiagramDot diagramDot = database.createObject(LocationDiagramDot.class, getAbsoluteTime());
                diagramDot.setTimeForChart(getTimeForChart());
                diagramDot.setTime(getTime());
                diagramDot.setLocation(location);
                diagramDot.setResultCode(resultCode);
                diagramDot.setParentPrimaryKey(primaryKey);

                segment.addLocationDot(diagramDot);

                diagrams.first().addSegment(segment);

                database.commitTransaction();
            }
        }
    }

    /*метод получения даты текущих рабочих суток в формате дд.мм.гг*/
    private String getDate(){
        Calendar calendar = Calendar.getInstance();
        /*вычислить время суток - час с дробной частью*/
        float time = (float)calendar.get(Calendar.HOUR_OF_DAY) + (calendar.get(Calendar.MINUTE)/60.0f);

        /*если время суток больше или равно 8 часам - это текущие рабочие сутки*/
        if (time >= 8.0f) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy", Locale.getDefault());
            return dateFormat.format(calendar.getTime());
        } else {
            /*если время суток меньше 8 часов - наступил следующий календарный день,
             * но текущие рабочие сутки продолжаются*/
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy", Locale.getDefault());
            return dateFormat.format(calendar.getTime());
        }
    }

    /*метод получения текущего времени в формате чч:мм*/
    private String getTime(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return dateFormat.format(calendar.getTime());
    }

    /*метод получения текущего времени в милисекундах*/
    private long getAbsoluteTime(){
        Calendar calendar = Calendar.getInstance();
        return calendar.getTimeInMillis();
    }

    /*метод получения текущего времени рабочих суток - от 0 до 24 часов*/
    private float getTimeForChart(){
        Calendar calendar = Calendar.getInstance();
        /*вычислить время суток - час с дробной частью*/
        float time = (float)calendar.get(Calendar.HOUR_OF_DAY) + (calendar.get(Calendar.MINUTE)/60.0f);

        if (time >= 8.0f) {
            return time - 8.0f;
        } else {
            return time + 16.0f;
        }
    }

    /*метод проверяет существует ли в базе данных таблица с данными за текущие сутки*/
    private boolean isTodayDiagramCreated(Realm database, long primaryKey){
        /*проверить, есть ли в базе данных таблица с текущей датой*/
        RealmResults<LocationDiagram> diagrams = database.where(LocationDiagram.class)
                .equalTo("parentPrimaryKey", primaryKey)
                .equalTo("date", getDate())
                .findAll();
        return diagrams.size() != 0;
    }

    /*Метод создает новый объект таблицы диаграммы с датой текущих рабочих суток*/
    private void createTodayDiagram(final Realm database, RealmResults<ConnectionAddress> addresses, long primaryKey){
        final String date = getDate();
        /*Записать объект LocationDiagram в базу данных*/
        database.beginTransaction();
        LocationDiagram diagram = database.createObject(LocationDiagram.class, getAbsoluteTime());
        diagram.setDate(date);
        diagram.setParentPrimaryKey(primaryKey);
        /*Создать объект LocationDiagramSegment и внести данные*/
        LocationDiagramSegment segment = database.createObject(LocationDiagramSegment.class, getAbsoluteTime());
        segment.setDate(date);
        segment.setParentPrimaryKey(primaryKey);
        diagram.addSegment(segment);
        addresses.first().addDiagram(diagram);
        database.commitTransaction();
    }

    /*Метод удаляет устаревшие объекты таблиц диаграмм со всеми входящими временными точками
     * оставляет только сегоднешнюю и одну прошлую*/
    private void deleteOutOfDateDiagrams(Realm database, long primaryKey){

        RealmResults<LocationDiagram> diagrams = database.where(LocationDiagram.class)
                .equalTo("parentPrimaryKey", primaryKey)
                .findAll()
                .sort("absoluteTime", Sort.DESCENDING);

        if (diagrams.size() > 2) {
            for (int i = 2; i < diagrams.size(); i++) {
                /*найти все относящиеся к этой диаграмме сегменты*/
                RealmResults<LocationDiagramSegment> segments = database.where(LocationDiagramSegment.class)
                        .equalTo("parentPrimaryKey", primaryKey)
                        .equalTo("diagrams.absoluteTime", diagrams.get(i).getAbsoluteTime())
                        .findAll();
                for (LocationDiagramSegment segment : segments) {
                    /*найти все относящиеся к этому сегменту временные точки и удалить их*/
                    RealmResults<LocationDiagramDot> locationDots = database.where(LocationDiagramDot.class)
                            .equalTo("parentPrimaryKey", primaryKey)
                            .equalTo("segments.absoluteTime", segment.getAbsoluteTime())
                            .findAll();
                    database.beginTransaction();
                    locationDots.deleteAllFromRealm();
                    database.commitTransaction();
                }
                database.beginTransaction();
                /*удалить все сегменты относящиеся к этой диаграмме*/
                segments.deleteAllFromRealm();
                /*удалить саму диаграмму*/
                diagrams.deleteFromRealm(i);
                database.commitTransaction();
            }
        }
    }
}
