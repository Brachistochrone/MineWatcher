package com.artemlikhomanov.minewatcher.model.realm;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/*Класс модели для Realm, содержащий причины выключения комбайна за сутки*/
public class PreviousStops extends RealmObject {

    @Required
    @PrimaryKey
    private     String                              date;                                           //дата рабочих суток

    private RealmList<CauseOfStop>                  causes;                                         //список причин выключения комбайна


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void addCauseOfStop(CauseOfStop cause) {
        causes.add(cause);
    }
}
