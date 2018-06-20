package com.artemlikhomanov.minewatcher.model;

/*Класс, содержащий константы, используемые в проекте*/
public class Const {

    public static final long FETCHING_KILLER_TRIGGER_TIME = (long) (1000.0*300.0);                  //5 min, время, после которого FetchService будет уничтожен и сокет будет закрыт

    public final static String IP_NUMBER = "178.151.34.8";                                          //Server IP
    public final static String UNDERGROUND_MODEM_NOT_AVAILABLE_MESSAGE = "No data from K2";

    public final static int PORT_NUMBER = 125;                                                      //Server port number
    public final static int DATA_ITEMS_ARRAY_LIST_SIZE = 800;                                       //Initial size of the ArrayList of Cls450StateDataItem
    public final static int DATA_ITEMS_LIMIT = 600;                                                 //Max number of Cls450StateDataItem in the ArrayList
    public final static int BAR_CHART_UPDATE_ANIMATION_TIME = 500;                                  //Время анимации изменения значения столбцов
    public final static int BAR_CHART_RESET_ANIMATION_TIME = 2000;                                  //Время анимации обнуления значения столбцов
    public final static int BREAKDOWN_PUMP_MOTOR_TEMPERATURE = 160;                                 //Аварийная темп. МС
    public final static int NORMAL_PUMP_MOTOR_TEMPERATURE = 50;                                     //Нормальная темп. МС
    public final static int NUMBER_OF_ROOF_SUPPORTS = 300;                                          //Количество секций лавы
    public final static int SHEARER_LENGTH = 8;                                                     //Длина комбайна
    public final static int RESULT_CANCELED = -1;                                                   //Код не успешного завершения
    public final static int RESULT_OK = 0;                                                          //Код успешного завершения
    public final static int SERVER_NOT_AVAILABLE = 1;                                               //Нет связи с сервером
    public final static int NETWORK_NOT_AVAILABLE = 2;                                              //Сеть не доступна
    public final static int UNDERGROUND_MODEM_NOT_AVAILABLE = 3;                                    //Нет связи с подземным модемом
    public final static int NO_CONNECTION_WITH_SHEARER = 4;                                         //Нет связи с комбайном
    public final static int CONNECTING = 5;                                                         //Подключение
    public final static int CONNECTION_ESTABLISHED = 6;                                             //Связь установлена
    public final static int SERVER_DISCONNECTED = 7;                                                //Связь разорвана
    public final static int CHARS_QUANTITY = 173;                                                   //Кол-во знаков в посылке минус первые три и чек сумма
    public final static int FRAGMENTS_QUANTITY = 4;                                                 //Кол-во вкладок - фрагментов
    public final static int SHEARER_TYPE_CLS450_first = 11;
    public final static int SHEARER_TYPE_CLS450 = 12;
    public final static int SHEARER_TYPE_KDK500 = 13;

    public final static float CURRENT_BAR_CHART_MAX_VALUE = 255.0f;                                 //Max значение токовых столбцов в %
    public final static float MOTOR_TEMPERATURE_BAR_CHART_MAX_VALUE = 160.0f;                       //Max значение темп столбцов двигателей в C
    public final static float BEARING_TEMPERATURE_BAR_CHART_MAX_VALUE = 80.0f;                      //Max значение темп столбцов двигателей в C
    public final static float FREQUENCY_BAR_CHART_MAX_VALUE = 100.0f;                               //Max значение частотных столбцов в Гц
    public final static float VELOCITY_CALCULATION_COEFFICIENT = 0.0175f;                           //Коэф. расчета скорость = (7/40)/10; 40Гц - 7 м/мин
    public final static float LONGWALL_LENGTH = 320.0f;                                             //Длина лавы
    public final static float WIDTH_OF_ROOF_SUPPORT = 1.5f;                                         //Ширина секции
    public final static float LOCATION_DIAGRAM_DESCRIPTION_TEXT_SIZE = 12f;                         //Высота текста названия графиков
    public final static float LOCATION_DIAGRAM_X_AXIS_MIN_VALUE = 0.0f;                             //Минимальное значение оси Х
    public final static float LOCATION_DIAGRAM_X_AXIS_MAX_VALUE = 24.0f;                            //Максимальное значение оси Х
    public final static float LOCATION_DIAGRAM_Y_AXIS_MIN_VALUE = 0.0f;                             //Минимальное значение оси Y
    public final static float LOCATION_DIAGRAM_Y_AXIS_MAX_VALUE = 350.0f;                           //Максимальное значение оси Y


    /*LSB-->MSB*/
    public final static boolean[] CHAR_0 = new boolean[]{false, false, false, false};               //0000
    public final static boolean[] CHAR_1 = new boolean[]{true, false, false, false};                //0001
    public final static boolean[] CHAR_2 = new boolean[]{false, true, false, false};                //0010
    public final static boolean[] CHAR_3 = new boolean[]{true, true, false, false};                 //0011
    public final static boolean[] CHAR_4 = new boolean[]{false, false, true, false};                //0100
    public final static boolean[] CHAR_5 = new boolean[]{true, false, true, false};                 //0101
    public final static boolean[] CHAR_6 = new boolean[]{false, true, true, false};                 //0110
    public final static boolean[] CHAR_7 = new boolean[]{true, true, true, false};                  //0111
    public final static boolean[] CHAR_8 = new boolean[]{false, false, false, true};                //1000
    public final static boolean[] CHAR_9 = new boolean[]{true, false, false, true};                 //1001
    public final static boolean[] CHAR_A = new boolean[]{false, true, false, true};                 //1010
    public final static boolean[] CHAR_B = new boolean[]{true, true, false, true};                  //1011
    public final static boolean[] CHAR_C = new boolean[]{false, false, true, true};                 //1100
    public final static boolean[] CHAR_D = new boolean[]{true, false, true, true};                  //1101
    public final static boolean[] CHAR_E = new boolean[]{false, true, true, true};                  //1110
    public final static boolean[] CHAR_F = new boolean[]{true, true, true, true};                   //1111

    public final static String[] VALUES_FOR_X_AXIS = new String[]{"8:00", "9:00", "10:00", "11:00",
                                                                    "12:00", "13:00", "14:00", "15:00",
                                                                    "16:00", "17:00", "18:00", "19:00",
                                                                    "20:00", "21:00", "22:00", "23:00",
                                                                    "00:00", "01:00", "02:00", "03:00",
                                                                    "04:00", "05:00", "06:00", "07:00",
                                                                    "08:00"};                       //значения для замены по оси Х
}
