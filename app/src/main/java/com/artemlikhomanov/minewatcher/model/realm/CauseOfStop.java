package com.artemlikhomanov.minewatcher.model.realm;

import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/*Класс модели для Realm, содержащий информацию о причине выключения комбайна*/
public class CauseOfStop extends RealmObject {

    @Required
    @PrimaryKey
    private     Long                                absoluteTime;

    @Required
    private     Long                                parentPrimaryKey;

    @Required
    private     String                              time;

    @Required
    private     String                              date;                                           //дата рабочих суток

    @Required
    private     String                              cause;                                          //причина выключения

    @LinkingObjects("stops")
    private final RealmResults<ConnectionAddress>   address = null;                                 //список причин выключения комбайна

    public Long getAbsoluteTime() {
        return absoluteTime;
    }

    public void setAbsoluteTime(Long absoluteTime) {
        this.absoluteTime = absoluteTime;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getCause() {
        return time + " " + cause + "\n";
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setParentPrimaryKey(Long parentPrimaryKey) {
        this.parentPrimaryKey = parentPrimaryKey;
    }
}
