package com.artemlikhomanov.minewatcher.model.realm;

import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/*Класс модели для Realm, содержащий информацию о положении комбайна в конкретный момент времени*/
public class LocationDiagramDot extends RealmObject {

    @Required
    @PrimaryKey
    private     Long            absoluteTime;

    @Required
    private     Long            parentPrimaryKey;

    private     Float           timeForChart;                                                       //x-value - от 0 до 24 часов
    @Required
    private     String          time;                                                               //legend of x-value

    private     Float           location;                                                           //y-value
    @Required
    private     Integer         resultCode;

    @LinkingObjects("dots")
    private final RealmResults<LocationDiagramSegment> segments = null;                             //список отрезков диаграмм, которым принадлежит эта точка

    public Long getAbsoluteTime() {
        return absoluteTime;
    }

    public void setAbsoluteTime(Long absoluteTime) {
        this.absoluteTime = absoluteTime;
    }

    public void setTimeForChart(Float timeForChart) {
        this.timeForChart = timeForChart;
    }

    public float getTimeForChart () {
        return timeForChart;
    }

    private Float getLocation() {
        return location;
    }

    public void setLocation(Float location) {
        this.location = location;
    }

    private Integer getResultCode() {
        return resultCode;
    }

    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Object[] getAll() {
        return new Object[]{getAbsoluteTime(), getTime(), getLocation(), getResultCode()};
    }

    public void setParentPrimaryKey(Long parentPrimaryKey) {
        this.parentPrimaryKey = parentPrimaryKey;
    }
}
