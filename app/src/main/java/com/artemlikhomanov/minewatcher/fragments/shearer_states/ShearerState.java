package com.artemlikhomanov.minewatcher.fragments.shearer_states;

/*паттерн "состояние"*/
/*интерфейс для обобщения состояния отображения комбайна в TechnicalWindowFragment*/
public interface ShearerState {
    /*выключить комбайн*/
    void turnOffShearer();
    /*вкл левый шнек*/
    void startLeftCutter();
    /*вкл правый шнек*/
    void startRightCutter();
    /*выкл подачу*/
    void turnOffTramming();
    /*подача влево*/
    void startTrammingLeft();
    /*подача вправо*/
    void startTrammingRight();
}
