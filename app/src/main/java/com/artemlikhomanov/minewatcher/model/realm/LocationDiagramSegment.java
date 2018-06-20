package com.artemlikhomanov.minewatcher.model.realm;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/*Класс модели для Realm, содержит непрерывный набор точек местоположения комбайна с достоверными данными*/
public class LocationDiagramSegment extends RealmObject {

    @Required
    @PrimaryKey
    private     Long                                absoluteTime;

    @Required
    private     Long                                parentPrimaryKey;

    private     boolean                             completed = false;

    @Required
    private     String                              date;                                           //дата рабочих суток

    private RealmList<LocationDiagramDot> dots;                                                     //список точек для построения данного сегмента диаграммы

    @LinkingObjects("segments")
    private final RealmResults<LocationDiagram> diagrams = null;

    public void setAbsoluteTime(Long absoluteTime) {
        this.absoluteTime = absoluteTime;
    }

    public Long getAbsoluteTime() {
        return absoluteTime;
    }

    public void addLocationDot(LocationDiagramDot dot) {
        if (!completed) {
            dots.add(dot);
        }
    }

    public boolean isCompleted () {
        return completed;
    }

    public void complete(boolean completed) {
        this.completed = completed;
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
