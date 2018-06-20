package com.artemlikhomanov.minewatcher.controller;

import android.util.Log;

import com.artemlikhomanov.minewatcher.model.Const;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/*Класс где описана логика открытия сокета, подключения к серверу и приема данных*/
public class TCPClient {

    private static final String TAG = "TCPClient";
//    private final Handler mHandler;
    private String m_IpNumber, m_IncomingMessage, m_Command;
    private BufferedReader m_In;
    private PrintWriter m_Out;
    private Socket m_Socket;
    private MessageCallback m_Listener;
    private ConnectionStateChangedListener m_ConnectionListener;
    private boolean m_Run = false;
    private int m_PortNumber;

    public TCPClient(String command, String ipNumber, String portNumber, MessageCallback listener, ConnectionStateChangedListener connectionListener) {
        m_Listener = listener;
        m_ConnectionListener = connectionListener;
        m_IpNumber = ipNumber;
        m_Command = command ;
        m_PortNumber = Integer.parseInt(portNumber);
//        mHandler = mHandler;
    }

    /*Public method for sending the message via OutputStream object*/
    public void sendMessage(String message){
        if (m_Out != null && !m_Out.checkError()) {
            m_Out.println(message);
            m_Out.flush();
//            mHandler.sendEmptyMessageDelayed(MainActivity.SENDING, 1000);
            Log.i(TAG, "Sent Message: " + message);

        }
    }

    /*Public method for stopping the TCPClient object ( and finalizing it after that )*/
    public void stopClient(){
        m_Run = false;
        Log.i(TAG, "Client stopped");
    }

    public void run() {

        m_Run = true;

        try {
            // Creating InetAddress object from m_IpNumber passed via constructor
            InetAddress serverAddress = InetAddress.getByName(m_IpNumber);
            Log.i(TAG, "Connecting...");

            /* Here the m_Socket is created with hardcoded port*/
            try {
                reportConnectionStateChange(Const.CONNECTING);
                m_Socket = new Socket(serverAddress, m_PortNumber);
            } catch (Exception e) {
                /*Если сервер не доступен*/
                Log.i(TAG, e.getMessage());
                Log.i(TAG, "Server is not available now");
                reportConnectionStateChange(Const.SERVER_NOT_AVAILABLE);
            }

            try {

                if (m_Socket != null) {
                    // Create PrintWriter object for sending messages to server.
                    m_Out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(m_Socket.getOutputStream())), true);

                    //Create BufferedReader object for receiving messages from server.
                    m_In = new BufferedReader(new InputStreamReader(m_Socket.getInputStream()));

                    Log.i(TAG, "In/Out created");

                    reportConnectionStateChange(Const.CONNECTION_ESTABLISHED);

                    //Listen for the incoming messages while m_Run = true
                    while (m_Run) {

                        m_IncomingMessage = m_In.readLine();

                        /*Если связь с сервером пропала*/
                        if (m_IncomingMessage == null) {
                            Log.i(TAG, "Server is not available now");
                            /*Отправить сообщение для обновления UI*/
                            reportConnectionStateChange(Const.SERVER_NOT_AVAILABLE);
                            /*Закрыть клиент*/
                            stopClient();
                        }

                        if (m_IncomingMessage != null && m_Listener != null) {

                            /*Incoming message is passed to MessageCallback object*/
                            m_Listener.callbackMessageReceiver(m_IncomingMessage);
                        }
                        m_IncomingMessage = null;
                    }
                }
            } catch (Exception e) {
                Log.i(TAG, "Error", e);
            } finally {
                if (m_Socket != null) {
                    m_Out.flush();
                    m_Out.close();
                    m_In.close();
                    m_Socket.close();
                    Log.i(TAG, "Socket Closed");
                }
            }
        } catch (Exception e) {
            Log.i(TAG, "Error", e);
        }
    }

    private void reportConnectionStateChange(int message) {
        if (m_ConnectionListener != null) {
            m_ConnectionListener.notifyConnectionStateChanged(message);
        }
    }

    /*Callback Interface for sending received messages*/
    public interface MessageCallback {
        void callbackMessageReceiver(String message);
    }
}
