package com.artemlikhomanov.minewatcher.model.realm;

import android.content.Intent;
import android.support.annotation.NonNull;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class ConnectionAddress extends RealmObject {

    public ConnectionAddress() {

    }

    public ConnectionAddress (Long absoluteTime) {
        this.absoluteTime = absoluteTime;
    }

    @Required
    @PrimaryKey
    private     Long                                absoluteTime;

    @Required
    private     String                              ipAddress       =   "";

    @Required
    private     String                              portNumber      =   "";

    private     String                              name            =   "";

    @Required
    private     Integer                             shearerType;

    private RealmList<CauseOfStop>                  stops;                                          //список причин выключения комбайна

    private RealmList<LocationDiagram>              diagrams;                                       //список диаграмм местоположения комбайна

    @NonNull
    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        name = ipAddress;
    }

    @NonNull
    public String getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(String portNumber) {
        this.portNumber = portNumber;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrimaryKey() {
        return absoluteTime;
    }

    public void addStop(CauseOfStop stop) {
        stops.add(stop);
    }

    public void addDiagram(LocationDiagram diagram) {
        diagrams.add(diagram);
    }

    public Integer getShearerType() {
        return shearerType;
    }

    public void setShearerType(Integer shearerType) {
        this.shearerType = shearerType;
    }
}
