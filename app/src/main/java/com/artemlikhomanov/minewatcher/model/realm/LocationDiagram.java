package com.artemlikhomanov.minewatcher.model.realm;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/*Класс модели для Realm, содержащий данные для графика положения комбайна за сутки*/
public class LocationDiagram extends RealmObject {

    @Required
    @PrimaryKey
    private     Long                                absoluteTime;

    @Required
    private     Long                                parentPrimaryKey;

    @Required
    private     String                              date;                                           //дата рабочих суток

    private RealmList<LocationDiagramSegment>       segments;                                       //список отрезков для построения данной диаграммы

    @LinkingObjects("diagrams")
    private final RealmResults<ConnectionAddress>   addresses = null;                               //список диаграмм местоположения комбайна

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setAbsoluteTime(Long absoluteTime) {
        this.absoluteTime = absoluteTime;
    }

    public Long getAbsoluteTime() {
        return absoluteTime;
    }

    public void addSegment(LocationDiagramSegment segment) {
        segments.add(segment);
    }

    public void setParentPrimaryKey(Long parentPrimaryKey) {
        this.parentPrimaryKey = parentPrimaryKey;
    }
}
