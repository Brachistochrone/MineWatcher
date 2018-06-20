package com.artemlikhomanov.minewatcher.events;

/*Класс для рассылки по EventBus для уведомления UI об изменении состояния соединения с сервером*/
public class ConnectionStateChangedEvent {

    private int m_Message;

    public ConnectionStateChangedEvent (int message) {
        m_Message = message;
    }

    public int getMessage() {
        return m_Message;
    }
}
