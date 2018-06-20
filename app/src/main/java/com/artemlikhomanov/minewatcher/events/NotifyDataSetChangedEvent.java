package com.artemlikhomanov.minewatcher.events;

import com.artemlikhomanov.minewatcher.model.Cls450StateDataItem;

/*Класс для рассылки по EventBus для уведомления UI об обновившихся данных в Cls450StateDataSet*/
public class NotifyDataSetChangedEvent {

    private Cls450StateDataItem m_dataItem;

    public NotifyDataSetChangedEvent (Cls450StateDataItem dataItem) {
        m_dataItem = dataItem;
    }

    public Cls450StateDataItem getDataItem() {
        return m_dataItem;
    }
}
