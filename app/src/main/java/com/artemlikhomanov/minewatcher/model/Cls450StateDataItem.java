package com.artemlikhomanov.minewatcher.model;

import android.content.Context;

import com.artemlikhomanov.minewatcher.R;

/*Класс содержит все данные о состоянии комбайна из одной посылки*/
public class Cls450StateDataItem {

    private Context m_Context;

    /*Панель лицевая*/
    private     Boolean         m_bShearerStartButton_FrontPanel;                                   //пуск комб
    private     Boolean         m_bShearerStopButton_FrontPanel;                                    //стоп комб
    private     Boolean         m_bConveyorStartButton_FrontPanel;                                  //пуск конв
    private     Boolean         m_bConveyorStopButton_FrontPanel;                                   //стоп конв
    private     Boolean         m_bEmergencyStopButton_FrontPanel;                                  //Стоп аварийный
    private     Boolean         m_bShearerControlSwitcher_FrontPanel;                               //Упр комб
    private     Boolean         m_bRadioControlSwitcher_FrontPanel;                                 //Упр - радио
    private     Boolean         m_bRoadControlPanelSwitcher_FrontPanel;                             //Упр - штрек
    private     Boolean         m_bReadiness_FrontPanel;                                            //Готовность
    private     Boolean         m_bWindowsToTheLeft_FrontPanel;                                     //окна влево
    private     Boolean         m_bWindowsToTheRidht_FrontPanel;                                    //окна вправо
    private     Boolean         m_bWindowsUp_FrontPanel;                                            //окна вверх
    private     Boolean         m_bWindowsDown_FrontPanel;                                          //окна вниз
    private     Boolean         m_bTwoCutterMotorsOn_FrontPanel;                                    //вкл два двиг
    private     Boolean         m_bRightCutterMotorOn_FrontPanel;                                   //вкл правый двиг
    private     Boolean         m_bInverterReset_FrontPanel;                                        //сброс ПЧ
    private     Boolean         m_bConveyorStopOn_FrontPanel;                                       //Вкл стоп конв
    private     Boolean         m_bConveyorStartOn_FrontPanel;                                      //вкл пуск конв
    private     Boolean         m_bShearerStopOn_FrontPanel;                                        //вкл стоп комб
    private     Boolean         m_bShearerStartOn_FrontPanel;                                       //вкл пуск комб
    private     Boolean         m_bFirstMainBreakerOn_FrontPanel;                                   //вкл КМ1
    private     Boolean         m_bSecondMainBreakerOn_FrontPanel;                                  //вкл КМ2

    /*Радиопульт*/
    private     Boolean         m_bShearerStartButton_RemoteControl;                                //пуск комб
    private     Boolean         m_bShearerStopButton_RemoteControl;                                 //стоп комб
    private     Boolean         m_bConveyorStartButton_RemoteControl;                               //пуск конв
    private     Boolean         m_bConveyorStopButton_RemoteControl;                                //стоп конв
    private     Boolean         m_bTramStopButton_RemoteControl;                                    //Стоп подача
    private     Boolean         m_bTramLeftButton_RemoteControl;                                    //Подача влево
    private     Boolean         m_bTramRightButton_RemoteControl;                                   //Подача вправо
    private     Boolean         m_bEmergencyStopButton_RemoteControl;                               //стоп общ
    private     Boolean         m_bLeftCutterUpButton_RemoteControl;                                //РОЛ вверх
    private     Boolean         m_bLeftCutterDownButton_RemoteControl;                              //РОЛ вниз
    private     Boolean         m_bRightCutterUpButton_RemoteControl;                               //РОП вверх
    private     Boolean         m_bRightCutterDownButton_RemoteControl;                             //РОП вниз

    /*Блок управления*/
    private     Boolean         m_bHumiditySensor_ControlUnit;                                      //Датч влажности
    private     Boolean         m_bFirstMainBreakerOnFeedback_ControlUnit;                          //подтв КМ1
    private     Boolean         m_bSecondMainBreakerOnFeedback_ControlUnit;                         //подтв КМ2
    private     Boolean         m_bThirdMainBreakerOnFeedback_ControlUnit;                          //подтв КМ3
    private     Boolean         m_bInverterReadiness_ControlUnit;                                   //Готовность ПЧ
    private     Boolean         m_bTramStopFeedback_ControlUnit;                                    //Подтв стопа подачи
    private     Boolean         m_bInverterSwitchOff_ControlUnit;                                   //Откл ПЧ
    private     Boolean         m_bInverterEmergencyStop_ControlUnit;                               //Стоп общ ПЧ
    private     Boolean         m_bPumpMotorBreakDownTemperature_ControlUnit;                       //Ав.темп МС
    private     Boolean         m_bLeftCutterMotorBreakDownTemperature_ControlUnit;                 //Ав.темп МР лев
    private     Boolean         m_bLeftCutterMotorWarningTemperature_ControlUnit;                   //Предав. МР лев
    private     Boolean         m_bRightCutterMotorBreakDownTemperature_ControlUnit;                //Ав.темп МР прав
    private     Boolean         m_bRightCutterMotorWarningTemperature_ControlUnit;                  //Предав. МР прав
    private     Boolean         m_bLeftHaulageMotorBreakDownTemperature_ControlUnit;                //Ав.темп МП лев
    private     Boolean         m_bLeftHaulageMotorWarningTemperature_ControlUnit;                  //Предав. МП лев
    private     Boolean         m_bRightHaulageMotorBreakDownTemperature_ControlUnit;               //Ав.темп МП прав
    private     Boolean         m_bRightHaulageMotorWarningTemperature_ControlUnit;                 //Предав. МП прав
    private     Integer         m_intLeftCutterMotorCurrent_ControlUnit;                            //Ток МР лев
    private     String          m_strLeftCutterMotorCurrent_ControlUnit;                            //Ток МР лев
    private     Integer         m_intRightCutterMotorCurrent_ControlUnit;                           //Ток МР прав
    private     String          m_strRightCutterMotorCurrent_ControlUnit;                           //Ток МР прав
    private     Integer         m_intLeftHaulageMotorCurrent_ControlUnit;                           //Ток МП лев
    private     String          m_strLeftHaulageMotorCurrent_ControlUnit;                           //Ток МП лев
    private     Integer         m_intRightHaulageMotorCurrent_ControlUnit;                          //Ток МП прав
    private     String          m_strRightHaulageMotorCurrent_ControlUnit;                          //Ток МП прав
    private     Integer         m_intPumpMotorCurrent_ControlUnit;                                  //Ток МС
    private     String          m_strPumpMotorCurrent_ControlUnit;                                  //Ток МС
    private     Integer         m_intHighVoltageValue_ControlUnit;                                  //Силовое напряжение (1140В)
    private     String          m_strHighVoltageValue_ControlUnit;                                  //Силовое напряжение (1140В)

    /*Панель контакторов*/
    private     Integer         m_intLeftCutterMotorInsulation_BreakersPanel;                       //R утечки MRL, кОм
    private     Integer         m_intRightCutterMotorInsulation_BreakersPanel;                      //R утечки MRR, кОм

    /*Пульт управления левый*/
    private     Boolean         m_bTramStopButton_LeftControlPanel;                                 //Стоп подача
    private     Boolean         m_bTramLeftButton_LeftControlPanel;                                 //Подача влево
    private     Boolean         m_bTramRightButton_LeftControlPanel;                                //Подача вправо
    private     Boolean         m_bTraillingCableLimitSwitch_LeftControlPanel;                      //УКТЦ
    private     Boolean         m_bLeftCutterUpButton_LeftControlPanel;                             //РОЛ вверх
    private     Boolean         m_bLeftCutterDownButton_LeftControlPanel;                           //РОЛ вниз
    private     Boolean         m_bRightCutterUpButton_LeftControlPanel;                            //РОП вверх
    private     Boolean         m_bRightCutterDownButton_LeftControlPanel;                          //РОП вниз
    private     Boolean         m_bEmergencyStopButton_LeftControlPanel;                            //стоп общ
    private     Boolean         m_bKeyOn_LeftControlPanel;                                          //Ключ
    private     Boolean         m_bSirenOn_LeftControlPanel;                                        //Сигнал
    private     Integer         m_intLeftCutterMotorTemperatureOne_LeftControlPanel;                //темп МРл 1 (Pt100)
    private     String          m_strLeftCutterMotorTemperatureOne_LeftControlPanel;                //темп МРл 1 (Pt100)
    private     Integer         m_intLeftCutterMotorTemperatureTwo_LeftControlPanel;                //темп МРл 2 (Pt100)
    private     String          m_strLeftCutterMotorTemperatureTwo_LeftControlPanel;                //темп МРл 2 (Pt100)
    private     Integer         m_intLeftCutterMotorTemperatureThree_LeftControlPanel;              //темп МРл 3 (Pt100)
    private     String          m_strLeftCutterMotorTemperatureThree_LeftControlPanel;              //темп МРл 3 (Pt100)
    private     Integer         m_intLeftHaulageMotorTemperatureOne_LeftControlPanel;               //темп МПл 1 (Pt100)
    private     String          m_strLeftHaulageMotorTemperatureOne_LeftControlPanel;               //темп МПл 1 (Pt100)
    private     Integer         m_intLeftHaulageMotorTemperatureTwo_LeftControlPanel;               //темп МПл 2 (Pt100)
    private     String          m_strLeftHaulageMotorTemperatureTwo_LeftControlPanel;               //темп МПл 2 (Pt100)
    private     Integer         m_intLeftHaulageMotorTemperatureThree_LeftControlPanel;             //темп МПл 3 (Pt100)
    private     String          m_strLeftHaulageMotorTemperatureThree_LeftControlPanel;             //темп МПл 3 (Pt100)

    /*Пульт управления правый*/
    private     Boolean         m_bTramStopButton_RightControlPanel;                                //Стоп подача
    private     Boolean         m_bTramLeftButton_RightControlPanel;                                //Подача влево
    private     Boolean         m_bTramRightButton_RightControlPanel;                               //Подача вправо
    private     Boolean         m_bLeftCutterUpButton_RightControlPanel;                            //РОЛ вверх
    private     Boolean         m_bLeftCutterDownButton_RightControlPanel;                          //РОЛ вниз
    private     Boolean         m_bRightCutterUpButton_RightControlPanel;                           //РОП вверх
    private     Boolean         m_bRightCutterDownButton_RightControlPanel;                         //РОП вниз
    private     Boolean         m_bEmergencyStopButton_RightControlPanel;                           //стоп общ
    private     Boolean         m_bKeyOn_RightControlPanel;                                         //Ключ
    private     Boolean         m_bSirenOn_RightControlPanel;                                       //Сигнал
    private     Integer         m_intRightCutterMotorTemperatureOne_RightControlPanel;              //темп МРп 1 (Pt100)
    private     String          m_strRightCutterMotorTemperatureOne_RightControlPanel;              //темп МРп 1 (Pt100)
    private     Integer         m_intRightCutterMotorTemperatureTwo_RightControlPanel;              //темп МРп 2 (Pt100)
    private     String          m_strRightCutterMotorTemperatureTwo_RightControlPanel;              //темп МРп 2 (Pt100)
    private     Integer         m_intRightCutterMotorTemperatureThree_RightControlPanel;            //темп МРп 3 (Pt100)
    private     String          m_strRightCutterMotorTemperatureThree_RightControlPanel;            //темп МРп 3 (Pt100)
    private     Integer         m_intRightHaulageMotorTemperatureOne_RightControlPanel;             //темп МПп 1 (Pt100)
    private     String          m_strRightHaulageMotorTemperatureOne_RightControlPanel;             //темп МПп 1 (Pt100)
    private     Integer         m_intRightHaulageMotorTemperatureTwo_RightControlPanel;             //темп МПп 2 (Pt100)
    private     String          m_strRightHaulageMotorTemperatureTwo_RightControlPanel;             //темп МПп 2 (Pt100)
    private     Integer         m_intRightHaulageMotorTemperatureThree_RightControlPanel;           //темп МПп 3 (Pt100)
    private     String          m_strRightHaulageMotorTemperatureThree_RightControlPanel;           //темп МПп 3 (Pt100)

    /*Блок управления распределителями*/
    private     Boolean         m_bLeftCutterUpCommand_HydraulicValveControlUnit;                   //РОЛ вверх
    private     Boolean         m_bLeftCutterDownCommand_HydraulicValveControlUnit;                 //РОЛ вниз
    private     Boolean         m_bRightCutterUpCommand_HydraulicValveControlUnit;                  //РОП вверх
    private     Boolean         m_bRightCutterDownCommand_HydraulicValveControlUnit;                //РОП вниз
    private     Boolean         m_bBrakes_HydraulicValveControlUnit;                                //тормоз
    private     Boolean         m_bRelief_HydraulicValveControlUnit;                                //разгрузка
    private     Boolean         m_bDirtyFilterSensor_HydraulicValveControlUnit;                     //Датч загряз фильтра
    private     Integer         m_intDustReducerPressureSensor_HydraulicValveControlUnit;           //ДД орош
    private     String          m_strDustReducerPressureSensor_HydraulicValveControlUnit;           //ДД орош
    private     Integer         m_intBrakesPressureSensor_HydraulicValveControlUnit;                //ДД торм
    private     String          m_strBrakesPressureSensor_HydraulicValveControlUnit;                //ДД торм
    private     Integer         m_intHydraulicSystemPressureSensor_HydraulicValveControlUnit;       //ДД насоса
    private     String          m_strHydraulicSystemPressureSensor_HydraulicValveControlUnit;       //ДД насоса
    private     Integer         m_intHaulageSystemFlowSensor_HydraulicValveControlUnit;             //ДР в подаче
    private     String          m_strHaulageSystemFlowSensor_HydraulicValveControlUnit;             //ДР в подаче
    private     Integer         m_intCuttingSystemFlowSensor_HydraulicValveControlUnit;             //ДР в резании
    private     String          m_strCuttingSystemFlowSensor_HydraulicValveControlUnit;             //ДР в резании
    private     Integer         m_intShearerFlowSensor_HydraulicValveControlUnit;                   //ДР в комб
    private     String          m_strShearerFlowSensor_HydraulicValveControlUnit;                   //ДР в комб
    private     Integer         m_intLeftCutterRangingGearcaseTemperature_HydraulicValveControlUnit;//t редуктора РОЛ
    private     String          m_strLeftCutterRangingGearcaseTemperature_HydraulicValveControlUnit;//t редуктора РОЛ
    private     Integer         m_intRightCutterRangingGearcaseTemperature_HydraulicValveControlUnit;//t редуктора РОП
    private     String          m_strRightCutterRangingGearcaseTemperature_HydraulicValveControlUnit;//t редуктора РОП
    private     Integer         m_intLeftHaulageRangingGearcaseTemperature_HydraulicValveControlUnit;//t редуктора подачи Л
    private     String          m_strLeftHaulageRangingGearcaseTemperature_HydraulicValveControlUnit;//t редуктора подачи Л
    private     Integer         m_intRightHaulageRangingGearcaseTemperature_HydraulicValveControlUnit;//t редуктора подачи П
    private     String          m_strRightHaulageRangingGearcaseTemperature_HydraulicValveControlUnit;//t редуктора подачи П

    /*ДКМ*/
    private     Integer         m_intHydraulicFluidTemperature_HydraulicFluidSensor;                //темпер масла
    private     String          m_strHydraulicFluidTemperature_HydraulicFluidSensor;                //темпер масла
    private     Integer         m_intHydraulicFluidLevel_HydraulicFluidSensor;                      //уров.масла
    private     String          m_strHydraulicFluidLevel_HydraulicFluidSensor;                      //уров.масла

    /*Пульт управления штрековый*/
    private     Boolean         m_bShearerStartButton_RoadControlPanel;                             //пуск комб
    private     Boolean         m_bShearerStopButton_RoadControlPanel;                              //стоп комб
    private     Boolean         m_bConveyorStartButton_RoadControlPanel;                            //пуск конв
    private     Boolean         m_bConveyorStopButton_RoadControlPanel;                             //стоп конв
    private     Boolean         m_bTramStopButton_RoadControlPanel;                                 //Стоп подача
    private     Boolean         m_bTramLeftButton_RoadControlPanel;                                 //Подача влево
    private     Boolean         m_bTramRightButton_RoadControlPanel;                                //Подача вправо
    private     Boolean         m_bLeftCutterUpButton_RoadControlPanel;                             //РОЛ вверх
    private     Boolean         m_bLeftCutterDownButton_RoadControlPanel;                           //РОЛ вниз
    private     Boolean         m_bRightCutterUpButton_RoadControlPanel;                            //РОП вверх
    private     Boolean         m_bRightCutterDownButton_RoadControlPanel;                          //РОП вниз
    private     Boolean         m_bEmergencyStopButton_RoadControlPanel;                            //стоп общ
    private     Boolean         m_bKeyOn_RoadControlPanel;                                          //Ключ
    private     Boolean         m_bSirenOn_RoadControlPanel;                                        //Сигнал
    private     Boolean         m_bShearerControlSwitcher_RoadControlPanel;                         //Упр комб
    private     Boolean         m_bRoadControlPanelSwitcher_RoadControlPanel;                       //Упр штрек
    private     Boolean         m_bConveyorStopOn_RoadControlPanel;                                 //Вкл стоп конв
    private     Boolean         m_bConveyorStartOn_RoadControlPanel;                                //вкл пуск конв
    private     Boolean         m_bShearerStopOn_RoadControlPanel;                                  //вкл стоп комб
    private     Boolean         m_bShearerStartOn_RoadControlPanel;                                 //вкл пуск комб

    /*Преобразователь частоты*/
    private     Boolean         m_bHighVoltageAbsence_Inverter;                                     //Нет 1140
    private     Boolean         m_bMaximumCurrentProtection_Inverter;                               //сработало МТЗ
    private     Boolean         m_bLackOfHighVoltage_Inverter;                                      //Пониж напряжение
    private     Boolean         m_bCapacitorsCharged_Inverter;                                      //Есть накачка
    private     Boolean         m_bEmergencyStop_Inverter;                                          //стоп общ
    private     Boolean         m_bMaximumCurrentExceeded_Inverter;                                 //Перегруз ПЧ
    private     Boolean         m_bPhaseAsymmetryExceeded_Inverter;                                 //Превыш ассим ЭД
    private     Boolean         m_bMaximumTemperatureExceeded_Inverter;                             //Перегрев ПЧ
    private     Boolean         m_bTramDirection_Inverter;                                          //влево/вправо (1/0)
    private     Boolean         m_bTramStop_Inverter;                                               //стоп подачи
    private     Boolean         m_bMotorsFrequency_Inverter;                                        //1000 об/мин // 750 об.мин
    private     Integer         m_intActualFrequency_Inverter;                                      //Частота текущая
    private     String          m_strActualFrequency_Inverter;                                      //Частота текущая
    private     String          m_strShearerVelocity_Inverter;                                      //Скорость комбайна м/мин
    private     Integer         m_intGivenFrequency_Inverter;                                       //Частота заданная
    private     String          m_strGivenFrequency_Inverter;                                       //Частота заданная
    private     Integer         m_intTotalCurrent_Inverter;                                         //Ток (sum)
    private     Integer         m_intLeftHaulageMotorCurrent_Inverter;                              //Ток (MPL)
    private     Integer         m_intRightHaulageMotorCurrent_Inverter;                             //Ток (MPR)

    /*Тех. окно*/
    private     Boolean         m_bShearerOn_TechnicalWindow;                                       //комб включен
    private     Boolean         m_bTramOn_TechnicalWindow;                                          //подача вкл
    private     Boolean         m_bConveyorOn_TechnicalWindow;                                      //конв включен
    private     Boolean         m_bLongwallStart_TechnicalWindow;                                   //Начало лавы слева/справа
    private     Boolean         m_bLeftControlPanel_TechnicalWindow;                                //UprPUL
    private     Boolean         m_bRightControlPanel_TechnicalWindow;                               //UprPUP
    private     Boolean         m_bRemoteControl_TechnicalWindow;                                   //UprRadio
    private     Boolean         m_bRoadControlPanel_TechnicalWindow;                                //UprStrek
    private     String          m_strLastStop_TechnicalWindow;                                      //Последний стоп
    private     Integer         m_intShearerLocation_TechnicalWindow;                               //местоположение
    private     Integer         m_intLastStopReason_TechnicalWindow;                                //Последний останов
    private     String          m_strIndicationCode_TechnicalWindow;                                //Код индикации

    /*Связь*/
    private     Boolean         m_bResetByPowerOff_Connection;                                      //Был ResetByPowerOff
    private     Boolean         m_bResetByWDT_Connection;                                           //Был ResetByWDT
    private     Boolean         m_bBlock_01h_Connection;                                            //есть связь 01h
    private     Boolean         m_bBlock_02h_Connection;                                            //есть связь 02h
    private     Boolean         m_bBlock_11h_Connection;                                            //есть связь 11h
    private     Boolean         m_bBlock_12h_Connection;                                            //есть связь 12h
    private     Boolean         m_bBlock_13h_Connection;                                            //есть связь 13h
    private     Boolean         m_bBlock_15h_Connection;                                            //есть связь 15h
    private     Boolean         m_bBlock_23h_Connection;                                            //есть связь 23h
    private     Boolean         m_bBlock_25h_Connection;                                            //есть связь 25h
    private     Boolean         m_bBlock_26h_Connection;                                            //есть связь 26h
    private     Boolean         m_bBlock_31h_Connection;                                            //есть связь 31h
    private     Boolean         m_bBlock_32h_Connection;                                            //есть связь 32h
    private     Boolean         m_bBlock_41h_Connection;                                            //есть связь 41h
    private     Boolean         m_bBlock_42h_Connection;                                            //есть связь 42h
    private     Boolean         m_bBlock_51h_Connection;                                            //есть связь 51h
    private     Boolean         m_bBlock_52h_Connection;                                            //есть связь 52h
    private     Boolean         m_bBlock_53h_Connection;                                            //есть связь 53h
    private     Boolean         m_bBlock_54h_Connection;                                            //есть связь 54h
    private     Boolean         m_bBlock_55h_Connection;                                            //есть связь 55h
    private     Boolean         m_bBlock_61h_Connection;                                            //есть связь 61h
    private     Boolean         m_bBlock_91h_Connection;                                            //есть связь 91h
    private     Boolean         m_bBlock_92h_Connection;                                            //есть связь 92h
    private     Boolean         m_bBlock_93h_Connection;                                            //есть связь 93h
    private     Boolean         m_bFirstRemoteControl_Connection;                                   //радиопульт включен главный
    private     Boolean         m_bSecondRemoteControl_Connection;                                  //Вспом.радиопульт включен

    /*Наработка*/
    private     Integer         m_intYesterdayHaulageOperatingTime_OperatingTime;                   //машинное время прошлого дня (по подаче)
    private     Integer         m_intDayOfPastOperatingTime_OperatingTime;                          //день прошлого машвремени
    private     Integer         m_intMonthOfPastOperatingTime_OperatingTime;                        //месяц прошлого машвремени
    private     Integer         m_intTodayHaulageOperatingTime_OperatingTime;                       //машинное время текущего дня (по подаче)
    private     Integer         m_intYesterdayCuttingOperatingTime_OperatingTime;                   //машинное время прошлого дня (по резанию)
    private     Integer         m_intTodayCuttingOperatingTime_OperatingTime;                       //машинное время текущего дня (по резанию)
    private     Integer         m_intYesterdayDistancePassed_OperatingTime;                         //суммарно пройдено метров за прошлые сутки
    private     Integer         m_intTodayDistancePassed_OperatingTime;                             //суммарно пройдено метров за сутки

    /*Неисправности*/
    private     String          m_strMalfunctionOne_Malfunctions;                                   //неисправность 1
    private     String          m_strMalfunctionTwo_Malfunctions;                                   //неисправность 2
    private     String          m_strMalfunctionThree_Malfunctions;                                 //неисправность 3
    private     String          m_strMalfunctionFour_Malfunctions;                                  //неисправность 4
    private     String          m_strMalfunctionFive_Malfunctions;                                  //неисправность 5

    public Cls450StateDataItem(Context context) {
        m_Context = context;
    }

    private String interpretCode(int code) {
        switch (code) {
            case 1:
                return m_Context.getResources().getString(R.string.malfunction_1);
            case 2:
                return m_Context.getResources().getString(R.string.malfunction_2);
            case 3:
                return m_Context.getResources().getString(R.string.malfunction_3);
            case 4:
                return m_Context.getResources().getString(R.string.malfunction_4);
            case 5:
                return m_Context.getResources().getString(R.string.malfunction_5);
            case 6:
                return m_Context.getResources().getString(R.string.malfunction_6);
            case 7:
                return m_Context.getResources().getString(R.string.malfunction_7);
            case 8:
                return m_Context.getResources().getString(R.string.malfunction_8);
            case 9:
                return m_Context.getResources().getString(R.string.malfunction_9);
            case 10:
                return m_Context.getResources().getString(R.string.malfunction_10);
            case 11:
                return m_Context.getResources().getString(R.string.malfunction_11);
            case 12:
                return m_Context.getResources().getString(R.string.malfunction_12);
            case 13:
                return m_Context.getResources().getString(R.string.malfunction_13);
            case 14:
                return m_Context.getResources().getString(R.string.malfunction_14);
            case 15:
                return m_Context.getResources().getString(R.string.malfunction_15);
            case 16:
                return m_Context.getResources().getString(R.string.malfunction_16);
            case 17:
                return m_Context.getResources().getString(R.string.malfunction_17);
            case 18:
                return m_Context.getResources().getString(R.string.malfunction_18);
            case 19:
                return m_Context.getResources().getString(R.string.malfunction_19);
            case 20:
                return m_Context.getResources().getString(R.string.malfunction_20);
            case 21:
                return m_Context.getResources().getString(R.string.malfunction_21);
            case 22:
                return m_Context.getResources().getString(R.string.malfunction_22);
            case 23:
                return m_Context.getResources().getString(R.string.malfunction_23);
            case 24:
                return m_Context.getResources().getString(R.string.malfunction_24);
            case 25:
                return m_Context.getResources().getString(R.string.malfunction_25);
            case 26:
                return m_Context.getResources().getString(R.string.malfunction_26);
            case 27:
                return m_Context.getResources().getString(R.string.malfunction_27);
            case 28:
                return m_Context.getResources().getString(R.string.malfunction_28);
            case 29:
                return m_Context.getResources().getString(R.string.malfunction_29);
            case 30:
                return m_Context.getResources().getString(R.string.malfunction_30);
            case 31:
                return m_Context.getResources().getString(R.string.malfunction_31);
            case 32:
                return m_Context.getResources().getString(R.string.malfunction_32);
            case 33:
                return m_Context.getResources().getString(R.string.malfunction_33);
            case 34:
                return m_Context.getResources().getString(R.string.malfunction_34);
            case 35:
                return m_Context.getResources().getString(R.string.malfunction_35);
            case 36:
                return m_Context.getResources().getString(R.string.malfunction_36);
            case 37:
                return m_Context.getResources().getString(R.string.malfunction_37);
            case 38:
                return m_Context.getResources().getString(R.string.malfunction_38);
            case 39:
                return m_Context.getResources().getString(R.string.malfunction_39);
            case 40:
                return m_Context.getResources().getString(R.string.malfunction_40);
            case 41:
                return m_Context.getResources().getString(R.string.malfunction_41);
            case 42:
                return m_Context.getResources().getString(R.string.malfunction_42);
            case 43:
                return m_Context.getResources().getString(R.string.malfunction_43);
            case 44:
                return m_Context.getResources().getString(R.string.malfunction_44);
            case 45:
                return m_Context.getResources().getString(R.string.malfunction_45);
            case 46:
                return m_Context.getResources().getString(R.string.malfunction_46);
            case 47:
                return m_Context.getResources().getString(R.string.malfunction_47);
            case 48:
                return m_Context.getResources().getString(R.string.malfunction_48);
            case 49:
                return m_Context.getResources().getString(R.string.malfunction_49);
            case 50:
                return m_Context.getResources().getString(R.string.malfunction_50);
            case 51:
                return m_Context.getResources().getString(R.string.malfunction_51);
            case 52:
                return m_Context.getResources().getString(R.string.malfunction_52);
            case 53:
                return m_Context.getResources().getString(R.string.malfunction_53);
            case 54:
                return m_Context.getResources().getString(R.string.malfunction_54);
            case 55:
                return m_Context.getResources().getString(R.string.malfunction_55);
            case 56:
                return m_Context.getResources().getString(R.string.malfunction_56);
            case 57:
                return m_Context.getResources().getString(R.string.malfunction_57);
            case 58:
                return m_Context.getResources().getString(R.string.malfunction_58);
            case 59:
                return m_Context.getResources().getString(R.string.malfunction_59);
            case 60:
                return m_Context.getResources().getString(R.string.malfunction_60);
            case 61:
                return m_Context.getResources().getString(R.string.malfunction_61);
            case 62:
                return m_Context.getResources().getString(R.string.malfunction_62);
            case 63:
                return m_Context.getResources().getString(R.string.malfunction_63);
            case 64:
                return m_Context.getResources().getString(R.string.malfunction_64);
            case 65:
                return m_Context.getResources().getString(R.string.malfunction_65);
            case 66:
                return m_Context.getResources().getString(R.string.malfunction_66);
            case 67:
                return m_Context.getResources().getString(R.string.malfunction_67);
            case 68:
                return m_Context.getResources().getString(R.string.malfunction_68);
            case 69:
                return m_Context.getResources().getString(R.string.malfunction_69);
            case 70:
                return m_Context.getResources().getString(R.string.malfunction_70);
            case 71:
                return m_Context.getResources().getString(R.string.malfunction_71);
            case 72:
                return m_Context.getResources().getString(R.string.malfunction_72);
            case 73:
                return m_Context.getResources().getString(R.string.malfunction_73);
            case 74:
                return m_Context.getResources().getString(R.string.malfunction_74);
            case 75:
                return m_Context.getResources().getString(R.string.malfunction_75);
            case 76:
                return m_Context.getResources().getString(R.string.malfunction_76);
            case 77:
                return m_Context.getResources().getString(R.string.malfunction_77);
            case 78:
                return m_Context.getResources().getString(R.string.malfunction_78);
            case 79:
                return m_Context.getResources().getString(R.string.malfunction_79);
            case 80:
                return m_Context.getResources().getString(R.string.malfunction_80);
            case 81:
                return m_Context.getResources().getString(R.string.malfunction_81);
            case 82:
                return m_Context.getResources().getString(R.string.malfunction_82);
            case 83:
                return m_Context.getResources().getString(R.string.malfunction_83);
            case 84:
                return m_Context.getResources().getString(R.string.malfunction_84);
            case 85:
                return m_Context.getResources().getString(R.string.malfunction_85);
            case 86:
                return m_Context.getResources().getString(R.string.malfunction_86);
            case 87:
                return m_Context.getResources().getString(R.string.malfunction_87);
            case 88:
                return m_Context.getResources().getString(R.string.malfunction_88);
            case 89:
                return m_Context.getResources().getString(R.string.malfunction_89);
            case 90:
                return m_Context.getResources().getString(R.string.malfunction_90);
            case 91:
                return m_Context.getResources().getString(R.string.malfunction_91);
            case 92:
                return m_Context.getResources().getString(R.string.malfunction_92);
            case 93:
                return m_Context.getResources().getString(R.string.malfunction_93);
            case 94:
                return m_Context.getResources().getString(R.string.malfunction_94);
            case 95:
                return m_Context.getResources().getString(R.string.malfunction_95);
            case 96:
                return m_Context.getResources().getString(R.string.malfunction_96);
            case 97:
                return m_Context.getResources().getString(R.string.malfunction_97);
            case 98:
                return m_Context.getResources().getString(R.string.malfunction_98);
            case 99:
                return m_Context.getResources().getString(R.string.malfunction_99);
            case 100:
                return m_Context.getResources().getString(R.string.malfunction_100);
            case 101:
                return m_Context.getResources().getString(R.string.malfunction_101);
            case 102:
                return m_Context.getResources().getString(R.string.malfunction_102);
            case 103:
                return m_Context.getResources().getString(R.string.malfunction_103);
            case 104:
                return m_Context.getResources().getString(R.string.malfunction_104);
            case 105:
                return m_Context.getResources().getString(R.string.malfunction_105);
            case 106:
                return m_Context.getResources().getString(R.string.malfunction_106);
            case 107:
                return m_Context.getResources().getString(R.string.malfunction_107);
            case 108:
                return m_Context.getResources().getString(R.string.malfunction_108);
            case 109:
                return m_Context.getResources().getString(R.string.malfunction_109);
            case 110:
                return m_Context.getResources().getString(R.string.malfunction_110);
            case 111:
                return m_Context.getResources().getString(R.string.malfunction_111);
            case 112:
                return m_Context.getResources().getString(R.string.malfunction_112);
            case 113:
                return m_Context.getResources().getString(R.string.malfunction_113);
            case 114:
                return m_Context.getResources().getString(R.string.malfunction_114);
            case 115:
                return m_Context.getResources().getString(R.string.malfunction_115);
            case 116:
                return m_Context.getResources().getString(R.string.malfunction_116);
            case 117:
                return m_Context.getResources().getString(R.string.malfunction_117);
            case 118:
                return m_Context.getResources().getString(R.string.malfunction_118);
            case 119:
                return m_Context.getResources().getString(R.string.malfunction_119);
            case 120:
                return m_Context.getResources().getString(R.string.malfunction_120);
            case 121:
                return m_Context.getResources().getString(R.string.malfunction_121);
            case 122:
                return m_Context.getResources().getString(R.string.malfunction_122);
            case 123:
                return m_Context.getResources().getString(R.string.malfunction_123);
            case 124:
                return m_Context.getResources().getString(R.string.malfunction_124);
            case 125:
                return m_Context.getResources().getString(R.string.malfunction_125);
            case 126:
                return m_Context.getResources().getString(R.string.malfunction_126);
            case 127:
                return m_Context.getResources().getString(R.string.malfunction_127);
            case 128:
                return m_Context.getResources().getString(R.string.malfunction_128);
            case 129:
                return m_Context.getResources().getString(R.string.malfunction_129);
            case 130:
                return m_Context.getResources().getString(R.string.malfunction_130);
            case 131:
                return m_Context.getResources().getString(R.string.malfunction_131);
            case 132:
                return m_Context.getResources().getString(R.string.malfunction_132);
            case 133:
                return m_Context.getResources().getString(R.string.malfunction_133);
            case 134:
                return m_Context.getResources().getString(R.string.malfunction_134);
            case 135:
                return m_Context.getResources().getString(R.string.malfunction_135);
            case 136:
                return m_Context.getResources().getString(R.string.malfunction_136);
            case 137:
                return m_Context.getResources().getString(R.string.malfunction_137);
            case 138:
                return m_Context.getResources().getString(R.string.malfunction_138);
            case 139:
                return m_Context.getResources().getString(R.string.malfunction_139);
            case 140:
                return m_Context.getResources().getString(R.string.malfunction_140);
            case 141:
                return m_Context.getResources().getString(R.string.malfunction_141);
            case 142:
                return m_Context.getResources().getString(R.string.malfunction_142);
            case 143:
                return m_Context.getResources().getString(R.string.malfunction_143);
            case 144:
                return m_Context.getResources().getString(R.string.malfunction_144);
            case 145:
                return m_Context.getResources().getString(R.string.malfunction_145);
            case 146:
                return m_Context.getResources().getString(R.string.malfunction_146);
            case 147:
                return m_Context.getResources().getString(R.string.malfunction_147);
            case 148:
                return m_Context.getResources().getString(R.string.malfunction_148);
            case 149:
                return m_Context.getResources().getString(R.string.malfunction_149);
            case 150:
                return m_Context.getResources().getString(R.string.malfunction_150);
            case 151:
                return m_Context.getResources().getString(R.string.malfunction_151);
            case 152:
                return m_Context.getResources().getString(R.string.malfunction_152);
            case 153:
                return m_Context.getResources().getString(R.string.malfunction_153);
            case 154:
                return m_Context.getResources().getString(R.string.malfunction_154);
            case 155:
                return m_Context.getResources().getString(R.string.malfunction_155);
            case 156:
                return m_Context.getResources().getString(R.string.malfunction_156);
            case 157:
                return m_Context.getResources().getString(R.string.malfunction_157);
            case 158:
                return m_Context.getResources().getString(R.string.malfunction_158);
            case 159:
                return m_Context.getResources().getString(R.string.malfunction_159);
            case 160:
                return m_Context.getResources().getString(R.string.malfunction_160);
            case 161:
                return m_Context.getResources().getString(R.string.malfunction_161);
            case 162:
                return m_Context.getResources().getString(R.string.malfunction_162);
            case 163:
                return m_Context.getResources().getString(R.string.malfunction_163);
            case 164:
                return m_Context.getResources().getString(R.string.malfunction_164);
            case 165:
                return m_Context.getResources().getString(R.string.malfunction_165);
            case 166:
                return m_Context.getResources().getString(R.string.malfunction_166);
            case 167:
                return m_Context.getResources().getString(R.string.malfunction_167);
            case 168:
                return m_Context.getResources().getString(R.string.malfunction_168);
            case 169:
                return m_Context.getResources().getString(R.string.malfunction_169);
            case 170:
                return m_Context.getResources().getString(R.string.malfunction_170);
            case 171:
                return m_Context.getResources().getString(R.string.malfunction_171);
            case 172:
                return m_Context.getResources().getString(R.string.malfunction_172);
            case 173:
                return m_Context.getResources().getString(R.string.malfunction_173);
            case 174:
                return m_Context.getResources().getString(R.string.malfunction_174);
            case 175:
                return m_Context.getResources().getString(R.string.malfunction_175);
            case 176:
                return m_Context.getResources().getString(R.string.malfunction_176);
            case 177:
                return m_Context.getResources().getString(R.string.malfunction_177);
            case 178:
                return m_Context.getResources().getString(R.string.malfunction_178);
            case 179:
                return m_Context.getResources().getString(R.string.malfunction_179);
            case 180:
                return m_Context.getResources().getString(R.string.malfunction_180);
            case 181:
                return m_Context.getResources().getString(R.string.malfunction_181);
            case 182:
                return m_Context.getResources().getString(R.string.malfunction_182);
            case 183:
                return m_Context.getResources().getString(R.string.malfunction_183);
            case 184:
                return m_Context.getResources().getString(R.string.malfunction_184);
        }
        return "";
    }

    public Integer getLeftCutterMotorCurrent() {
        return m_intLeftCutterMotorCurrent_ControlUnit;
    }

    public String getLeftCutterMotorCurrentString() {
        return m_strLeftCutterMotorCurrent_ControlUnit;
    }

    public void setLeftCutterMotorCurrent(Integer motorCurrent) {
        m_intLeftCutterMotorCurrent_ControlUnit = motorCurrent;
        m_strLeftCutterMotorCurrent_ControlUnit = String.valueOf(motorCurrent) + "%";
    }

    public Integer getRightCutterMotorCurrent() {
        return m_intRightCutterMotorCurrent_ControlUnit;
    }

    public String getRightCutterMotorCurrentString() {
        return m_strRightCutterMotorCurrent_ControlUnit;
    }

    public void setRightCutterMotorCurrent(Integer motorCurrent) {
        m_intRightCutterMotorCurrent_ControlUnit = motorCurrent;
        m_strRightCutterMotorCurrent_ControlUnit = String.valueOf(motorCurrent) + "%";
    }

    public Integer getLeftHaulageMotorCurrent() {
        return m_intLeftHaulageMotorCurrent_ControlUnit;
    }

    public String getLeftHaulageMotorCurrentString() {
        return m_strLeftHaulageMotorCurrent_ControlUnit;
    }

    public void setLeftHaulageMotorCurrent(Integer motorCurrent) {
        m_intLeftHaulageMotorCurrent_ControlUnit = motorCurrent;
        m_strLeftHaulageMotorCurrent_ControlUnit = String.valueOf(motorCurrent) + "%";
    }

    public Integer getRightHaulageMotorCurrent() {
        return m_intRightHaulageMotorCurrent_ControlUnit;
    }

    public String getRightHaulageMotorCurrentString() {
        return m_strRightHaulageMotorCurrent_ControlUnit;
    }

    public void setRightHaulageMotorCurrent(Integer motorCurrent) {
        m_intRightHaulageMotorCurrent_ControlUnit = motorCurrent;
        m_strRightHaulageMotorCurrent_ControlUnit = String.valueOf(motorCurrent) + "%";
    }

    public Integer getPumpMotorCurrent() {
        return m_intPumpMotorCurrent_ControlUnit;
    }

    public String getPumpMotorCurrentString() {
        return m_strPumpMotorCurrent_ControlUnit;
    }

    public void setPumpMotorCurrent(Integer motorCurrent) {
        m_intPumpMotorCurrent_ControlUnit = motorCurrent;
        m_strPumpMotorCurrent_ControlUnit = String.valueOf(motorCurrent) + "%";
    }

    public Integer getLeftCutterMotorStatorTemperature() {
        return m_intLeftCutterMotorTemperatureOne_LeftControlPanel;
    }

    public String getLeftCutterMotorStatorTemperatureString() {
        return m_strLeftCutterMotorTemperatureOne_LeftControlPanel;
    }

    public void setLeftCutterMotorStatorTemperature(Integer temperature) {
        m_intLeftCutterMotorTemperatureOne_LeftControlPanel = temperature;
        m_strLeftCutterMotorTemperatureOne_LeftControlPanel = String.valueOf(temperature) + "°C";
    }

    public Integer getLeftCutterMotorBearingNTemperature() {
        return m_intLeftCutterMotorTemperatureTwo_LeftControlPanel;
    }

    public String getLeftCutterMotorBearingNTemperatureString() {
        return m_strLeftCutterMotorTemperatureTwo_LeftControlPanel;
    }

    public void setLeftCutterMotorBearingNTemperature(Integer temperature) {
        m_intLeftCutterMotorTemperatureTwo_LeftControlPanel = temperature;
        m_strLeftCutterMotorTemperatureTwo_LeftControlPanel = String.valueOf(temperature) + "°C";
    }

    public Integer getLeftCutterMotorBearingPTemperature() {
        return m_intLeftCutterMotorTemperatureThree_LeftControlPanel;
    }

    public String getLeftCutterMotorBearingPTemperatureString() {
        return m_strLeftCutterMotorTemperatureThree_LeftControlPanel;
    }

    public void setLeftCutterMotorBearingPTemperature(Integer temperature) {
        m_intLeftCutterMotorTemperatureThree_LeftControlPanel = temperature;
        m_strLeftCutterMotorTemperatureThree_LeftControlPanel = String.valueOf(temperature) + "°C";
    }

    public Integer getLeftHaulageMotorStatorTemperature() {
        return m_intLeftHaulageMotorTemperatureOne_LeftControlPanel;
    }

    public String getLeftHaulageMotorStatorTemperatureString() {
        return m_strLeftHaulageMotorTemperatureOne_LeftControlPanel;
    }

    public void setLeftHaulageMotorStatorTemperature(Integer temperature) {
        m_intLeftHaulageMotorTemperatureOne_LeftControlPanel = temperature;
        m_strLeftHaulageMotorTemperatureOne_LeftControlPanel = String.valueOf(temperature) + "°C";
    }

    public Integer getLeftHaulageMotorBearingNTemperature() {
        return m_intLeftHaulageMotorTemperatureTwo_LeftControlPanel;
    }

    public String getLeftHaulageMotorBearingNTemperatureString() {
        return m_strLeftHaulageMotorTemperatureTwo_LeftControlPanel;
    }

    public void setLeftHaulageMotorBearingNTemperature(Integer temperature) {
        m_intLeftHaulageMotorTemperatureTwo_LeftControlPanel = temperature;
        m_strLeftHaulageMotorTemperatureTwo_LeftControlPanel = String.valueOf(temperature) + "°C";
    }

    public Integer getLeftHaulageMotorBearingPTemperature() {
        return m_intLeftHaulageMotorTemperatureThree_LeftControlPanel;
    }

    public String getLeftHaulageMotorBearingPTemperatureString() {
        return m_strLeftHaulageMotorTemperatureThree_LeftControlPanel;
    }

    public void setLeftHaulageMotorBearingPTemperature(Integer temperature) {
        m_intLeftHaulageMotorTemperatureThree_LeftControlPanel = temperature;
        m_strLeftHaulageMotorTemperatureThree_LeftControlPanel = String.valueOf(temperature) + "°C";
    }

    public Integer getRightCutterMotorStatorTemperature() {
        return m_intRightCutterMotorTemperatureOne_RightControlPanel;
    }

    public String getRightCutterMotorStatorTemperatureString() {
        return m_strRightCutterMotorTemperatureOne_RightControlPanel;
    }

    public void setRightCutterMotorStatorTemperature(Integer temperature) {
        m_intRightCutterMotorTemperatureOne_RightControlPanel = temperature;
        m_strRightCutterMotorTemperatureOne_RightControlPanel = String.valueOf(temperature) + "°C";
    }

    public Integer getRightCutterMotorBearingNTemperature() {
        return m_intRightCutterMotorTemperatureTwo_RightControlPanel;
    }

    public String getRightCutterMotorBearingNTemperatureString() {
        return m_strRightCutterMotorTemperatureTwo_RightControlPanel;
    }

    public void setRightCutterMotorBearingNTemperature(Integer temperature) {
        m_intRightCutterMotorTemperatureTwo_RightControlPanel = temperature;
        m_strRightCutterMotorTemperatureTwo_RightControlPanel = String.valueOf(temperature) + "°C";
    }

    public Integer getRightCutterMotorBearingPTemperature() {
        return m_intRightCutterMotorTemperatureThree_RightControlPanel;
    }

    public String getRightCutterMotorBearingPTemperatureString() {
        return m_strRightCutterMotorTemperatureThree_RightControlPanel;
    }

    public void setRightCutterMotorBearingPTemperature(Integer temperature) {
        m_intRightCutterMotorTemperatureThree_RightControlPanel = temperature;
        m_strRightCutterMotorTemperatureThree_RightControlPanel = String.valueOf(temperature) + "°C";
    }

    public Integer getRightHaulageMotorStatorTemperature() {
        return m_intRightHaulageMotorTemperatureOne_RightControlPanel;
    }

    public String getRightHaulageMotorStatorTemperatureString() {
        return m_strRightHaulageMotorTemperatureOne_RightControlPanel;
    }

    public void setRightHaulageMotorStatorTemperature(Integer temperature) {
        m_intRightHaulageMotorTemperatureOne_RightControlPanel = temperature;
        m_strRightHaulageMotorTemperatureOne_RightControlPanel = String.valueOf(temperature) + "°C";
    }

    public Integer getRightHaulageMotorBearingNTemperature() {
        return m_intRightHaulageMotorTemperatureTwo_RightControlPanel;
    }

    public String getRightHaulageMotorBearingNTemperatureString() {
        return m_strRightHaulageMotorTemperatureTwo_RightControlPanel;
    }

    public void setRightHaulageMotorBearingNTemperature(Integer temperature) {
        m_intRightHaulageMotorTemperatureTwo_RightControlPanel = temperature;
        m_strRightHaulageMotorTemperatureTwo_RightControlPanel = String.valueOf(temperature) + "°C";
    }

    public Integer getRightHaulageMotorBearingPTemperature() {
        return m_intRightHaulageMotorTemperatureThree_RightControlPanel;
    }

    public String getRightHaulageMotorBearingPTemperatureString() {
        return m_strRightHaulageMotorTemperatureThree_RightControlPanel;
    }

    public void setRightHaulageMotorBearingPTemperature(Integer temperature) {
        m_intRightHaulageMotorTemperatureThree_RightControlPanel = temperature;
        m_strRightHaulageMotorTemperatureThree_RightControlPanel = String.valueOf(temperature) + "°C";
    }

    public Integer getLeftCutterGearcaseTemperature() {
        return m_intLeftCutterRangingGearcaseTemperature_HydraulicValveControlUnit;
    }

    public String getLeftCutterGearcaseTemperatureString() {
        return m_strLeftCutterRangingGearcaseTemperature_HydraulicValveControlUnit;
    }

    public void setLeftCutterGearcaseTemperature(Integer temperature) {
        m_intLeftCutterRangingGearcaseTemperature_HydraulicValveControlUnit = temperature;
        m_strLeftCutterRangingGearcaseTemperature_HydraulicValveControlUnit = String.valueOf(temperature) + "°C";
    }

    public Integer getRightCutterGearcaseTemperature() {
        return m_intRightCutterRangingGearcaseTemperature_HydraulicValveControlUnit;
    }

    public String getRightCutterGearcaseTemperatureString() {
        return m_strRightCutterRangingGearcaseTemperature_HydraulicValveControlUnit;
    }

    public void setRightCutterGearcaseTemperature(Integer temperature) {
        m_intRightCutterRangingGearcaseTemperature_HydraulicValveControlUnit = temperature;
        m_strRightCutterRangingGearcaseTemperature_HydraulicValveControlUnit = String.valueOf(temperature) + "°C";
    }

    public Integer getLeftHaulageGearcaseTemperature() {
        return m_intLeftHaulageRangingGearcaseTemperature_HydraulicValveControlUnit;
    }

    public String getLeftHaulageGearcaseTemperatureString() {
        return m_strLeftHaulageRangingGearcaseTemperature_HydraulicValveControlUnit;
    }

    public void setLeftHaulageGearcaseTemperature(Integer temperature) {
        m_intLeftHaulageRangingGearcaseTemperature_HydraulicValveControlUnit = temperature;
        m_strLeftHaulageRangingGearcaseTemperature_HydraulicValveControlUnit = String.valueOf(temperature) + "°C";
    }

    public Integer getRightHaulageGearcaseTemperature() {
        return m_intRightHaulageRangingGearcaseTemperature_HydraulicValveControlUnit;
    }

    public String getRightHaulageGearcaseTemperatureString() {
        return m_strRightHaulageRangingGearcaseTemperature_HydraulicValveControlUnit;
    }

    public void setRightHaulageGearcaseTemperature(Integer temperature) {
        m_intRightHaulageRangingGearcaseTemperature_HydraulicValveControlUnit = temperature;
        m_strRightHaulageRangingGearcaseTemperature_HydraulicValveControlUnit = String.valueOf(temperature) + "°C";
    }

    public Boolean getPumpMotorBreakDownTemperature() {
        return m_bPumpMotorBreakDownTemperature_ControlUnit;
    }

    public void setPumpMotorBreakDownTemperature(Boolean temperature) {
        m_bPumpMotorBreakDownTemperature_ControlUnit = temperature;
    }

    public Integer getActualInverterFrequency() {
        return m_intActualFrequency_Inverter;
    }

    public String getActualInverterFrequencyString() {
        return m_strActualFrequency_Inverter;
    }

    public void setActualInverterFrequency(Integer frequency) {
        m_intActualFrequency_Inverter = frequency;

        try {
            m_strActualFrequency_Inverter = new StringBuilder().append(frequency / 10.0f).substring(0, 4);
        } catch (IndexOutOfBoundsException e) {
            m_strActualFrequency_Inverter = String.valueOf(frequency / 10.0f);
        }

        setShearerVelocity(frequency);
    }

    public void setShearerVelocity(Integer frequency) {
        try {
            m_strShearerVelocity_Inverter = new StringBuilder().append(frequency * Const.VELOCITY_CALCULATION_COEFFICIENT).substring(0, 4);
        } catch (IndexOutOfBoundsException e) {
            m_strShearerVelocity_Inverter = String.valueOf(frequency * Const.VELOCITY_CALCULATION_COEFFICIENT);
        }
    }

    public String getShearerVelocity () {
        return m_strShearerVelocity_Inverter;
    }

    public Integer getGivenInverterFrequency() {
        return m_intGivenFrequency_Inverter;
    }

    public String getGivenInverterFrequencyString() {
        return m_strGivenFrequency_Inverter;
    }

    public void setGivenInverterFrequency(Integer frequency) {
        m_intGivenFrequency_Inverter = frequency;

        try {
            m_strGivenFrequency_Inverter = new StringBuilder().append(frequency / 10.0f).substring(0, 4);
        } catch (IndexOutOfBoundsException e) {
            m_strGivenFrequency_Inverter = String.valueOf(frequency / 10.0f);
        }

    }

    /*1 - to the left; 0 - to the right*/
    public Boolean isTrammingLeft() {
        return m_bTramDirection_Inverter;
    }

    /*1 - to the left; 0 - to the right*/
    public void setTramDirection(Boolean tramDirection) {
        m_bTramDirection_Inverter = tramDirection;
    }

    /*1 - stop; 0 - tramming*/
    public Boolean getTramStop() {
        return m_bTramStop_Inverter;
    }

    /*1 - stop; 0 - tramming*/
    public void setTramStop(Boolean tramStop) {
        m_bTramStop_Inverter = tramStop;
    }

    public Integer getShearerLocation() {
        return m_intShearerLocation_TechnicalWindow;
    }

    public void setShearerLocation(Integer location) {
        m_intShearerLocation_TechnicalWindow = location;
    }

    /*1 - left; 0 - right*/
    public Boolean isLongwallStartRight() {
        return m_bLongwallStart_TechnicalWindow;
    }

    /*1 - left; 0 - right*/
    public void setLongwallStart(Boolean start) {
        m_bLongwallStart_TechnicalWindow = start;
    }

    public Boolean isShearerOn() {
        return m_bShearerOn_TechnicalWindow;
    }

    public void setShearerOn(Boolean isOn) {
        m_bShearerOn_TechnicalWindow = isOn;
    }

    public Boolean isTramOn() {
        return m_bTramOn_TechnicalWindow;
    }

    public void setTramOn(Boolean isOn) {
        m_bTramOn_TechnicalWindow = isOn;
    }

    public Boolean getInverterSwitchOff() {
        return m_bInverterSwitchOff_ControlUnit;
    }

    public void setInverterSwitchOff(Boolean isOff) {
        m_bInverterSwitchOff_ControlUnit = isOff;
    }

    public Boolean getInverterEmergencyStop() {
        return m_bInverterEmergencyStop_ControlUnit;
    }

    public void setInverterEmergencyStop(Boolean emergencyStop) {
        m_bInverterEmergencyStop_ControlUnit = emergencyStop;
    }

    public Boolean getMotorsFrequency() {
        return m_bMotorsFrequency_Inverter;
    }

    /*0 - 1000 об/мин, 1 - 750 об.мин*/
    public void setMotorsFrequency(Boolean isLowFrequency) {
        m_bMotorsFrequency_Inverter = isLowFrequency;
    }

    public Boolean getConveyorOn() {
        return m_bConveyorOn_TechnicalWindow;
    }

    public void setConveyorOn(Boolean isOn) {
        m_bConveyorOn_TechnicalWindow = isOn;
    }

    public String getIndicationCode() {
        return m_strIndicationCode_TechnicalWindow;
    }

    public void setIndicationCode(String code) {
        m_strIndicationCode_TechnicalWindow = code;
    }

    public Boolean isFirstMainBreakerOn() {
        return m_bFirstMainBreakerOn_FrontPanel;
    }

    public void setFirstMainBreakerOn(Boolean isOn) {
        m_bFirstMainBreakerOn_FrontPanel = isOn;
    }

    public Boolean isSecondMainBreakerOn() {
        return m_bSecondMainBreakerOn_FrontPanel;
    }

    public void setSecondMainBreakerOn(Boolean isOn) {
        m_bSecondMainBreakerOn_FrontPanel = isOn;
    }

    public Integer getHydraulicFluidTemperature() {
        return m_intHydraulicFluidTemperature_HydraulicFluidSensor;
    }

    public String getHydraulicFluidTemperatureString() {
        return m_strHydraulicFluidTemperature_HydraulicFluidSensor;
    }

    public void setHydraulicFluidTemperature(Integer temperature) {
        m_intHydraulicFluidTemperature_HydraulicFluidSensor = temperature;
        m_strHydraulicFluidTemperature_HydraulicFluidSensor = String.valueOf(temperature) + "°C";
    }

    public Integer getHydraulicFluidLevel() {
        return m_intHydraulicFluidLevel_HydraulicFluidSensor;
    }

    public String getHydraulicFluidLevelString() {
        return m_strHydraulicFluidLevel_HydraulicFluidSensor;
    }

    public void setHydraulicFluidLevel(Integer level) {
        m_intHydraulicFluidLevel_HydraulicFluidSensor = level;

        try {
            m_strHydraulicFluidLevel_HydraulicFluidSensor = new StringBuilder().append(level / 10.0f).substring(0, 4);
            m_strHydraulicFluidLevel_HydraulicFluidSensor = m_strHydraulicFluidLevel_HydraulicFluidSensor + m_Context.getResources().getString(R.string.liter);
        } catch (IndexOutOfBoundsException e) {
            m_strHydraulicFluidLevel_HydraulicFluidSensor = String.valueOf(level / 10.0f);
            m_strHydraulicFluidLevel_HydraulicFluidSensor = m_strHydraulicFluidLevel_HydraulicFluidSensor + m_Context.getResources().getString(R.string.liter);
        }
    }

    public Integer getDustReducerWaterPressure() {
        return m_intDustReducerPressureSensor_HydraulicValveControlUnit;
    }

    public String getDustReducerWaterPressureString() {
        return m_strDustReducerPressureSensor_HydraulicValveControlUnit;
    }

    public void setDustReducerWaterPressure(Integer pressure) {
        m_intDustReducerPressureSensor_HydraulicValveControlUnit = pressure;

        try {
            m_strDustReducerPressureSensor_HydraulicValveControlUnit = new StringBuilder().append(pressure / 10.0f).substring(0, 4);
            m_strDustReducerPressureSensor_HydraulicValveControlUnit = m_strDustReducerPressureSensor_HydraulicValveControlUnit + m_Context.getResources().getString(R.string.atmosphere);
        } catch (IndexOutOfBoundsException e) {
            m_strDustReducerPressureSensor_HydraulicValveControlUnit = String.valueOf(pressure / 10.0f) + m_Context.getResources().getString(R.string.atmosphere);
        }
    }

    public Integer getBrakesHydraulicFluidPressure() {
        return m_intBrakesPressureSensor_HydraulicValveControlUnit;
    }

    public String getBrakesHydraulicFluidPressureString() {
        return m_strBrakesPressureSensor_HydraulicValveControlUnit;
    }

    public void setBrakesHydraulicFluidPressure(Integer pressure) {
        m_intBrakesPressureSensor_HydraulicValveControlUnit = pressure;
        m_strBrakesPressureSensor_HydraulicValveControlUnit = String.valueOf(pressure) + m_Context.getResources().getString(R.string.atmosphere);
    }

    public Integer getPumpHydraulicFluidPressure() {
        return m_intHydraulicSystemPressureSensor_HydraulicValveControlUnit;
    }

    public String getPumpHydraulicFluidPressureString() {
        return m_strHydraulicSystemPressureSensor_HydraulicValveControlUnit;
    }

    public void setPumpHydraulicFluidPressure(Integer pressure) {
        m_intHydraulicSystemPressureSensor_HydraulicValveControlUnit = pressure;
        m_strHydraulicSystemPressureSensor_HydraulicValveControlUnit = String.valueOf(pressure) + m_Context.getResources().getString(R.string.atmosphere);
    }

    public Integer getHaulageSystemWaterConsumption() {
        return m_intHaulageSystemFlowSensor_HydraulicValveControlUnit;
    }

    public String getHaulageSystemWaterConsumptionString() {
        return m_strHaulageSystemFlowSensor_HydraulicValveControlUnit;
    }

    public void setHaulageSystemWaterConsumption(Integer consumption) {
        m_intHaulageSystemFlowSensor_HydraulicValveControlUnit = consumption;
        m_strHaulageSystemFlowSensor_HydraulicValveControlUnit = String.valueOf(consumption) + m_Context.getResources().getString(R.string.lpm);
    }

    public Integer getCuttingSystemWaterConsumption() {
        return m_intCuttingSystemFlowSensor_HydraulicValveControlUnit;
    }

    public String getCuttingSystemWaterConsumptionString() {
        return m_strCuttingSystemFlowSensor_HydraulicValveControlUnit;
    }

    public void setCuttingSystemWaterConsumption(Integer consumption) {
        m_intCuttingSystemFlowSensor_HydraulicValveControlUnit = consumption;
        m_strCuttingSystemFlowSensor_HydraulicValveControlUnit = String.valueOf(consumption) + m_Context.getResources().getString(R.string.lpm);
    }

    public Integer getShearerWaterConsumption() {
        return m_intShearerFlowSensor_HydraulicValveControlUnit;
    }

    public String getShearerWaterConsumptionString() {
        return m_strShearerFlowSensor_HydraulicValveControlUnit;
    }

    public void setShearerWaterConsumption(Integer consumption) {
        m_intShearerFlowSensor_HydraulicValveControlUnit = consumption;
        m_strShearerFlowSensor_HydraulicValveControlUnit = String.valueOf(consumption) + m_Context.getResources().getString(R.string.lpm);
    }

    public Integer getHighVoltageValue() {
        return m_intHighVoltageValue_ControlUnit;
    }

    public String getHighVoltageValueString() {
        return m_strHighVoltageValue_ControlUnit;
    }

    public void setHighVoltageValue(Integer voltage) {
        m_intHighVoltageValue_ControlUnit = voltage;
        m_strHighVoltageValue_ControlUnit = String.valueOf(voltage) + m_Context.getResources().getString(R.string.volts);
    }

    public String getMalfunctionOne() {
        return m_strMalfunctionOne_Malfunctions;
    }

    public void setMalfunctionOne(int malfunctionCode) {
        m_strMalfunctionOne_Malfunctions = interpretCode(malfunctionCode);
    }

    public String getMalfunctionTwo() {
        return m_strMalfunctionTwo_Malfunctions;
    }

    public void setMalfunctionTwo(int malfunctionCode) {
        m_strMalfunctionTwo_Malfunctions = interpretCode(malfunctionCode);
    }

    public String getMalfunctionThree() {
        return m_strMalfunctionThree_Malfunctions;
    }

    public void setMalfunctionThree(int malfunctionCode) {
        m_strMalfunctionThree_Malfunctions = interpretCode(malfunctionCode);
    }

    public String getMalfunctionFour() {
        return m_strMalfunctionFour_Malfunctions;
    }

    public void setMalfunctionFour(int malfunctionCode) {
        m_strMalfunctionFour_Malfunctions = interpretCode(malfunctionCode);
    }

    public String getMalfunctionFive() {
        return m_strMalfunctionFive_Malfunctions;
    }

    public void setMalfunctionFive(int malfunctionCode) {
        m_strMalfunctionFive_Malfunctions = interpretCode(malfunctionCode);
    }

    public String getLastStopCause() {
        return m_strLastStop_TechnicalWindow;
    }

    public void setLastStopCause(int malfunctionCode) {
        m_strLastStop_TechnicalWindow = interpretCode(malfunctionCode);
    }

    public Boolean getBlock_42h_ConnectionState() {
        return m_bBlock_42h_Connection;
    }

    public void setBlock_42h_ConnectionState(boolean isConnected) {
        m_bBlock_42h_Connection = isConnected;
    }

    public Boolean getBlock_32h_ConnectionState() {
        return m_bBlock_32h_Connection;
    }

    public void setBlock_32h_ConnectionState(boolean isConnected) {
        m_bBlock_32h_Connection = isConnected;
    }

    public Boolean getBlock_55h_ConnectionState() {
        return m_bBlock_55h_Connection;
    }

    public void setBlock_55h_ConnectionState(boolean isConnected) {
        m_bBlock_55h_Connection = isConnected;
    }

    public Boolean getBlock_01h_ConnectionState() {
        return m_bBlock_01h_Connection;
    }

    public void setSetBlock_01h_ConnectionState(boolean isConnected) {
        m_bBlock_01h_Connection = isConnected;
    }

    public Boolean getBlock_02h_ConnectionState() {
        return m_bBlock_02h_Connection;
    }

    public void setBlock_02h_ConnectionState(boolean isConnected) {
        m_bBlock_02h_Connection = isConnected;
    }

    public Boolean getBlock_11h_ConnectionState() {
        return m_bBlock_11h_Connection;
    }

    public void setBlock_11h_ConnectionState(boolean isConnected) {
        m_bBlock_11h_Connection = isConnected;
    }

    public Boolean getBlock_12h_ConnectionState() {
        return m_bBlock_12h_Connection;
    }

    public void setBlock_12h_ConnectionState(boolean isConnected) {
        m_bBlock_12h_Connection = isConnected;
    }

    public Boolean getBlock_13h_ConnectionState() {
        return m_bBlock_13h_Connection;
    }

    public void setBlock_13h_ConnectionState(boolean isConnected) {
        m_bBlock_13h_Connection = isConnected;
    }

    public Boolean getBlock_15h_ConnectionState() {
        return m_bBlock_15h_Connection;
    }

    public void setBlock_15h_ConnectionState(boolean isConnected) {
        m_bBlock_15h_Connection = isConnected;
    }

    public Boolean getBlock_23h_ConnectionState() {
        return m_bBlock_23h_Connection;
    }

    public void setBlock_23h_ConnectionState(boolean isConnected) {
        m_bBlock_23h_Connection = isConnected;
    }

    public Boolean getFirstRemoteControl_ConnectionState() {
        return m_bFirstRemoteControl_Connection;
    }

    public void setFirstRemoteControl_ConnectionState(boolean isConnected) {
        m_bFirstRemoteControl_Connection = isConnected;
    }

    public Boolean getSecondRemoteControl_ConnectionState () {
        return m_bSecondRemoteControl_Connection;
    }

    public void setSecondRemoteControl_ConnectionState(boolean isConnected) {
        m_bSecondRemoteControl_Connection = isConnected;
    }

    public Boolean getBlock_25h_ConnectionState() {
        return m_bBlock_25h_Connection;
    }

    public void setBlock_25h_ConnectionState(boolean isConnected) {
        m_bBlock_25h_Connection = isConnected;
    }

    public Boolean getBlock_26h_ConnectionState() {
        return m_bBlock_26h_Connection;
    }

    public void setBlock_26h_ConnectionState(boolean isConnected) {
        m_bBlock_26h_Connection = isConnected;
    }

    public Boolean getBlock_31h_ConnectionState() {
        return m_bBlock_31h_Connection;
    }

    public void setBlock_31h_ConnectionState(boolean isConnected) {
        m_bBlock_31h_Connection = isConnected;
    }

    public Boolean getBlock_91h_ConnectionState() {
        return m_bBlock_91h_Connection;
    }

    public void setBlock_91h_ConnectionState(boolean isConnected) {
        m_bBlock_91h_Connection = isConnected;
    }

    public Boolean getBlock_41h_ConnectionState() {
        return m_bBlock_41h_Connection;
    }

    public void setBlock_41h_ConnectionState(boolean isConnected) {
        m_bBlock_41h_Connection = isConnected;
    }

    public Boolean getBlock_92h_ConnectionState() {
        return m_bBlock_92h_Connection;
    }

    public void setBlock_92h_ConnectionState(boolean isConnected) {
        m_bBlock_92h_Connection = isConnected;
    }

    public Boolean getBlock_54h_ConnectionState() {
        return m_bBlock_54h_Connection;
    }

    public void setBlock_54h_ConnectionState(boolean isConnected) {
        m_bBlock_54h_Connection = isConnected;
    }

    public Boolean getBlock_52h_ConnectionState() {
        return m_bBlock_52h_Connection;
    }

    public void setBlock_52h_ConnectionState(boolean isConnected) {
        m_bBlock_52h_Connection = isConnected;
    }

    public Boolean getBlock_53h_ConnectionState() {
        return m_bBlock_53h_Connection;
    }

    public void setBlock_53h_ConnectionState(boolean isConnected) {
        m_bBlock_53h_Connection = isConnected;
    }

    public Boolean getBlock_51h_ConnectionState() {
        return m_bBlock_51h_Connection;
    }

    public void setBlock_51h_ConnectionState(boolean isConnected) {
        m_bBlock_51h_Connection = isConnected;
    }

    public Boolean getBlock_93h_ConnectionState() {
        return m_bBlock_93h_Connection;
    }

    public void setBlock_93h_ConnectionState(boolean isConnected) {
        m_bBlock_93h_Connection = isConnected;
    }

    public Boolean getBlock_61h_ConnectionState() {
        return m_bBlock_61h_Connection;
    }

    public void setBlock_61h_ConnectionState(boolean isConnected) {
        m_bBlock_61h_Connection = isConnected;
    }
}