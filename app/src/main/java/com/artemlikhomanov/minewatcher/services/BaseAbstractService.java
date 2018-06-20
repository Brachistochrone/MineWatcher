package com.artemlikhomanov.minewatcher.services;

import android.app.IntentService;
import android.net.ConnectivityManager;

import com.artemlikhomanov.minewatcher.model.realm.CauseOfStop;
import com.artemlikhomanov.minewatcher.model.realm.ConnectionAddress;
import com.artemlikhomanov.minewatcher.model.realm.LocationDiagram;
import com.artemlikhomanov.minewatcher.model.realm.LocationDiagramDot;
import com.artemlikhomanov.minewatcher.model.realm.LocationDiagramSegment;
import com.artemlikhomanov.minewatcher.model.realm.PreviousStops;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public abstract class BaseAbstractService extends IntentService {

    private final static String TAG = "BaseAbstractService";

    public BaseAbstractService(String name) {
        super(name);
    }

    /*метод проверки доступности сети для фоновых операций*/
    boolean isNetworkAvailableAndConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        boolean isNetworkAvailable = cm.getActiveNetworkInfo() != null;

        return isNetworkAvailable && cm.getActiveNetworkInfo().isConnected();
    }

    /*метод создает объект LocationDiagramDot, вносит в него данные и записывает в базу данных*/
    void recordData(Realm database, Float location, int resultCode, long primaryKey){

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

    /*метод создает объект PreviousStops, вносит в него данные и записывает в базу данных*/
    void recordData(Realm database, String cause, long primaryKey){

        RealmResults<CauseOfStop> stops = database.where(CauseOfStop.class)
                .equalTo("parentPrimaryKey", primaryKey)
                .notEqualTo("date", getDate())
                .findAll();

        if (!stops.isEmpty()) {
            database.beginTransaction();
            stops.deleteAllFromRealm();
            database.commitTransaction();
        }

        RealmResults<ConnectionAddress> addresses = database.where(ConnectionAddress.class)
                .equalTo("absoluteTime", primaryKey)
                .findAll();

        //Записать объект CauseOfStop в базу данных в текущей таблице
        database.beginTransaction();
        CauseOfStop stop = database.createObject(CauseOfStop.class, getAbsoluteTime());
        stop.setTime(getTime());
        stop.setCause(cause);
        stop.setParentPrimaryKey(primaryKey);
        addresses.first().addStop(stop);
        database.commitTransaction();
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

    /*получить дату вчерашнего дня*/
    private String getYesterdayDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy", Locale.getDefault());
        return dateFormat.format(calendar.getTime());
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

    /*метод проверяет существует ли в базе данных таблица с данными за текущие сутки*/
    private boolean isTodayStopListCreated(Realm database){
        /*проверить, есть ли в базе данных таблица с текущей датой*/
        RealmResults<PreviousStops> results = database.where(PreviousStops.class).equalTo("date", getDate()).findAll();
        return results.size() != 0;
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

    /*Метод создает новый объект таблицы причин выключения комбайна с датой текущих рабочих суток*/
    private void createTodayStopList(Realm database){
        /*Создать объект PreviousStops и внести данные*/
        final PreviousStops causes = new PreviousStops();
        causes.setDate(getDate());

        /*Записать объект PreviousStops в базу данных*/
        database.executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(Realm realm) {
                /* This will update an existing object with the same primary key
                or create a new object if there is no one with this primary key*/
                realm.copyToRealmOrUpdate(causes);
            }
        });
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

    /*Метод удаляет вчерашний объект таблицы причин выключения комбайна*/
    private void deleteYesterdayStopList(Realm database){
        /*получить дату вчерашнего дня*/
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy", Locale.getDefault());
        String yesterday = dateFormat.format(calendar.getTime());

        /*проверить, есть ли в базе данных таблица со вчерашней датой*/
        RealmResults<PreviousStops> results = database.where(PreviousStops.class).equalTo("date", yesterday).findAll();

        if (!results.isEmpty()) {

            /*найти все относящиеся к этой таблице объекты причин выключения*/
            RealmResults<CauseOfStop> causes = database.where(CauseOfStop.class)
                    .equalTo("causes.date", yesterday).findAll();

            database.beginTransaction();
            if (!causes.isEmpty()){
                causes.deleteAllFromRealm();
            }
            results.deleteAllFromRealm();
            database.commitTransaction();
        }
    }
}
