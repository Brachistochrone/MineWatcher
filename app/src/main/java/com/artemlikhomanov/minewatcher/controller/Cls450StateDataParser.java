package com.artemlikhomanov.minewatcher.controller;

import android.content.Context;
import android.util.Log;

import com.artemlikhomanov.minewatcher.model.Cls450StateDataItem;
import com.artemlikhomanov.minewatcher.model.Const;
import com.google.common.base.CharMatcher;

/*Класс парсит принятое сообщение от сервера и создает объект Cls450StateDataItem*/
public class Cls450StateDataParser {

    private final static String TAG = "Cls450StateDataParser";

    private static char m_tempChar_one;
    private static char m_tempChar_two;
    private static char m_tempChar_three;
    private static String m_tempStr;
    private static int m_ZeroQuantity = 0;

    /*Метод разбивает полученное сообщение и заполняет объект Cls450StateDataItem*/
    public static synchronized Cls450StateDataItem parseItem (String receivedMessage, Context context, ConnectionStateChangedListener listener) {

        /*Если сообщение не null, не пустое и начинается с ! знака - значит это данные посылки*/
        if (receivedMessage != null && !receivedMessage.isEmpty() && receivedMessage.charAt(0) == '!') {

            /*Проверить на ASCII символы*/
            if (isAllAscii(receivedMessage)) {

                /*Проверить чек сумму*/
                if (isCheckSumCorrect(receivedMessage)) {

                    /*Если в посылке вместо данных нули*/
                    if (containsNoData()) {
                        listener.notifyConnectionStateChanged(Const.NO_CONNECTION_WITH_SHEARER);
                        return null;
                    }
                    /*Парсить сообщение*/
                    return parseMessage(receivedMessage, context);
                }
                return null;
            }
            return null;
        } else if (receivedMessage != null && !receivedMessage.isEmpty() && receivedMessage.contains(Const.UNDERGROUND_MODEM_NOT_AVAILABLE_MESSAGE)) {
            /*Нет связи с подземной частью*/
            /*Отправить сообщение для обновления UI*/
            listener.notifyConnectionStateChanged(Const.UNDERGROUND_MODEM_NOT_AVAILABLE);
        }
        return null;
    }

    /*Метод проверяет все ли символы в строке ASCII*/
    private static boolean isAllAscii(String receivedMessage) {
        return CharMatcher.ascii().matchesAllOf(receivedMessage);
    }

    /*Метод проверки чек суммы и подсчета количества нулей*/
    private static boolean isCheckSumCorrect (String receivedMessage) {
        int checkSum = 0;
        int index = 0;
        /*Вычислить чек сумму*/
        for (char c : receivedMessage.toCharArray()) {
            if (index < receivedMessage.length() - 2) {
                checkSum = checkSum + (int) c;
                /*Счет количества 0 в сообщении, минус первые 3 и последние 2 символа*/
                if (index > 2 && c == '0') {
                    m_ZeroQuantity++;
                }
                index++;
            }
        }

        /*Преобразовать в строку и оставить два младших символа*/
        String s = Integer.toHexString(checkSum).toUpperCase().substring(2);

        /*Если вычесленная чек сумма совпадает с последними двумя символами сообщения*/
        if (s.equals(receivedMessage.substring(receivedMessage.length() - 2))) {
            return true;
        }
        return false;
    }

    /*Метод проверяет что в посылке все нули*/
    private static boolean containsNoData() {
        /*Если количество нолей 173*/
        if (m_ZeroQuantity == Const.CHARS_QUANTITY) {
            m_ZeroQuantity = 0;
            return true;
        }
        m_ZeroQuantity = 0;
        return false;
    }

    /*Метод который парсит строку сообщения и заносит данные в объект Cls450StateDataItem*/
    private static Cls450StateDataItem parseMessage (String receivedMessage, Context context) {

        Cls450StateDataItem dataItem = new Cls450StateDataItem(context);                            //создать объект Cls450StateDataItem
        int index = -1;
        boolean[] bTemp;

        for (char c : receivedMessage.toCharArray()) {
            index++;

            switch (index) {
                case 8:
                    bTemp = charToBoolean(c);
                    dataItem.setFirstMainBreakerOn(bTemp[0]);                                       //вкл КМ1
                    dataItem.setSecondMainBreakerOn(bTemp[1]);                                      //вкл КМ2
                    break;
                case 14:
                    bTemp = charToBoolean(c);
                    dataItem.setInverterSwitchOff(bTemp[0]);                                        //Откл ПЧ
                    dataItem.setInverterEmergencyStop(bTemp[1]);                                    //Стоп общ ПЧ
                    dataItem.setPumpMotorBreakDownTemperature(bTemp[3]);                            //Ав.темп МС
                    break;
                case 17:
                    m_tempChar_one = c;                                                             //MSB
                    break;
                case 18:
                    m_tempChar_two = c;                                                             //Ток МР лев
                    dataItem.setLeftCutterMotorCurrent(twoCharsToInt(m_tempChar_one, m_tempChar_two));
                    break;
                case 19:
                    m_tempChar_one = c;                                                             //MSB
                    break;
                case 20:
                    m_tempChar_two = c;                                                             //Ток МР прав
                    dataItem.setRightCutterMotorCurrent(twoCharsToInt(m_tempChar_one, m_tempChar_two));
                    break;
                case 21:
                    m_tempChar_one = c;                                                             //MSB
                    break;
                case 22:
                    m_tempChar_two = c;
                    dataItem.setPumpMotorCurrent(twoCharsToInt(m_tempChar_one, m_tempChar_two));    //Ток МС
                    break;
                case 23:
                    m_tempChar_one = c;                                                             //MSB
                    break;
                case 24:
                    m_tempChar_two = c;
                    break;
                case 25:
                    m_tempChar_three = c;                                                           //Силовое напряжение (1140В)
                    dataItem.setHighVoltageValue(threeCharsToInt(m_tempChar_one, m_tempChar_two, m_tempChar_three));
                    break;
                case 37:
                    m_tempChar_one = c;                                                             //MSB
                    break;
                case 38:
                    m_tempChar_two = c;                                                             //темп МРл 1 (Pt100)
                    dataItem.setLeftCutterMotorStatorTemperature(twoCharsToInt(m_tempChar_one, m_tempChar_two));
                    break;
                case 39:
                    m_tempChar_one = c;                                                             //MSB
                    break;
                case 40:
                    m_tempChar_two = c;                                                             //темп МРл 2 (Pt100)
                    dataItem.setLeftCutterMotorBearingNTemperature(twoCharsToInt(m_tempChar_one, m_tempChar_two));
                    break;
                case 41:
                    m_tempChar_one = c;                                                             //MSB
                    break;
                case 42:
                    m_tempChar_two = c;                                                             //темп МРл 3 (Pt100)
                    dataItem.setLeftCutterMotorBearingPTemperature(twoCharsToInt(m_tempChar_one, m_tempChar_two));
                    break;
                case 43:
                    m_tempChar_one = c;                                                             //MSB
                    break;
                case 44:
                    m_tempChar_two = c;                                                             //темп МПл 1 (Pt100)
                    dataItem.setLeftHaulageMotorStatorTemperature(twoCharsToInt(m_tempChar_one, m_tempChar_two));
                    break;
                case 45:
                    m_tempChar_one = c;                                                             //MSB
                    break;
                case 46:
                    m_tempChar_two = c;                                                             //темп МПл 2 (Pt100)
                    dataItem.setLeftHaulageMotorBearingNTemperature(twoCharsToInt(m_tempChar_one, m_tempChar_two));
                    break;
                case 47:
                    m_tempChar_one = c;                                                             //MSB
                    break;
                case 48:
                    m_tempChar_two = c;                                                             //темп МПл 3 (Pt100)
                    dataItem.setLeftHaulageMotorBearingPTemperature(twoCharsToInt(m_tempChar_one, m_tempChar_two));
                    break;
                case 52:
                    m_tempChar_one = c;                                                             //MSB
                    break;
                case 53:
                    m_tempChar_two = c;                                                             //темп МРп 1 (Pt100)
                    dataItem.setRightCutterMotorStatorTemperature(twoCharsToInt(m_tempChar_one, m_tempChar_two));
                    break;
                case 54:
                    m_tempChar_one = c;                                                             //MSB
                    break;
                case 55:
                    m_tempChar_two = c;                                                             //темп МРп 2 (Pt100)
                    dataItem.setRightCutterMotorBearingNTemperature(twoCharsToInt(m_tempChar_one, m_tempChar_two));
                    break;
                case 56:
                    m_tempChar_one = c;                                                             //MSB
                    break;
                case 57:
                    m_tempChar_two = c;                                                             //темп МРп 3 (Pt100)
                    dataItem.setRightCutterMotorBearingPTemperature(twoCharsToInt(m_tempChar_one, m_tempChar_two));
                    break;
                case 58:
                    m_tempChar_one = c;                                                             //MSB
                    break;
                case 59:
                    m_tempChar_two = c;                                                             //темп МПп 1 (Pt100)
                    dataItem.setRightHaulageMotorStatorTemperature(twoCharsToInt(m_tempChar_one, m_tempChar_two));
                    break;
                case 60:
                    m_tempChar_one = c;                                                             //MSB
                    break;
                case 61:
                    m_tempChar_two = c;                                                             //темп МПп 2 (Pt100)
                    dataItem.setRightHaulageMotorBearingNTemperature(twoCharsToInt(m_tempChar_one, m_tempChar_two));
                    break;
                case 62:
                    m_tempChar_one = c;                                                             //MSB
                    break;
                case 63:
                    m_tempChar_two = c;                                                             //темп МПп 3 (Pt100)
                    dataItem.setRightHaulageMotorBearingPTemperature(twoCharsToInt(m_tempChar_one, m_tempChar_two));
                    break;
                case 66:
                    m_tempChar_one = c;                                                             //MSB
                    break;
                case 67:
                    m_tempChar_two = c;                                                             //ДД орош
                    dataItem.setDustReducerWaterPressure(twoCharsToInt(m_tempChar_one, m_tempChar_two));
                    break;
                case 68:
                    m_tempChar_one = c;                                                             //MSB
                    break;
                case 69:
                    m_tempChar_two = c;                                                             //ДД торм
                    dataItem.setBrakesHydraulicFluidPressure(twoCharsToInt(m_tempChar_one, m_tempChar_two));
                    break;
                case 70:
                    m_tempChar_one = c;                                                             //MSB
                    break;
                case 71:
                    m_tempChar_two = c;                                                             //ДД насоса
                    dataItem.setPumpHydraulicFluidPressure(twoCharsToInt(m_tempChar_one, m_tempChar_two));
                    break;
                case 72:
                    m_tempChar_one = c;                                                             //MSB
                    break;
                case 73:
                    m_tempChar_two = c;                                                             //ДР в подаче
                    dataItem.setHaulageSystemWaterConsumption(twoCharsToInt(m_tempChar_one, m_tempChar_two));
                    break;
                case 74:
                    m_tempChar_one = c;                                                             //MSB
                    break;
                case 75:
                    m_tempChar_two = c;                                                             //ДР в резании
                    dataItem.setCuttingSystemWaterConsumption(twoCharsToInt(m_tempChar_one, m_tempChar_two));
                    break;
                case 76:
                    m_tempChar_one = c;                                                             //MSB
                    break;
                case 77:
                    m_tempChar_two = c;                                                             //ДР в комб
                    dataItem.setShearerWaterConsumption(twoCharsToInt(m_tempChar_one, m_tempChar_two));
                    break;
                case 78:
                    m_tempChar_one = c;                                                             //MSB
                    break;
                case 79:
                    m_tempChar_two = c;                                                             //t редуктора РОЛ
                    dataItem.setLeftCutterGearcaseTemperature(twoCharsToInt(m_tempChar_one, m_tempChar_two));
                    break;
                case 80:
                    m_tempChar_one = c;                                                             //MSB
                    break;
                case 81:
                    m_tempChar_two = c;                                                             //t редуктора РОП
                    dataItem.setRightCutterGearcaseTemperature(twoCharsToInt(m_tempChar_one, m_tempChar_two));
                    break;
                case 82:
                    m_tempChar_one = c;                                                             //MSB
                    break;
                case 83:
                    m_tempChar_two = c;                                                             //t редуктора подачи Л
                    dataItem.setLeftHaulageGearcaseTemperature(twoCharsToInt(m_tempChar_one, m_tempChar_two));
                    break;
                case 84:
                    m_tempChar_one = c;                                                             //MSB
                    break;
                case 85:
                    m_tempChar_two = c;                                                             //t редуктора подачи П
                    dataItem.setRightHaulageGearcaseTemperature(twoCharsToInt(m_tempChar_one, m_tempChar_two));
                    break;
                case 86:
                    m_tempChar_one = c;                                                             //MSB
                    break;
                case 87:
                    m_tempChar_two = c;                                                             //темпер масла
                    dataItem.setHydraulicFluidTemperature(twoCharsToInt(m_tempChar_one, m_tempChar_two));
                    break;
                case 88:
                    m_tempChar_one = c;                                                             //MSB
                    break;
                case 89:
                    m_tempChar_two = c;
                    break;
                case 90:
                    m_tempChar_three = c;                                                           //уров.масла
                    dataItem.setHydraulicFluidLevel(threeCharsToInt(m_tempChar_one, m_tempChar_two, m_tempChar_three));
                    break;
                case 99:
                    m_tempChar_one = c;                                                             //MSB
                    break;
                case 100:
                    m_tempChar_two = c;
                    break;
                case 101:
                    m_tempChar_three = c;                                                           //Частота текущая
                    dataItem.setActualInverterFrequency(threeCharsToInt(m_tempChar_one, m_tempChar_two, m_tempChar_three));
                    break;
                case 106:
                    bTemp = charToBoolean(c);
                    dataItem.setTramDirection(bTemp[0]);                                            //влево/вправо
                    dataItem.setTramStop(bTemp[1]);                                                 //стоп подачи
                    dataItem.setMotorsFrequency(bTemp[3]);                                          //1000 об/мин // 750 об.мин
                    break;
                case 107:
                    bTemp = charToBoolean(c);
                    dataItem.setShearerOn(bTemp[0]);                                                //комб включен
                    dataItem.setTramOn(bTemp[1]);                                                   //подача вкл
                    dataItem.setConveyorOn(bTemp[2]);                                               //конв включен
                    dataItem.setLongwallStart(bTemp[3]);                                            //Начало лавы слева/справа
                    break;
                case 108:
                    m_tempChar_one = c;                                                             //MSB
                    break;
                case 109:
                    m_tempChar_two = c;
                    dataItem.setLastStopCause(twoCharsToInt(m_tempChar_one, m_tempChar_two));       //Последний стоп
                    break;
                case 110:
                    m_tempChar_one = c;                                                             //MSB
                    break;
                case 111:
                    m_tempChar_two = c;
                    break;
                case 112:
                    m_tempChar_three = c;                                                           //местоположение
                    dataItem.setShearerLocation(threeCharsToInt(m_tempChar_one, m_tempChar_two, m_tempChar_three));
                    break;
                case 113:                                                                           //код индикации старший знак
                    if (c == 'K') {                                                                 //меняем англ. К на П
                        c = 'П';
                    }
                    m_tempStr = String.valueOf(c);
                    break;
                case 114:                                                                           //код индикации средний знак
                    if (c == '4' && m_tempStr.contains("П")) {                                      //меняем 4 на Ч
                        c = 'Ч';
                    }
                    m_tempStr = m_tempStr + c;
                    dataItem.setIndicationCode(m_tempStr);
                    break;
                case 115:                                                                           //код индикации младший знак
                    m_tempStr = m_tempStr + c;
                    dataItem.setIndicationCode(m_tempStr);
                    break;
                case 116:                                                                           //код индикации точка
                    if (c == '1') {                                                                 //после первого знака
                        m_tempStr = new StringBuilder(m_tempStr).insert(dataItem.getIndicationCode().length()-2, ".").toString();
                        dataItem.setIndicationCode(m_tempStr);
                    } else if (c == '2') {                                                          //после второго знака
                        m_tempStr = new StringBuilder(m_tempStr).insert(dataItem.getIndicationCode().length()-1, ".").toString();
                        dataItem.setIndicationCode(m_tempStr);
                    }
                    break;
                case 117:
                    bTemp = charToBoolean(c);
                    dataItem.setBlock_32h_ConnectionState(bTemp[3]);                                //есть связь 32h
                    break;
                case 118:
                    bTemp = charToBoolean(c);
                    dataItem.setBlock_42h_ConnectionState(bTemp[2]);                                //есть связь 42h
                    dataItem.setBlock_55h_ConnectionState(bTemp[3]);                                //есть связь 55h
                    break;
                case 119:
                    bTemp = charToBoolean(c);
                    dataItem.setSetBlock_01h_ConnectionState(bTemp[0]);                             //есть связь 01h
                    dataItem.setBlock_02h_ConnectionState(bTemp[1]);                                //есть связь 02h
                    dataItem.setBlock_11h_ConnectionState(bTemp[2]);                                //есть связь 11h
                    dataItem.setBlock_12h_ConnectionState(bTemp[3]);                                //есть связь 12h
                    break;
                case 120:
                    bTemp = charToBoolean(c);
                    dataItem.setBlock_13h_ConnectionState(bTemp[0]);                                //есть связь 13h
                    dataItem.setBlock_15h_ConnectionState(bTemp[1]);                                //есть связь 15h
                    dataItem.setBlock_23h_ConnectionState(bTemp[2]);                                //есть связь 23h
                    dataItem.setFirstRemoteControl_ConnectionState(bTemp[3]);                       //радиопульт включен главный
                    break;
                case 121:
                    bTemp = charToBoolean(c);
                    dataItem.setBlock_25h_ConnectionState(bTemp[0]);                                //есть связь 25h
                    dataItem.setBlock_26h_ConnectionState(bTemp[1]);                                //есть связь 26h
                    dataItem.setBlock_31h_ConnectionState(bTemp[2]);                                //есть связь 31h
                    dataItem.setBlock_91h_ConnectionState(bTemp[3]);                                //есть связь 91h
                    break;
                case 122:
                    bTemp = charToBoolean(c);
                    dataItem.setBlock_41h_ConnectionState(bTemp[0]);                                 //есть связь 41h
                    dataItem.setBlock_92h_ConnectionState(bTemp[1]);                                //есть связь 92h
                    dataItem.setBlock_54h_ConnectionState(bTemp[2]);                                //есть связь 54h
                    dataItem.setBlock_52h_ConnectionState(bTemp[3]);                                //есть связь 52h
                    break;
                case 123:
                    bTemp = charToBoolean(c);
                    dataItem.setBlock_53h_ConnectionState(bTemp[0]);                                //есть связь 53h
                    dataItem.setBlock_51h_ConnectionState(bTemp[1]);                                //есть связь 51h
                    dataItem.setBlock_93h_ConnectionState(bTemp[2]);                                //есть связь 93h
                    dataItem.setBlock_61h_ConnectionState(bTemp[3]);                                //есть связь 61h
                    break;
                case 148:
                    m_tempChar_one = c;                                                             //MSB
                    break;
                case 149:
                    m_tempChar_two = c;
                    dataItem.setMalfunctionOne(twoCharsToInt(m_tempChar_one, m_tempChar_two));      //неисправность 1
                    break;
                case 150:
                    m_tempChar_one = c;                                                             //MSB
                    break;
                case 151:
                    m_tempChar_two = c;
                    dataItem.setMalfunctionTwo(twoCharsToInt(m_tempChar_one, m_tempChar_two));      //неисправность 2
                    break;
                case 152:
                    m_tempChar_one = c;                                                             //MSB
                    break;
                case 153:
                    m_tempChar_two = c;
                    dataItem.setMalfunctionThree(twoCharsToInt(m_tempChar_one, m_tempChar_two));    //неисправность 3
                    break;
                case 154:
                    m_tempChar_one = c;                                                             //MSB
                    break;
                case 155:
                    m_tempChar_two = c;
                    dataItem.setMalfunctionFour(twoCharsToInt(m_tempChar_one, m_tempChar_two));     //неисправность 4
                    break;
                case 156:
                    m_tempChar_one = c;                                                             //MSB
                    break;
                case 157:
                    m_tempChar_two = c;
                    dataItem.setMalfunctionFive(twoCharsToInt(m_tempChar_one, m_tempChar_two));     //неисправность 5
                    break;
                case 158:
                    m_tempChar_one = c;                                                             //MSB
                    break;
                case 159:
                    m_tempChar_two = c;                                                             //Ток МП лев
                    dataItem.setLeftHaulageMotorCurrent(twoCharsToInt(m_tempChar_one, m_tempChar_two));
                    break;
                case 160:
                    m_tempChar_one = c;                                                             //MSB
                    break;
                case 161:
                    m_tempChar_two = c;                                                             //Ток МП прав
                    dataItem.setRightHaulageMotorCurrent(twoCharsToInt(m_tempChar_one, m_tempChar_two));
                    break;
                case 171:
                    bTemp = charToBoolean(c);
                    dataItem.setSecondRemoteControl_ConnectionState(bTemp[0]);                      //Вспом.радиопульт включен
                    break;
                case 172:
                    m_tempChar_one = c;                                                             //MSB
                    break;
                case 173:
                    m_tempChar_two = c;
                    break;
                case 174:
                    m_tempChar_three = c;                                                           //Частота заданная
                    dataItem.setGivenInverterFrequency(threeCharsToInt(m_tempChar_one, m_tempChar_two, m_tempChar_three));
                    break;
            }
        }
        return dataItem;
    }

    /*Метод интерпритации ASCII символа в HEX и воследующего разложения на биты*/
    private static boolean[] charToBoolean (char c) {
        switch (c) {
            case '0':
                return Const.CHAR_0;
            case '1':
                return Const.CHAR_1;
            case '2':
                return Const.CHAR_2;
            case '3':
                return Const.CHAR_3;
            case '4':
                return Const.CHAR_4;
            case '5':
                return Const.CHAR_5;
            case '6':
                return Const.CHAR_6;
            case '7':
                return Const.CHAR_7;
            case '8':
                return Const.CHAR_8;
            case '9':
                return Const.CHAR_9;
            case 'A':
                return Const.CHAR_A;
            case 'B':
                return Const.CHAR_B;
            case 'C':
                return Const.CHAR_C;
            case 'D':
                return Const.CHAR_D;
            case 'E':
                return Const.CHAR_E;
            case 'F':
                return Const.CHAR_F;
        }
        return Const.CHAR_0;
    }

    /*Метод склеивает два ASCII символа, получая строку с HEX и преобразует ее в int*/
    private static int twoCharsToInt (char first, char second) {
        char[] buffer = new char[]{first, second};
        String hexNumber = String.valueOf(buffer);
        try {
            /*number 16 refers to base 16, i.e. a number system (hexadecimal)*/
            return Integer.parseInt(hexNumber, 16);
        } catch (NumberFormatException e) {
            /*Если в строке недопустимые символы*/
            return 0;
        }
    }

    /*Метод склеивает три ASCII символа, получая строку с HEX и преобразует ее в int*/
    private static int threeCharsToInt (char first, char second, char third) {
        char[] buffer = new char[]{first, second, third};
        String hexNumber = String.valueOf(buffer);
        try {
            /*number 16 refers to base 16, i.e. a number system (hexadecimal)*/
            return Integer.parseInt(hexNumber, 16);
        } catch (NumberFormatException e) {
            /*Если в строке недопустимые символы*/
            return 0;
        }
    }
}
